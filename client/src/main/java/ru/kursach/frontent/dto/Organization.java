package ru.kursach.frontent.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Organization {
    private UUID id;
    private String name;
    private String inn;
    private String kpp;
    private String address;
}
