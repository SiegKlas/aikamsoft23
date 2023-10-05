package ru.michael.aikamsoft23.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.michael.aikamsoft23.data.Customer;
import ru.michael.aikamsoft23.json.CustomerStat;
import ru.michael.aikamsoft23.json.PurchaseStat;
import ru.michael.aikamsoft23.json.SearchResult;
import ru.michael.aikamsoft23.json.input.CriteriasRequest;
import ru.michael.aikamsoft23.json.input.StatRequest;
import ru.michael.aikamsoft23.json.output.OutputType;
import ru.michael.aikamsoft23.json.output.SearchResponse;
import ru.michael.aikamsoft23.json.output.StatResponse;
import ru.michael.aikamsoft23.repositories.CustomerRepository;
import ru.michael.aikamsoft23.repositories.PurchaseRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CustomerController {
    private final CustomerRepository customerRepository;
    private final PurchaseRepository purchaseRepository;

    @Autowired
    public CustomerController(CustomerRepository customerRepository, PurchaseRepository purchaseRepository) {
        this.customerRepository = customerRepository;
        this.purchaseRepository = purchaseRepository;
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchCustomers(@RequestBody CriteriasRequest criterias) {
        List<SearchResult> results = new ArrayList<>();
        for (var criteria : criterias.getCriterias()) {
            List<Customer> customers = new ArrayList<>();
            if (criteria.getLastName() != null) {
                customers.addAll(customerRepository.readCustomersByLastName(criteria.getLastName()));
            } else if (criteria.getProductName() != null && criteria.getMinTimes() != null) {
                customers.addAll(customerRepository.findCustomersByProductAndMinTimes(
                        criteria.getProductName(),
                        criteria.getMinTimes())
                );
            } else if (criteria.getMinExpenses() != null && criteria.getMaxExpenses() != null) {
                customers.addAll(customerRepository.findCustomersByTotalExpenses(
                        criteria.getMinExpenses(), criteria.getMaxExpenses())
                );
            } else if (criteria.getBadCustomers() != null) {
                customers.addAll(customerRepository.findCustomersWithFewestPurchases(
                        Pageable.ofSize(criteria.getBadCustomers()))
                );
            } else {
                throw new RuntimeException();
            }
            results.add(new SearchResult(criteria, customers));
        }
        return ResponseEntity.ok(new SearchResponse(OutputType.search, results));
    }

    @GetMapping("/stat")
    public ResponseEntity<?> findStat(@RequestBody StatRequest statRange) {
        LocalDate from = statRange.getStartDate();
        LocalDate to = statRange.getEndDate();
        Integer daysDiff = (int) ChronoUnit.DAYS.between(from, to);
        List<Object[]> objects = purchaseRepository.getCustomerStatistics(from, to);
        Map<String, List<PurchaseStat>> namePurchase = new HashMap<>();
        for (var object : objects) {
            String name = object[0] + " " + object[1];
            String purchaseName = (String) object[2];
            BigDecimal expenses = (BigDecimal) object[3];

            PurchaseStat purchaseStat = new PurchaseStat(purchaseName, expenses);
            if (namePurchase.containsKey(name)) {
                namePurchase.get(name).add(purchaseStat);
            } else {
                namePurchase.put(name, new ArrayList<>(List.of(purchaseStat)));
            }
        }
        List<CustomerStat> customers = new ArrayList<>();
        namePurchase.forEach((k, v) -> {
            BigDecimal totalExpenses =
                    v.stream().map(PurchaseStat::getExpenses).reduce(BigDecimal::add).orElse(new BigDecimal(0));
            customers.add(new CustomerStat(k, v, totalExpenses));
        });
        BigDecimal totalExpenses =
                customers.stream().map(CustomerStat::getTotalExpenses).reduce(BigDecimal::add).orElse(new BigDecimal(0));
        BigDecimal avgExpenses = BigDecimal.ZERO;
        if (!customers.isEmpty()) {
            avgExpenses = totalExpenses.divide(new BigDecimal(customers.size()), RoundingMode.CEILING);
        }
        return ResponseEntity.ok(
                new StatResponse(OutputType.stat, daysDiff, customers, totalExpenses, avgExpenses)
        );
    }
}
