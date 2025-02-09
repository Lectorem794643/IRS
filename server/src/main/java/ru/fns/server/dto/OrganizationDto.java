package ru.fns.server.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrganizationDto {
    private Long id;
    private String organization_name;
    private String inn;
    private String kpp;
    private String address;
}
