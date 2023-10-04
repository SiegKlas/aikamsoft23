package ru.michael.aikamsoft23.json.input;

import lombok.Data;
import ru.michael.aikamsoft23.json.Criteria;

import java.util.List;

@Data
public class CriteriasRequest {
    private List<Criteria> criterias;
}
