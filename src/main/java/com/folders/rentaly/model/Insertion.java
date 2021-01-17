package com.folders.rentaly.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Insertion {
	private Realty realty = null;
	private String description = "";
	private double cost = 0.0;
}