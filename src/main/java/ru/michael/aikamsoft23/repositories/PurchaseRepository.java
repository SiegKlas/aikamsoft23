package ru.michael.aikamsoft23.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.michael.aikamsoft23.data.Purchase;

public interface PurchaseRepository extends CrudRepository<Purchase, Long> {

}
