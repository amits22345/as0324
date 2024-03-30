package com.demo.amit.apps.rental.dto;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
public class RentAgreementDTO {
	
		private String aggrementId;
		private String customerName;
		private Date checkOutDate;
		private long requestNoOfDays;
		private List<ToolDTO> toolsInfo;
		private BigDecimal totalAmount = new BigDecimal("0.00");
		private BigDecimal totalDiscount= new BigDecimal("0.00");
		private Date dueDate;
		
		
		
	
}
