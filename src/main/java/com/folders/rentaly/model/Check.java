package com.folders.rentaly.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Check {
	private Rent rent = null;
	private double cost = 0.0;
	private String type = "";
	private LocalDate expiration = null;
	private boolean paid = false;
}