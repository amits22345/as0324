package com.demo.amit.apps.rental.dto;

import java.math.BigDecimal;
import java.util.Date;

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
public class ToolInfo {
	private String toolCode;
	
	
	private BigDecimal amountBeforeDiscount;
	private BigDecimal amountAfterDiscount;
	
	private BigDecimal discountAmount;
	
}
