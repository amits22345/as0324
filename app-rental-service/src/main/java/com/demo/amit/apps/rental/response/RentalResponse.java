package com.demo.amit.apps.rental.response;

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

	private String responseString;
}
