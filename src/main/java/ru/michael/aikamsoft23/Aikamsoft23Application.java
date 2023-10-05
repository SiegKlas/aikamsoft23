package ru.michael.aikamsoft23;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.michael.aikamsoft23.json.input.CriteriasRequest;
import ru.michael.aikamsoft23.json.input.StatRequest;
import ru.michael.aikamsoft23.rest.CustomerController;

import java.io.File;
import java.util.Objects;

@SpringBootApplication
public class Aikamsoft23Application {

    private final CustomerController customerController;

    @Autowired
    public Aikamsoft23Application(CustomerController customerController) {
        this.customerController = customerController;
    }

    public static void main(String[] args) {
        SpringApplication.run(Aikamsoft23Application.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader() {
        return args -> {
            String operationType = args[0];
            String inputFile = args[1];
            String outputFile = args[2];

            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
            if (Objects.equals(operationType, "stat")) {
                StatRequest statRequest = objectMapper.readValue(new File(inputFile), StatRequest.class);
                var e = customerController.findStat(statRequest);
                objectMapper.writeValue(new File(outputFile), e.getBody());
            } else if (Objects.equals(operationType, "search")) {
                CriteriasRequest criteriasRequest = objectMapper.readValue(new File(inputFile), CriteriasRequest.class);
                var e = customerController.searchCustomers(criteriasRequest);
                objectMapper.writeValue(new File(outputFile), e.getBody());
            }

            System.exit(0);
        };
    }
}
