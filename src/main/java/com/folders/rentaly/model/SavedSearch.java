package com.folders.rentaly.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SavedSearch {

    private Integer id;

    private Double longitude;

    private Double latitude;

    private Float min_price;

    private Float max_price;

    private User user;
}