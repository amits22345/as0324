package com.demo.amit.apps.rental.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
@Document(value = "AGREEMENT")
public class RentalAgrement {
	@Id
	private String aggrementId;
	private String customerName;
	private Date checkOutDate;
	private List<Tool> toolsInfo;
	@Builder.Default
	private BigDecimal totalAmount = new BigDecimal("0.00");
	@Builder.Default
	private BigDecimal totalDiscount= new BigDecimal("0.00");
	private Date dueDate;
	
}
