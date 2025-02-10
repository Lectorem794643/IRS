package ru.fns.server.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class OrganizationDto {
    private UUID id;
    private String organization_name;
    private String inn;
    private String kpp;
    private String address;
}
