package com.validationsample.validation;

import jakarta.validation.constraints.NotBlank;

public class UserGetQueryParam {

    @NotBlank
    private String givenName;

    @NotBlank
    private String familyName;

    public UserGetQueryParam(String givenName, String familyName) {
        this.givenName = givenName;
        this.familyName = familyName;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getFamilyName() {
        return familyName;
    }
}
