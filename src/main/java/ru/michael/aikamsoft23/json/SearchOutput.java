package ru.michael.aikamsoft23.json;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SearchOutput {
    private String type;
    private List<SearchResult> results;
}
