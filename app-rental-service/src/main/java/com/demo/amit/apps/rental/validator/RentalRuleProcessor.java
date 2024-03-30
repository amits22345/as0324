package com.demo.amit.apps.rental.validator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.demo.amit.apps.rental.dto.RentAgreementDTO;
import com.demo.amit.apps.rental.dto.RentalRequest;
import com.demo.amit.apps.rental.dto.ToolDTO;
import com.demo.amit.apps.rental.dto.ToolInfo;
import com.demo.amit.apps.rental.exception.DiscountNotInRangeException;
import com.demo.amit.apps.rental.exception.DuplicateToolRentRequestException;
import com.demo.amit.apps.rental.exception.InvalidCheckOutDateException;
import com.demo.amit.apps.rental.exception.InvalidRentalDayException;
import com.demo.amit.apps.rental.model.RentalAgrement;
import com.demo.amit.apps.rental.model.Tool;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RentalRuleProcessor {
	private final WebClient webClient;

	public RentalAgrement processRentAgreement(RentalRequest rentalRequest) throws DiscountNotInRangeException,
			DuplicateToolRentRequestException, InvalidCheckOutDateException, InvalidRentalDayException {
		validateRequest(rentalRequest);

		RentalAgrement rentalAgrement = processRequest(rentalRequest);

		return rentalAgrement;
	}

	

	private RentalAgrement processRequest(RentalRequest rentalRequest) {
		RentalAgrement rentAgreement = RentalAgrement.builder().customerName(rentalRequest.getCustomerName())
				.checkOutDate(rentalRequest.getCheckOutDate()).toolsInfo(new ArrayList<>()).build();

		ToolDTO[] tools = webClient.get().uri("http://localhost:8080/api/app-listing").retrieve()
				.bodyToMono(ToolDTO[].class).block();

		setDueDate(rentAgreement, rentalRequest.getNoOfDays());
		rentalRequest.getRentTools().stream().forEach(item -> {
			ToolDTO matchedTool = Arrays.stream(tools).filter(n -> item.getToolCode().equals(n.getToolCode()))
					.findFirst().get();

			long actualNoIfDay = getActualbillingDay(matchedTool, rentalRequest.getCheckOutDate(),
					rentAgreement.getDueDate(), rentalRequest.getNoOfDays());

			Tool toolInstance = Tool.builder().toolCode(matchedTool.getToolCode()).toolId(matchedTool.getToolId())
					.toolName(matchedTool.getToolName()).toolType(matchedTool.getToolType())
					.brand(matchedTool.getBrand()).freeOnWeekendsOrHolidays(matchedTool.isFreeOnWeekendsOrHolidays())
					.discountInPercentage(rentalRequest.getDiscountInPercentage())
					.rentperDay(matchedTool.getRentperDay())
					.amountBeforeDiscount(matchedTool.getRentperDay().multiply(new BigDecimal(actualNoIfDay)))
					.effectiveNofOfdays(actualNoIfDay).build();

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

	private void setDueDate(RentalAgrement rentalAgrement, int noofDays) {
		LocalDate startDate = rentalAgrement.getCheckOutDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate endDate = startDate.plusDays((noofDays-1));

		// Get the day of the week
		
		rentalAgrement.setDueDate(Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
	}

	private long getActualbillingDay(ToolDTO matchedTool, Date checkOutDate, Date dueDate, long noOfDays) {
		// TODO Auto-generated method stub

		if (matchedTool.isFreeOnWeekendsOrHolidays()) {
			LocalDate startDate = checkOutDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate endDate = startDate.plusDays(noOfDays-1);
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
			noOfDays = noOfDays - getNoOfWeekends(startDate, endDate);
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
