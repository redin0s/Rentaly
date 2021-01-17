package com.folders.rentaly.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Realty {
	private int id;
	private User owner = null;
	private String location = "";
	private String type = "";
}