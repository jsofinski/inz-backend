package com.pwr.inzynierka.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDTO {
    private final Long id;
    private final String name;
    private final String publicKey;
}
