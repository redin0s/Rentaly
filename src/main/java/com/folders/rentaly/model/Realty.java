package com.folders.rentaly.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "realty", schema = "prova")
public class Realty implements Serializable{
    private static final long serialVersionUID = -4955414593716130737L;

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double latitude;

    private Double longitude;

    @Column(columnDefinition = "character varying(255)")
	private String type;



    @Min(1)
    private Integer square_meters;

    @Min(0)
    private Integer max_holders;

    @Column(columnDefinition = "integer default 0")
    @Min(0)
    private Integer current_holders;
    
    @JoinColumn(name = "owner_id")
    @ManyToOne
    private User owner;

    @JoinColumn(name = "insertion_id")
    @OneToOne(cascade = CascadeType.ALL)
    private Insertion insertion;

    @Column(columnDefinition = "character varying(255)")
    private String city;

    @Column(columnDefinition = "character varying(255)")
    private String address;

    @Column(name = "is_draft")
    private Boolean draft;
}