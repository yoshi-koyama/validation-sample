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

    @AssertTrue(message = "givenNameかfamilyNameが空白です")
    public boolean isGivenNameOrFamilyNameNotBlank() {
        // givenNameかfamilyNameがnullまたは空文字または半角スペースのときにfalseを返す
        if (givenName == null || familyName == null) {
            return false;
        } else if (givenName.isBlank() || familyName.isBlank()) {
            return false;
        }
        return true;
    }
}
