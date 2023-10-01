package com.validationsample.validation;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public class PersonRequest {

    @NotNull
    @JsonProperty("first_name")
    private final String firstName;

    @NotNull
    @JsonProperty("last_name")
    private final String lastName;

    public PersonRequest(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
