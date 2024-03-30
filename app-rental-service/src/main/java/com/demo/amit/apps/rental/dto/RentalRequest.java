package com.demo.amit.apps.rental.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Accessors
@Data
@Builder
public class RentalRequest {
	
	private String customerName;
	private Date checkOutDate;
	private List<ToolInfo> rentTools;
	private int noOfDays;
	private int discountInPercentage;

}
