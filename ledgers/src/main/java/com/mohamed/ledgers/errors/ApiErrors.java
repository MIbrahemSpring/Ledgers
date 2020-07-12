package com.mohamed.ledgers.errors;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrors {
	
	public List<ApiError> errors;
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public class ApiError{
		
		public String code;
		
		public String error;
		
		public String description;
		
		
	}

}
