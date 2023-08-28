package com.masai.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum Role {

    ADMIN("ADMIN"),
    USER("USER");

    private final String roleName;
}