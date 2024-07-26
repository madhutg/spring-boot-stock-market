package com.euclid.stockmarket.constants.enums;

public enum TradeIndicator {
    BUY("BUY"),
    SELL("SELL");
    final String description;

    private TradeIndicator(String description){
        this.description = description;
    }
}
