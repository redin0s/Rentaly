package com.folders.rentaly.model;

import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Realty {

    private Integer id;

    private Double latitude;

    private Double longitude;

	private String type;

    @Min(1)
    private Integer square_meters;

    @Min(0)
    private Integer max_holders;

    @Min(0)
    private Integer current_holders;
    
    private User owner;

    private Insertion insertion;

    private String city;

    private String address;

    private Boolean draft;
}