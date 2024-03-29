package com.demo.amit.apps.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

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
@Document(value = "TOOL")
public class Tools {
	@Id
	private String toolId;
	@NonNull
	private String toolName;
	@NonNull
	@Indexed(unique = true)
	private String toolCode;
	@NonNull
	private String toolType;
	@NonNull
	private String brand;
	private boolean freeOnWeekends;
}
