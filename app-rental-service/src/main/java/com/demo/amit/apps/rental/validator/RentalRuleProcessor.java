package com.demo.amit.apps.rental.validator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.demo.amit.apps.rental.dto.RentAgreementDTO;
import com.demo.amit.apps.rental.dto.RentalRequest;
import com.demo.amit.apps.rental.dto.ToolConfig;
import com.demo.amit.apps.rental.dto.ToolDTO;
import com.demo.amit.apps.rental.dto.ToolInfo;
import com.demo.amit.apps.rental.exception.DiscountNotInRangeException;
import com.demo.amit.apps.rental.exception.DuplicateToolRentRequestException;
import com.demo.amit.apps.rental.exception.InvalidCheckOutDateException;
import com.demo.amit.apps.rental.exception.InvalidRentalDayException;
import com.demo.amit.apps.rental.response.RentalResponse;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ComponentScan(value = "com.demo.amit")
@AllArgsConstructor
@Component
@Slf4j
public class RentalRuleProcessor {
	
	@Autowired
    private ToolConfig toolConfig;



	public RentalResponse processRentAgreement(RentalRequest rentalRequest) throws DiscountNotInRangeException,
			DuplicateToolRentRequestException, InvalidCheckOutDateException, InvalidRentalDayException {
		validateRequest(rentalRequest);

		RentAgreementDTO rentalAgrement = processRequest(rentalRequest);

		return RentalResponse.builder().agreementDTO(rentalAgrement).build();
	}

	

	private RentAgreementDTO processRequest(RentalRequest rentalRequest) {
		RentAgreementDTO rentAgreement = RentAgreementDTO.builder().customerName(rentalRequest.getCustomerName())
				.checkOutDate(rentalRequest.getCheckOutDate()).requestNoOfDays(rentalRequest.getNoOfDays()).toolsInfo(new ArrayList<>()).build();
		
		setDueDate(rentAgreement, rentalRequest.getNoOfDays());
		rentalRequest.getRentTools().stream().forEach(item -> {
			ToolDTO matchedTool = toolConfig.loadTools().stream().filter(n -> item.getToolCode().equals(n.getToolCode()))
					.findFirst().get();

			long actualNoIfDay = getActualbillingDay(matchedTool, rentalRequest.getCheckOutDate(),
					rentAgreement.getDueDate(), rentalRequest.getNoOfDays());

			ToolDTO toolInstance = ToolDTO.builder().toolCode(matchedTool.getToolCode()).toolId(matchedTool.getToolId())
					.toolName(matchedTool.getToolName()).toolType(matchedTool.getToolType())
					.brand(matchedTool.getBrand()).freeOnWeekends(matchedTool.isFreeOnWeekends())
					.freeOnHolidays(matchedTool.isFreeOnHolidays())
					.discountInPercentage(rentalRequest.getDiscountInPercentage())
					.rentPerDay(matchedTool.getRentPerDay())
					.amountBeforeDiscount(matchedTool.getRentPerDay().multiply(new BigDecimal(actualNoIfDay)))
					.effectiveNofOfdays(actualNoIfDay)
					.build();

			toolInstance.setDiscountAmount((toolInstance.getAmountBeforeDiscount()
					.multiply(new BigDecimal(toolInstance.getDiscountInPercentage()).divide(new BigDecimal(100)))));
			toolInstance.getDiscountAmount().setScale(2, RoundingMode.HALF_UP);
			toolInstance.setAmountAfterDiscount(
					toolInstance.getAmountBeforeDiscount().subtract(toolInstance.getDiscountAmount()));
			toolInstance.getAmountAfterDiscount().setScale(2, RoundingMode.HALF_DOWN);
			rentAgreement.setTotalAmount(rentAgreement.getTotalAmount().add(toolInstance.getAmountAfterDiscount()));
			rentAgreement.setTotalDiscount(rentAgreement.getTotalDiscount().add(toolInstance.getDiscountAmount()));
			rentAgreement.getToolsInfo().add(toolInstance);
			
		});

		return rentAgreement;
	}

	private void setDueDate(RentAgreementDTO rentalAgrement, int noofDays) {
		LocalDate startDate = rentalAgrement.getCheckOutDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate endDate = startDate.plusDays((noofDays-1));

		// Get the day of the week
		
		rentalAgrement.setDueDate(Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
	}

	private long getActualbillingDay(ToolDTO matchedTool, Date checkOutDate, Date dueDate, long noOfDays) {
		// TODO Auto-generated method stub
		LocalDate startDate = checkOutDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate endDate = startDate.plusDays(noOfDays-1);
		
		if (matchedTool.isFreeOnWeekends()) {
		
			noOfDays = noOfDays - getNoOfWeekends(startDate, endDate);
		}
		if (matchedTool.isFreeOnHolidays()) {
			int startYear = startDate.getYear();
			int endYear = endDate.getYear();

			for (; startYear <= endYear; startYear++) {

				LocalDate July4th = LocalDate.parse(startYear + "-07-04");
				DayOfWeek dayOfWeek = July4th.getDayOfWeek();
				if (dayOfWeek.equals(DayOfWeek.SATURDAY))
					noOfDays--;
				else if (dayOfWeek.equals(DayOfWeek.SUNDAY))
					noOfDays++;
				else if (July4th.isAfter(startDate) && July4th.isBefore(endDate))
					noOfDays--;
			}
			LocalDate firstMondayDate = getFirstSeptMondayDate(startYear);
			if (firstMondayDate.isAfter(startDate) && firstMondayDate.isBefore(endDate)) {
				noOfDays--;
			}
			
		}
		return noOfDays;
	}

	private LocalDate getFirstSeptMondayDate(int year) {
		LocalDate firstSeptember = LocalDate.of(year, 9, 1);
		LocalDate firstMondayOfSeptember = firstSeptember.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
		return firstMondayOfSeptember;
	}

	private long getNoOfWeekends(LocalDate startDate, LocalDate endDate) {
		endDate = endDate.plusDays(1);
		Set<DayOfWeek> weekend = EnumSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
		long weekendsBetween = startDate.datesUntil(endDate).filter(date -> weekend.contains(date.getDayOfWeek()))
				.count();
		return weekendsBetween;
	}

	private void validateRequest(RentalRequest rentalRequest) throws DiscountNotInRangeException,
			DuplicateToolRentRequestException, InvalidCheckOutDateException, InvalidRentalDayException {
		if (rentalRequest.getDiscountInPercentage() < 0 || rentalRequest.getDiscountInPercentage() > 100) {
			throw new DiscountNotInRangeException("Discount percentage is not in the range of 0-100");
		}
		if (rentalRequest.getNoOfDays() <= 0) {
			throw new InvalidRentalDayException("Invalid request days exception");
		}
		if (rentalRequest.getRentTools().size() != listToSet(rentalRequest.getRentTools().stream()).size()) {
			throw new DuplicateToolRentRequestException("Duplicate tools in request");
		}
//		if (rentalRequest.getCheckOutDate() == null || rentalRequest.getCheckOutDate()
//				.before(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()))) {
//
//			throw new InvalidCheckOutDateException("Date specifieed in checkout is invalid");
//		}
	}

	@SuppressWarnings("unchecked")
	private Set<ToolInfo> listToSet(Stream<ToolInfo> stream) {

		// Set to store the duplicate elements
		Set<ToolInfo> items = new HashSet<>();

		// Return the set of duplicate elements
		return stream.filter(n -> items.add(n)).collect(Collectors.toSet());
	}
}
