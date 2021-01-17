package com.folders.rentaly.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Search {
	private String location = "";
	private String type = "";
	private int max_distance = 0;
	private int price_min = 0;
	private int price_max = 0;
}