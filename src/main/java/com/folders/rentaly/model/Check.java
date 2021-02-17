package com.folders.rentaly.model;

import java.time.LocalDate;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Check {
    private Integer id;

    private String check_type;

    @DecimalMin("0.0")
    @Digits(integer=6, fraction =2)
    private Float cost;

    private LocalDate expire;

	private Rent rent;
}