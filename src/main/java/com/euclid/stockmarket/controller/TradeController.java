package com.euclid.stockmarket.controller;

import com.euclid.stockmarket.constants.enums.TradeIndicator;
import com.euclid.stockmarket.dto.request.TradeRequest;
import com.euclid.stockmarket.exception.InvalidTradeException;
import com.euclid.stockmarket.model.Trade;
import com.euclid.stockmarket.service.impl.TradeService;
import com.euclid.stockmarket.util.TradeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stocks")
public class TradeController {
    @Autowired
    private TradeUtil tradeUtil;
    @Autowired
    private TradeService tradeService;

    @GetMapping("/{symbol}/dividendYield")
    public double getDividendYield(@PathVariable String symbol, @RequestParam double price) {
        tradeUtil.validatePrice(price);
        return tradeService.calculateDividendYield(symbol, price);
    }

    @GetMapping("/{symbol}/peRatio")
    public double getPERatio(@PathVariable String symbol, @RequestParam double price) {
        tradeUtil.validatePrice(price);
        return tradeService.calculatePERatio(symbol, price);
    }

    @PostMapping("/{symbol}/trade")
    public ResponseEntity<Trade> recordTrade(@PathVariable String symbol, @RequestBody TradeRequest tradeRequest) {
        tradeUtil.validateTradeParameters(symbol,tradeRequest);
        return ResponseEntity.ok(tradeService.recordTrade(symbol,tradeRequest));
    }

    @GetMapping("/{symbol}/volumeWeightedStockPrice")
    public double getVolumeWeightedStockPrice(@PathVariable String symbol) {
        return tradeService.calculateVolumeWeightedStockPrice(symbol);
    }

    @GetMapping("/gbceAllShareIndex")
    public double getGBCEAllShareIndex() {
        return tradeService.calculateGBCEAllShareIndex();
    }
}