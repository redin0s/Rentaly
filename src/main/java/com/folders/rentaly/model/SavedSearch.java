package com.folders.rentaly.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SavedSearch {

    private Integer id;

    private String location;

    private Float min_price;

    private Float max_price;

    private User holder;
}