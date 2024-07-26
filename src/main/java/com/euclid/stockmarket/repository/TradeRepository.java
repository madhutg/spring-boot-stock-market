package com.euclid.stockmarket.repository;

import com.euclid.stockmarket.model.Stock;
import com.euclid.stockmarket.model.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {
    List<Trade> findByStockAndTimestampAfter(Stock stock, LocalDateTime timestamp);
}