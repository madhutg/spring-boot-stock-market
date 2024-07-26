package com.euclid.stockmarket.dto.request;

import com.euclid.stockmarket.constants.enums.TradeIndicator;
import com.euclid.stockmarket.model.Stock;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TradeRequest {

    private LocalDateTime timestamp;

    private int quantity;

    private TradeIndicator indicator; // "BUY" or "SELL"

    private double price;
}
