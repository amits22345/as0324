package com.demo.amit.apps.rental.controller;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.demo.amit.apps.rental.dto.RentAgreementDTO;
import com.demo.amit.apps.rental.dto.RentalRequest;
import com.demo.amit.apps.rental.exception.DiscountNotInRangeException;
import com.demo.amit.apps.rental.exception.DuplicateToolRentRequestException;
import com.demo.amit.apps.rental.exception.InvalidCheckOutDateException;
import com.demo.amit.apps.rental.exception.InvalidRentalDayException;
import com.demo.amit.apps.rental.service.RentalService;

@RestController
@RequestMapping("/api/rent-tools")
public class RentalController {

	private RentalService rentalService;

	@Autowired
	public RentalController(RentalService rentalService) {
		this.rentalService = rentalService;
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public RentAgreementDTO create(@RequestBody RentalRequest request)
			throws DiscountNotInRangeException, DuplicateToolRentRequestException, InvalidCheckOutDateException, InvalidRentalDayException {
		RentAgreementDTO dto= rentalService.createRentAgrement(request);
		printRentAgreement(dto);
		return dto;
	}
	private void printRentAgreement(RentAgreementDTO dto) {
		DecimalFormat df = new DecimalFormat("'$'0.00", DecimalFormatSymbols.getInstance(Locale.US));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println("------------------------------------------------------------------------------------------------");
		System.out.println("---------------------------------Rent Agreement ------------------------------------------------");
		System.out.println("------------------------------------------------------------------------------------------------");
		System.out.println(String.format("Agreement Id         : %s",dto.getAggrementId()));
		System.out.println(String.format("Customer name        : %s",dto.getCustomerName()));
		System.out.println(String.format("Checkout Date        : %s",sdf.format(dto.getCheckOutDate())));
		System.out.println(String.format("Due Date             : %s",sdf.format(dto.getDueDate())));
		System.out.println(String.format("Requested No of days : %d",dto.getRequestNoOfDays()));
		System.out.println("");
		System.out.println("Rental App List      : ");
		System.out.println("------------------------------------------------------------------------------------------------");
		dto.getToolsInfo().forEach(item->{
			System.out.println("Sr No                          		:"+dto.getToolsInfo().indexOf(item)+1);
			System.out.println("App Code                          	:"+item.getToolCode());
			System.out.println("App Type                          	:"+item.getToolType());
			System.out.println("Brand                          		:"+item.getBrand());
			System.out.println("Rent per Day                        	:"+df.format(item.getRentperDay()));
			System.out.println("Effective rental days               	:"+item.getEffectiveNofOfdays());
			System.out.println("Free On weekend and holiday         	:"+item.isFreeOnWeekendsOrHolidays());
			System.out.println("Amount before discount              	:"+df.format(item.getAmountBeforeDiscount()));
			System.out.println("Discount Percentage                 	:"+item.getDiscountInPercentage()+"%");
			System.out.println("Discount Amount                     	:"+df.format(item.getDiscountAmount()));
			System.out.println("Amount After Discount               	:"+df.format(item.getAmountAfterDiscount()));
			
			System.out.println();
				
		});
		System.out.println("------------------------------------------------------------------------------------------------");
		System.out.println("Total Discount :"+df.format(dto.getTotalDiscount()));
		System.out.println("Total Amount :"+df.format(dto.getTotalAmount()));
		System.out.println("------------------------------------------------------------------------------------------------");
		
		
		
		
		
		
	}
}
