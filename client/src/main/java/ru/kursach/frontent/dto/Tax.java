package ru.kursach.frontent.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.kursach.frontent.dto.enams.TaxStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Tax {
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("organization_name")
    private String organizationName;
    @JsonProperty("summ")
    private double sum;
    @JsonProperty("tax_type")
    private String taxType;
    @JsonProperty("paying_deadline")
    private String payingDeadline;
    @JsonProperty("status")
    private TaxStatus status;
}
