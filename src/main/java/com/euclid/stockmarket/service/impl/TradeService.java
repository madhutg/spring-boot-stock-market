package com.euclid.stockmarket.service.impl;

import com.euclid.stockmarket.constants.enums.TradeIndicator;
import com.euclid.stockmarket.dto.request.TradeRequest;
import com.euclid.stockmarket.exception.StockNotFoundException;
import com.euclid.stockmarket.model.Stock;
import com.euclid.stockmarket.model.Trade;
import com.euclid.stockmarket.repository.StockRepository;
import com.euclid.stockmarket.repository.TradeRepository;
import com.euclid.stockmarket.service.ITradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class TradeService implements ITradeService {
    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private TradeRepository tradeRepository;

    @Override
    public double calculateDividendYield(String symbol, double price) {
        Stock stock = stockRepository.findBySymbol(symbol).orElseThrow(() -> new StockNotFoundException("Stock not found"));;
        if (stock.getType().equals("Common")) {
            return stock.getLastDividend() / price;
        } else {
            return (stock.getFixedDividend() * stock.getParValue()) / price;
        }
    }

    @Override
    public double calculatePERatio(String symbol, double price) {
        Stock stock = stockRepository.findBySymbol(symbol).orElseThrow(() -> new StockNotFoundException("Stock not found"));
        return price / stock.getLastDividend();
    }

    @Override
    public Trade recordTrade(String symbol, TradeRequest tradeRequest) {
        Stock stock = stockRepository.findBySymbol(symbol).orElseThrow(() -> new StockNotFoundException("Stock not found"));
        Trade trade = new Trade();
        trade.setStock(stock);
        trade.setTimestamp(tradeRequest.getTimestamp());
        trade.setQuantity(tradeRequest.getQuantity());
        trade.setIndicator(tradeRequest.getIndicator());
        trade.setPrice(tradeRequest.getPrice());
        return tradeRepository.save(trade);
    }

    @Override
    public double calculateVolumeWeightedStockPrice(String stockSymbol) {
        Stock stock = stockRepository.findBySymbol(stockSymbol).orElseThrow(() -> new StockNotFoundException("Stock not found"));
        LocalDateTime tenMinutesAgo = LocalDateTime.now().minusMinutes(10);

        List<Trade> trades = tradeRepository.findByStockAndTimestampAfter(stock, tenMinutesAgo);

        if (trades.isEmpty()) {
            return 0.0;
        }

        double totalTradePriceQuantity = trades.stream()
                .mapToDouble(trade -> trade.getPrice() * trade.getQuantity())
                .sum();
        int totalQuantity = trades.stream()
                .mapToInt(Trade::getQuantity)
                .sum();

        return totalQuantity == 0 ? 0.0 : totalTradePriceQuantity / totalQuantity;
    }

    @Override
    public double calculateGBCEAllShareIndex() {
        List<Stock> stocks = stockRepository.findAll();

        if (stocks.isEmpty()) {
            return 0.0;
        }

        List<Double> volumeWeightedPrices = stocks.stream()
                .map(stock -> calculateVolumeWeightedStockPrice(stock.getSymbol()))
                .filter(price -> price > 0)
                .toList();

        if (volumeWeightedPrices.isEmpty()) {
            return 0.0;
        }

        double product = volumeWeightedPrices.stream()
                .reduce(1.0, (a, b) -> a * b);

        return Math.pow(product, 1.0 / volumeWeightedPrices.size());
    }

}
