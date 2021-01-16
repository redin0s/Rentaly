package com.folders.rentaly.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@SuppressWarnings("unused")
public class ServiceResponse<T> {
	
	private String status;
	private T data;

}