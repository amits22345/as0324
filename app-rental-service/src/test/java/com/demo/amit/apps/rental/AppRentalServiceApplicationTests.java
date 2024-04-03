package com.demo.amit.apps.rental;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.demo.amit.apps.rental.dto.RentAgreementDTO;
import com.demo.amit.apps.rental.dto.RentalRequest;
import com.demo.amit.apps.rental.dto.ToolInfo;
import com.demo.amit.apps.rental.exception.DiscountNotInRangeException;
import com.demo.amit.apps.rental.exception.DuplicateToolRentRequestException;
import com.demo.amit.apps.rental.exception.InvalidCheckOutDateException;
import com.demo.amit.apps.rental.exception.InvalidRentalDayException;
import com.demo.amit.apps.rental.response.RentalResponse;
import com.demo.amit.apps.rental.validator.RentalRuleProcessor;

@RunWith(SpringRunner.class)
@SpringBootTest
class AppRentalServiceApplicationTests {

	@Autowired
	private RentalRuleProcessor rentalRuleProcessor;

	@Test
	public void createRenatalAgreementTest1()throws DiscountNotInRangeException, DuplicateToolRentRequestException, InvalidCheckOutDateException, InvalidRentalDayException {
		assertThrows(DiscountNotInRangeException.class, () -> {
			try {

				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
				ToolInfo info = ToolInfo.builder().toolCode("JAKR").build();
				RentalRequest request = RentalRequest.builder()
						.customerName("Amit Sharma")
						.discountInPercentage(101)
						.checkOutDate(sdf.parse("9/3/15"))
						.noOfDays(5)
						.rentTools(new ArrayList<>(Arrays.asList(info)))
						.build();

				RentalResponse dto = rentalRuleProcessor.processRentAgreement(request);
				assertNotNull(dto);
				printRentAgreement(dto.getAgreementDTO());

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	@Test
	public void createRenatalAgreementTest2() throws DiscountNotInRangeException, DuplicateToolRentRequestException, InvalidCheckOutDateException, InvalidRentalDayException {
		
			try {

				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
				ToolInfo info = ToolInfo.builder().toolCode("LADW").build();
				RentalRequest request = RentalRequest.builder()
						.customerName("Amit Sharma")
						.discountInPercentage(10)
						.checkOutDate(sdf.parse("7/2/20"))
						.noOfDays(3)
						.rentTools(new ArrayList<>(Arrays.asList(info)))
						.build();

				RentalResponse dto = rentalRuleProcessor.processRentAgreement(request);
				assertNotNull(dto);
				printRentAgreement(dto.getAgreementDTO());

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	@Test
	public void createRenatalAgreementTest3() throws DiscountNotInRangeException, DuplicateToolRentRequestException, InvalidCheckOutDateException, InvalidRentalDayException {
		
			try {

				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
				ToolInfo info = ToolInfo.builder().toolCode("CHNS").build();
				RentalRequest request = RentalRequest.builder()
						.customerName("Amit Sharma")
						.discountInPercentage(25)
						.checkOutDate(sdf.parse("7/2/15"))
						.noOfDays(5)
						.rentTools(new ArrayList<>(Arrays.asList(info)))
						.build();

				RentalResponse dto = rentalRuleProcessor.processRentAgreement(request);
				assertNotNull(dto);
				printRentAgreement(dto.getAgreementDTO());

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	@Test
	public void createRenatalAgreementTest4() throws DiscountNotInRangeException, DuplicateToolRentRequestException, InvalidCheckOutDateException, InvalidRentalDayException {
		
			try {

				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
				ToolInfo info = ToolInfo.builder().toolCode("JAKD").build();
				RentalRequest request = RentalRequest.builder()
						.customerName("Amit Sharma")
						.discountInPercentage(0)
						.checkOutDate(sdf.parse("9/3/15"))
						.noOfDays(6)
						.rentTools(new ArrayList<>(Arrays.asList(info)))
						.build();

				RentalResponse dto = rentalRuleProcessor.processRentAgreement(request);
				assertNotNull(dto);
				printRentAgreement(dto.getAgreementDTO());

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	@Test
	public void createRenatalAgreementTest5() throws DiscountNotInRangeException, DuplicateToolRentRequestException, InvalidCheckOutDateException, InvalidRentalDayException {
		
			try {

				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
				ToolInfo info = ToolInfo.builder().toolCode("JAKR").build();
				RentalRequest request = RentalRequest.builder()
						.customerName("Amit Sharma")
						.discountInPercentage(0)
						.checkOutDate(sdf.parse("7/2/15"))
						.noOfDays(6)
						.rentTools(new ArrayList<>(Arrays.asList(info)))
						.build();

				RentalResponse dto = rentalRuleProcessor.processRentAgreement(request);
				assertNotNull(dto);
				printRentAgreement(dto.getAgreementDTO());

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	@Test
	public void createRenatalAgreementTest6() throws DiscountNotInRangeException, DuplicateToolRentRequestException, InvalidCheckOutDateException, InvalidRentalDayException {
		
			try {

				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
				ToolInfo info = ToolInfo.builder().toolCode("JAKR").build();
				RentalRequest request = RentalRequest.builder()
						.customerName("Amit Sharma")
						.discountInPercentage(50)
						.checkOutDate(sdf.parse("7/2/20"))
						.noOfDays(4)
						.rentTools(new ArrayList<>(Arrays.asList(info)))
						.build();

				RentalResponse dto = rentalRuleProcessor.processRentAgreement(request);
				assertNotNull(dto);
				printRentAgreement(dto.getAgreementDTO());

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	@Test
	public void createRenatalAgreementTestMultipleTool() throws DiscountNotInRangeException, DuplicateToolRentRequestException, InvalidCheckOutDateException, InvalidRentalDayException {
		
			try {

				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
				ToolInfo info = ToolInfo.builder().toolCode("JAKR").build();
				ToolInfo info2 = ToolInfo.builder().toolCode("JAKD").build();
				RentalRequest request = RentalRequest.builder()
						.customerName("Amit Sharma")
						.discountInPercentage(50)
						.checkOutDate(sdf.parse("7/2/20"))
						.noOfDays(4)
						.rentTools(new ArrayList<>(Arrays.asList(info, info2)))
						.build();

				RentalResponse dto = rentalRuleProcessor.processRentAgreement(request);
				assertNotNull(dto);
				printRentAgreement(dto.getAgreementDTO());

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	@Test
	public void createRenatalAgreementTest7() throws DiscountNotInRangeException, DuplicateToolRentRequestException, InvalidCheckOutDateException, InvalidRentalDayException {
		assertThrows(InvalidRentalDayException.class, () -> {
			try {

				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
				ToolInfo info = ToolInfo.builder().toolCode("JAKR").build();
				RentalRequest request = RentalRequest.builder()
						.customerName("Amit Sharma")
						.discountInPercentage(50)
						.checkOutDate(sdf.parse("7/2/20"))
						.noOfDays(0)
						.rentTools(new ArrayList<>(Arrays.asList(info)))
						.build();

				RentalResponse dto = rentalRuleProcessor.processRentAgreement(request);
				assertNotNull(dto);
				printRentAgreement(dto.getAgreementDTO());
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	
	private void printRentAgreement(RentAgreementDTO dto) {
		DecimalFormat df = new DecimalFormat("'$'0.00", DecimalFormatSymbols.getInstance(Locale.US));
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
		System.out.println(
				"------------------------------------------------------------------------------------------------");
		System.out.println(
				"---------------------------------Rent Agreement ------------------------------------------------");
		System.out.println(
				"------------------------------------------------------------------------------------------------");
		System.out.println(String.format("Agreement Id         : %s", dto.getAggrementId()));
		System.out.println(String.format("Customer name        : %s", dto.getCustomerName()));
		System.out.println(String.format("Checkout Date        : %s", sdf.format(dto.getCheckOutDate())));
		System.out.println(String.format("Due Date             : %s", sdf.format(dto.getDueDate())));
		System.out.println(String.format("Requested No of days : %d", dto.getRequestNoOfDays()));
		System.out.println("");
		System.out.println("Rental App List      : ");
		System.out.println(
				"------------------------------------------------------------------------------------------------");
		dto.getToolsInfo().forEach(item -> {
			System.out.println("Sr No                          		:" + dto.getToolsInfo().indexOf(item) + 1);
			System.out.println("App Code                          	:" + item.getToolCode());
			System.out.println("App Type                          	:" + item.getToolType());
			System.out.println("Brand                          		:" + item.getBrand());
			System.out.println("Rent per Day                        	:" + df.format(item.getRentPerDay()));
			System.out.println("Effective rental days               	:" + item.getEffectiveNofOfdays());
			System.out.println("Free On weekend and holiday         	:" + item.isFreeOnWeekends());
			System.out.println("Free On holiday         		:" + item.isFreeOnHolidays());
			System.out.println("Amount before discount              	:" + df.format(item.getAmountBeforeDiscount()));
			System.out.println("Discount Percentage                 	:" + item.getDiscountInPercentage() + "%");
			System.out.println("Discount Amount                     	:" + df.format(item.getDiscountAmount()));
			System.out.println("Amount After Discount               	:" + df.format(item.getAmountAfterDiscount()));

			System.out.println();

		});
		System.out.println(
				"------------------------------------------------------------------------------------------------");
		System.out.println("Total Discount :" + df.format(dto.getTotalDiscount()));
		System.out.println("Total Amount :" + df.format(dto.getTotalAmount()));
		System.out.println(
				"------------------------------------------------------------------------------------------------");
	}
}
