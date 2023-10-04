package ru.michael.aikamsoft23.json.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.michael.aikamsoft23.json.CustomerStat;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class StatResponse {
    private OutputType type;
    private Integer totalDays;
    private List<CustomerStat> customers;
    private BigDecimal totalExpenses;
    private BigDecimal avgExpenses;
}
