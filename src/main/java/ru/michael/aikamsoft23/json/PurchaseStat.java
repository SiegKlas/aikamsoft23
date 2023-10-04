package ru.michael.aikamsoft23.json;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class PurchaseStat {
    private String name;
    private BigDecimal expenses;
}
