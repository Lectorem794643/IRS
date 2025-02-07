package ru.kursach.frontent.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.kursach.frontent.dto.enams.UserRole;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    @JsonProperty("id")
    private UUID id;
    @NonNull @JsonProperty("name")
    private String name;
    @NonNull @JsonProperty("login")
    private String login;
    @NonNull @JsonProperty("email")
    private String email;
    @NonNull @JsonProperty("tel")
    private String tel;
    @NonNull @JsonProperty("authority")
    private UserRole authority;
}
