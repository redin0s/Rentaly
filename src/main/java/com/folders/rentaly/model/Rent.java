package com.folders.rentaly.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Rent {
	private Realty realty = null;
	private User leaseholder = null;
	private double monthly_check = 0.0;
}