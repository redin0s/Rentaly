package com.folders.rentaly.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "check", schema = "prova")
public class Check {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "character varying(255)")
    private String check_type;

    @Column(columnDefinition = "numeric(10,2)")
    @DecimalMin("0.0")
    @Digits(integer=6, fraction =2)
    private Float cost;

    private LocalDate expire;

    @JoinColumn(name = "rent_id")
    @ManyToOne
	private Rent rent;
}