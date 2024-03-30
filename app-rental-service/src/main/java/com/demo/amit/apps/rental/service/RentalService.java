package com.demo.amit.apps.rental.service;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.amit.apps.rental.dto.RentAgreementDTO;
import com.demo.amit.apps.rental.dto.RentalRequest;
import com.demo.amit.apps.rental.dto.ToolDTO;
import com.demo.amit.apps.rental.exception.DiscountNotInRangeException;
import com.demo.amit.apps.rental.exception.DuplicateToolRentRequestException;
import com.demo.amit.apps.rental.exception.InvalidCheckOutDateException;
import com.demo.amit.apps.rental.exception.InvalidRentalDayException;
import com.demo.amit.apps.rental.model.RentalAgrement;
import com.demo.amit.apps.rental.repository.RentalRepository;
import com.demo.amit.apps.rental.validator.RentalRuleProcessor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RentalService {
	
	@Autowired
	private RentalRuleProcessor rentalRuleProcessor;
	@Autowired
	private RentalRepository rentalRepository;
	
	public RentAgreementDTO createRentAgrement(RentalRequest request) throws DiscountNotInRangeException, DuplicateToolRentRequestException, InvalidCheckOutDateException, InvalidRentalDayException {
		// TODO Auto-generated method stub
		RentalAgrement agreeAgrement= rentalRuleProcessor.processRentAgreement(request);
		agreeAgrement= rentalRepository.save(agreeAgrement);
		return entityToDTO(agreeAgrement, request.getNoOfDays());
	}
	private RentAgreementDTO entityToDTO(RentalAgrement rentalAgrement, long requestNoOfdays) {
		// TODO Auto-generated method stub
		RentAgreementDTO dto = RentAgreementDTO.builder()
				.customerName(rentalAgrement.getCustomerName())
				.aggrementId(rentalAgrement.getAggrementId())
				.requestNoOfDays(requestNoOfdays)
				.checkOutDate(rentalAgrement.getCheckOutDate())
				.dueDate(rentalAgrement.getDueDate())
				.totalAmount(rentalAgrement.getTotalAmount())
				.totalDiscount(rentalAgrement.getTotalDiscount())
				.toolsInfo(rentalAgrement.getToolsInfo().stream().map(item->
					ToolDTO.builder()
						.amountAfterDiscount(item.getAmountAfterDiscount())
						.amountBeforeDiscount(item.getAmountAfterDiscount())
						.brand(item.getBrand())
						.discountAmount(item.getDiscountAmount())
						.discountInPercentage(item.getDiscountInPercentage())
						.effectiveNofOfdays(item.getEffectiveNofOfdays())
						.freeOnWeekendsOrHolidays(item.isFreeOnWeekendsOrHolidays())
						.rentperDay(item.getRentperDay())
						.toolCode(item.getToolCode())
						.toolId(item.getToolId())
						.toolName(item.getToolName())
						.toolType(item.getToolType())
					.build()).collect(Collectors.toList()))
				.build();
		
		return dto;
	}
	
	

}
