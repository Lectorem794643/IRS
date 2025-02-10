package ru.fns.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxAssessmentRequestDto {
    private String user_name;
    private String organization_name;
    private String tax_type;
    private String summ;
    private LocalDate paying_deadline;
    private String status;
}