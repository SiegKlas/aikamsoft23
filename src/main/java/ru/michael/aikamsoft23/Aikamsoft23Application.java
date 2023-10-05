package ru.michael.aikamsoft23;

import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.michael.aikamsoft23.json.input.CriteriasRequest;
import ru.michael.aikamsoft23.json.input.StatRequest;
import ru.michael.aikamsoft23.json.output.ErrorResponse;
import ru.michael.aikamsoft23.json.output.OutputType;
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
            if (args.length < 3) {
                System.err.println("Usage: java -jar aikamsoft23.jar request_type input.json output.json");
                System.exit(1);
            }
            String operationType = args[0];
            String inputFile = args[1];
            String outputFile = args[2];

            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
            if (Objects.equals(operationType, "stat")) {
                try {
                    StatRequest statRequest = objectMapper.readValue(new File(inputFile), StatRequest.class);
                    var e = customerController.findStat(statRequest);
                    objectMapper.writeValue(new File(outputFile), e.getBody());
                } catch (DatabindException exception) {
                    objectMapper.writeValue(
                            new File(outputFile),
                            new ErrorResponse(OutputType.error, "Wrong date input")
                    );
                }
            } else if (Objects.equals(operationType, "search")) {
                try {
                    CriteriasRequest criteriasRequest = objectMapper.readValue(new File(inputFile),
                            CriteriasRequest.class);
                    var e = customerController.searchCustomers(criteriasRequest);
                    objectMapper.writeValue(new File(outputFile), e.getBody());
                } catch (DatabindException exception) {
                    objectMapper.writeValue(
                            new File(outputFile),
                            new ErrorResponse(OutputType.error, "Wrong search input")
                    );
                }
            }

            System.exit(0);
        };
    }
}
