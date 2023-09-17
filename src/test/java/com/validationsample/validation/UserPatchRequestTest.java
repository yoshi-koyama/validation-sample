package com.validationsample.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class UserPatchRequestTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void givenNameがnullのときにバリデーションエラーとなること() {
        UserPatchRequest userPatchRequest = new UserPatchRequest(null, "familyName");

        Set<ConstraintViolation<UserPatchRequest>> violations = validator.validate(userPatchRequest);

        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(tuple("givenNameOrFamilyNameNotBlank", "givenNameかfamilyNameが空白です"));
    }

    @Test
    public void familyNameがnullのときにバリデーションエラーとなること() {
        UserPatchRequest userPatchRequest = new UserPatchRequest("givenName", null);

        Set<ConstraintViolation<UserPatchRequest>> violations = validator.validate(userPatchRequest);

        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(tuple("givenNameOrFamilyNameNotBlank", "givenNameかfamilyNameが空白です"));
    }

    @Test
    public void givenNameが空文字のときにバリデーションエラーとなること() {
        UserPatchRequest userPatchRequest = new UserPatchRequest("", "familyName");

        Set<ConstraintViolation<UserPatchRequest>> violations = validator.validate(userPatchRequest);

        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(tuple("givenNameOrFamilyNameNotBlank", "givenNameかfamilyNameが空白です"));
    }

    @Test
    public void familyNameが空文字のときにバリデーションエラーとなること() {
        UserPatchRequest userPatchRequest = new UserPatchRequest("givenName", "");

        Set<ConstraintViolation<UserPatchRequest>> violations = validator.validate(userPatchRequest);

        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(tuple("givenNameOrFamilyNameNotBlank", "givenNameかfamilyNameが空白です"));
    }

    @Test
    public void givenNameが半角スペースのみのときにバリデーションエラーとなること() {
        UserPatchRequest userPatchRequest = new UserPatchRequest(" ", "familyName");

        Set<ConstraintViolation<UserPatchRequest>> violations = validator.validate(userPatchRequest);

        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(tuple("givenNameOrFamilyNameNotBlank", "givenNameかfamilyNameが空白です"));
    }

    @Test
    public void familyNameが半角スペースのみのときにバリデーションエラーとなること() {
        UserPatchRequest userPatchRequest = new UserPatchRequest("givenName", " ");

        Set<ConstraintViolation<UserPatchRequest>> violations = validator.validate(userPatchRequest);

        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(tuple("givenNameOrFamilyNameNotBlank", "givenNameかfamilyNameが空白です"));
    }

}
