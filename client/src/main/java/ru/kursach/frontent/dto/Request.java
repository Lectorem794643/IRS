package ru.kursach.frontent.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.kursach.frontent.dto.enams.Status;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Request {
    @JsonProperty("theme")
    private String theme;
    @JsonProperty("body")
    private String body;
    @JsonProperty("date")
    private String date;
    @JsonProperty("status")
    private Status status;

    public Request(String theme, String body){
        this.theme = theme;
        this.body = body;
    }
}
