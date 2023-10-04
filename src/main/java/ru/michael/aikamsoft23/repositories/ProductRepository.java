package ru.michael.aikamsoft23.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.michael.aikamsoft23.data.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

}
