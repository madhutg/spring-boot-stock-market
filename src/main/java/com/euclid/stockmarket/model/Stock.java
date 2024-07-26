package com.euclid.stockmarket.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String symbol;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private int lastDividend;

    @Column
    private double fixedDividend;

    @Column(nullable = false)
    private int parValue;

}
