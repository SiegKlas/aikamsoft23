package ru.michael.aikamsoft23.json;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class CustomerStat {
    private String name;
    private List<PurchaseStat> purchases;
    private BigDecimal totalExpenses;
}
