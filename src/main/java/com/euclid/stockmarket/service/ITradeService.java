package com.euclid.stockmarket.service;

import com.euclid.stockmarket.dto.request.TradeRequest;
import com.euclid.stockmarket.model.Trade;

public interface ITradeService {

    double calculateDividendYield(String symbol, double price);

    double calculatePERatio(String symbol, double price);

    Trade recordTrade(String symbol, TradeRequest tradeRequest);

    double calculateVolumeWeightedStockPrice(String symbol);

    double calculateGBCEAllShareIndex();
}
