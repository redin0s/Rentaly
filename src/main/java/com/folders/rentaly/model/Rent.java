package com.folders.rentaly.model;

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
@Table(name = "rent", schema = "prova")
public class Rent {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(columnDefinition = "numeric(10,2)")
    private Float cost;

    private Integer duration;

    @JoinColumn(name = "realty_id")
    @OneToOne
    private Realty realty;

    @JoinColumn(name = "holder_id")
    @OneToOne
    private User holder;
}