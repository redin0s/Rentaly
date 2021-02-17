package com.folders.rentaly.model;

import java.time.LocalDate;

import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Rent {
    private Integer id;

    @Min(0)
    private Integer cost;

    private LocalDate start;

    private LocalDate end;

    private Realty realty;

    private User holder;

    private Boolean active;
}