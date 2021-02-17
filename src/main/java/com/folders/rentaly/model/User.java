package com.folders.rentaly.model;

import javax.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class User {
	private Integer id;

	@Email
	private String email;
	private String password;
	private Boolean active;

}