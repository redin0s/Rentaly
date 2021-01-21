package com.folders.rentaly.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "realty", schema = "prova")
public class Realty{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank
    @Column(columnDefinition = "character varying(255)")
    private String display_name;

    private Double latitude;

    private Double longitude;

    @Column(columnDefinition = "character varying(255)")
    private String type;

    @Min(1)
    private Integer square_meters;

    @Min(0)
    private Integer max_holders;
    
    @JoinColumn(name = "owner_id")
    @ManyToOne
    private User owner;
}