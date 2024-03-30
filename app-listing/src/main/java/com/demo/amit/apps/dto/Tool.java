package com.demo.amit.apps.dto;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;

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
public class Tool {
	@Id
	private String toolId;
	private String toolName;
	private String toolCode;
	private String toolType;
	private String brand;
	private boolean freeOnWeekendsOrHolidays;
	private BigDecimal rentperDay;
}
