package ru.michael.aikamsoft23.json.output;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private OutputType type;
    private String message;
}
