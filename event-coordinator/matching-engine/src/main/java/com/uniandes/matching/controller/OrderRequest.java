package com.uniandes.matching.controller;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderRequest {
    private String symbol;
    private Integer quantity;
    private BigDecimal price;
    private String type; // "BUY" o "SALE"
    private String userId;
}