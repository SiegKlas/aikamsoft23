package ru.michael.aikamsoft23.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.michael.aikamsoft23.data.Customer;

import java.math.BigDecimal;
import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    List<Customer> readCustomersByLastName(String lastname);

    @Query("SELECT p.customer FROM Purchase p WHERE p.product.name = :productName GROUP BY p.customer HAVING COUNT(p) >= :minTimes")
    List<Customer> findCustomersByProductAndMinTimes(@Param("productName") String productName, @Param("minTimes") int minTimes);

    @Query("SELECT p.customer FROM Purchase p GROUP BY p.customer HAVING SUM(p.product.price) >= :minExpenses AND SUM(p.product.price) <= :maxExpenses")
    List<Customer> findCustomersByTotalExpenses(@Param("minExpenses") BigDecimal minExpenses, @Param("maxExpenses") BigDecimal maxExpenses);

    @Query("SELECT p.customer FROM Purchase p GROUP BY p.customer ORDER BY COUNT(*) ASC")
    List<Customer> findCustomersWithFewestPurchases(Pageable pageable);
}