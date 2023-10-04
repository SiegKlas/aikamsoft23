package ru.michael.aikamsoft23.json.input;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StatRequest {
    private LocalDate startDate;
    private LocalDate endDate;
}
