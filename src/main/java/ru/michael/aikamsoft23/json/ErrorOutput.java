package ru.michael.aikamsoft23.json;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorOutput {
    private String type;
    private String message;
}
