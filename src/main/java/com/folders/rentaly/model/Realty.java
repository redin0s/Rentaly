package com.folders.rentaly.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "realty", schema = "prova")
public class Realty{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(columnDefinition = "character varying(255)")
    private String location;

    @Column(columnDefinition = "character varying(255)")
    private String type;

    private Integer square_meters;
    private Integer max_holders;
    
    @JoinColumn(name = "owner_id")
    @ManyToOne
    private User owner;
}