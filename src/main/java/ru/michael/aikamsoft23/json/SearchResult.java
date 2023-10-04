package ru.michael.aikamsoft23.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.michael.aikamsoft23.data.Customer;

import java.util.List;

@Data
@AllArgsConstructor
public class SearchResult {
    private Criteria criteria;
    private List<Customer> results;
}
