package ru.michael.aikamsoft23.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.michael.aikamsoft23.data.Purchase;

import java.time.LocalDate;
import java.util.List;

public interface PurchaseRepository extends CrudRepository<Purchase, Long> {
    @Query("SELECT c.firstName, c.lastName, p.name, SUM(p.price) " +
            "FROM Purchase pu " +
            "JOIN pu.customer c " +
            "JOIN pu.product p " +
            "WHERE pu.purchaseDate BETWEEN :startDate AND :endDate " +
            "GROUP BY c.firstName, c.lastName, p.name " +
            "ORDER BY c.firstName, c.lastName, p.name ASC")
    List<Object[]> getCustomerStatistics(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
