package com.folders.rentaly.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "insertion", schema = "prova")
public class Insertion {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String description;

    @Column(columnDefinition = "numeric(10,2)")
    private Float cost;

    private LocalDate publish_date;

    @JoinColumn(name = "realty_id")
    @OneToOne
	private Realty realty;
}