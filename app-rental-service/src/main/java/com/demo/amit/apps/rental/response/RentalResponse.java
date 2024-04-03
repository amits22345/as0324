package com.demo.amit.apps.rental.response;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.demo.amit.apps.rental.dto.RentAgreementDTO;
import com.demo.amit.apps.rental.dto.ToolDTO;
import com.demo.amit.apps.rental.dto.ToolInfo;

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
public class RentalResponse {

	private RentAgreementDTO agreementDTO;

}
