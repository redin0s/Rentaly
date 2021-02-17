package com.folders.rentaly.model;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Insertion {
    private Integer id;

    private String description;

    @Min(0)
    private Integer cost;

    private LocalDate publish_date;

    private List<byte[]> images;

    private Boolean is_visible;
}