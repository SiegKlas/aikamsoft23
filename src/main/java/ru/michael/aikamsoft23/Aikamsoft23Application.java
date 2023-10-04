package ru.michael.aikamsoft23;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.michael.aikamsoft23.data.Customer;
import ru.michael.aikamsoft23.data.Product;
import ru.michael.aikamsoft23.data.Purchase;
import ru.michael.aikamsoft23.repositories.CustomerRepository;
import ru.michael.aikamsoft23.repositories.ProductRepository;
import ru.michael.aikamsoft23.repositories.PurchaseRepository;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootApplication
public class Aikamsoft23Application {

    public static void main(String[] args) {
        SpringApplication.run(Aikamsoft23Application.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(CustomerRepository customerRepository, ProductRepository productRepository,
                                        PurchaseRepository purchaseRepository) {
        return args -> {
            customerRepository.deleteAll();
            Customer customer1 = new Customer("Иван", "Иванов");
            Customer customer2 = new Customer("Петр", "Петров");
            Customer customer3 = new Customer("Сидр", "Сидоров");
            Customer customer4 = new Customer("Абоба", "Абобов");
            customerRepository.save(customer1);
            customerRepository.save(customer2);
            customerRepository.save(customer3);
            customerRepository.save(customer4);

            productRepository.deleteAll();
            Product product1 = new Product("Минеральная вода", BigDecimal.valueOf(1.50));
            Product product2 = new Product("Сок", BigDecimal.valueOf(2.50));
            Product product3 = new Product("Хлеб", BigDecimal.valueOf(0.50));
            productRepository.save(product1);
            productRepository.save(product2);
            productRepository.save(product3);

            purchaseRepository.deleteAll();
            purchaseRepository.save(new Purchase(customer1, product1, LocalDate.now()));
            purchaseRepository.save(new Purchase(customer1, product2, LocalDate.now()));
            purchaseRepository.save(new Purchase(customer2, product1, LocalDate.now()));
            purchaseRepository.save(new Purchase(customer2, product2, LocalDate.now()));
            purchaseRepository.save(new Purchase(customer2, product3, LocalDate.now()));
            purchaseRepository.save(new Purchase(customer3, product3, LocalDate.now()));
            purchaseRepository.save(new Purchase(customer4, product3, LocalDate.now()));
        };
    }
}
