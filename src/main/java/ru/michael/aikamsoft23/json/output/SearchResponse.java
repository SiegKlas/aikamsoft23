package ru.michael.aikamsoft23.json.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.michael.aikamsoft23.json.SearchResult;

import java.util.List;

@Data
@AllArgsConstructor
public class SearchResponse {
    private OutputType type;
    private List<SearchResult> results;
}
