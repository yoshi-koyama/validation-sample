package com.validationsample.validation;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class NameForm {

    @NotBlank
    private String givenName;

    @NotBlank
    @Length(min = 1, max = 5)
    private String familyName;

    public NameForm(String givenName, String familyName) {
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
