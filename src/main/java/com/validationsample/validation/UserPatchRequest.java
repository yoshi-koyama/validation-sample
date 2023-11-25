package com.validationsample.validation;

import jakarta.validation.constraints.AssertTrue;

public class UserPatchRequest {

    private String givenName;

    private String familyName;

    public UserPatchRequest(String givenName, String familyName) {
        this.givenName = givenName;
        this.familyName = familyName;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    @AssertTrue(message = "givenNameとfamilyNameの両方が空白です")
    public boolean isBothGivenNameAndFamilyNameNotBlank() {
        // givenNameとfamilyNameの全てがnullまたは空文字または半角スペースのときにfalseを返す
        if ((givenName == null || givenName.isBlank()) && (familyName == null || familyName.isBlank())) {
            return false;
        } else {
            return true;
        }
    }
}
