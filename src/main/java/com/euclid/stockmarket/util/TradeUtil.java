package com.euclid.stockmarket.util;

import com.euclid.stockmarket.constants.enums.TradeIndicator;
import com.euclid.stockmarket.dto.request.TradeRequest;
import com.euclid.stockmarket.exception.InvalidTradeException;
import org.springframework.stereotype.Component;

@Component
public class TradeUtil {

    public void validatePrice(double price) {
        if (price <= 0) {
            throw new InvalidTradeException("Price must be greater than zero.");
        }
    }

    public void validateTradeParameters(String symbol, TradeRequest tradeRequest) {
        if (symbol == null || symbol.isEmpty()) {
            throw new InvalidTradeException("Stock symbol must be provided.");
        }
        if (tradeRequest.getQuantity() <= 0) {
            throw new InvalidTradeException("Quantity must be greater than zero.");
        }
        if (tradeRequest.getIndicator() == null || (!tradeRequest.getIndicator().equals(TradeIndicator.BUY) && !tradeRequest.getIndicator().equals(TradeIndicator.SELL))) {
            throw new InvalidTradeException("Indicator must be 'buy' or 'sell'.");
        }
        validatePrice(tradeRequest.getPrice());
    }
}

