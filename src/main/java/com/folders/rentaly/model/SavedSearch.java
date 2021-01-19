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
@Table(name = "saved_search", schema = "prova")
public class SavedSearch {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String location;

    @Column(columnDefinition = "numeric(10,2)")
    private Float min_price;

    @Column(columnDefinition = "numeric(10,2)")
    private Float max_price;

    @JoinColumn(name = "holder_id")
    @ManyToOne
    private User holder;
}