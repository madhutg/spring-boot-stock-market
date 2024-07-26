package com.euclid.stockmarket.model;

import com.euclid.stockmarket.constants.enums.TradeIndicator;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Stock stock;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private TradeIndicator indicator; // "BUY" or "SELL"

    @Column(nullable = false)
    private double price;
}
