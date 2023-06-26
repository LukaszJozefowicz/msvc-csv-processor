package com.ljozefowicz.csvprocessor.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class DataEntry {
    private LocalDate transactionDate;
    private BigDecimal amount;
    private String currency;
    private String transactionType;
    private String recipient;
    private String sender;
    private String transactionDescription;

}
