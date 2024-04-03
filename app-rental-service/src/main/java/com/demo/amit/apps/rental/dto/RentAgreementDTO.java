package com.demo.amit.apps.rental.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
@NoArgsConstructor
@AllArgsConstructor
@Accessors
@Data
@Builder
public class RentAgreementDTO {
	
	@Default
		private String aggrementId = UUID.randomUUID().toString();
		private String customerName;
		private Date checkOutDate;
		private long requestNoOfDays;
		private List<ToolDTO> toolsInfo;
		@Default
		private BigDecimal totalAmount = new BigDecimal("0.00");
		@Default
		private BigDecimal totalDiscount= new BigDecimal("0.00");
		private Date dueDate;
		
		
		
	
}
