package ru.michael.aikamsoft23.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.michael.aikamsoft23.json.CriteriaContainer;
import ru.michael.aikamsoft23.data.Customer;
import ru.michael.aikamsoft23.json.SearchOutput;
import ru.michael.aikamsoft23.json.SearchResult;
import ru.michael.aikamsoft23.repositories.CustomerRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CustomerController {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchCustomers(@RequestBody CriteriaContainer criteriaContainer) {
        List<SearchResult> results = new ArrayList<>();
        for (var criteria : criteriaContainer.getCriterias()) {
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
        return ResponseEntity.ok(new SearchOutput("search", results));
    }
}
