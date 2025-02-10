package ru.fns.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxAssessmentResponseDto {
    private UUID id;
    private String user_name;
    private String organization_name;
    private String tax_type;
    private String summ;
    private LocalDate paying_deadline;
    private String status;
}

