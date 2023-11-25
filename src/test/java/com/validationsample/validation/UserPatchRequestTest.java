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
    public void givenNameとfamilyNamの両方がnullのときにバリデーションエラーとなること() {
        UserPatchRequest userPatchRequest = new UserPatchRequest(null, null);

        Set<ConstraintViolation<UserPatchRequest>> violations = validator.validate(userPatchRequest);

        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(tuple("bothGivenNameAndFamilyNameNotBlank", "givenNameとfamilyNameの両方が空白です"));
    }

    @Test
    public void givenNameとfamilyNamの両方が空文字のときにバリデーションエラーとなること() {
        UserPatchRequest userPatchRequest = new UserPatchRequest("", "");

        Set<ConstraintViolation<UserPatchRequest>> violations = validator.validate(userPatchRequest);

        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(tuple("bothGivenNameAndFamilyNameNotBlank", "givenNameとfamilyNameの両方が空白です"));
    }

    @Test
    public void givenNameとfamilyNamの両方が半角スペースのときにバリデーションエラーとなること() {
        UserPatchRequest userPatchRequest = new UserPatchRequest(" ", " ");

        Set<ConstraintViolation<UserPatchRequest>> violations = validator.validate(userPatchRequest);

        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(tuple("bothGivenNameAndFamilyNameNotBlank", "givenNameとfamilyNameの両方が空白です"));
    }

    @Test
    public void givenNameがnullでfamilyNamが空文字のときにバリデーションエラーとなること() {
        UserPatchRequest userPatchRequest = new UserPatchRequest(null, "");

        Set<ConstraintViolation<UserPatchRequest>> violations = validator.validate(userPatchRequest);

        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(tuple("bothGivenNameAndFamilyNameNotBlank", "givenNameとfamilyNameの両方が空白です"));
    }

    @Test
    public void givenNameがnullでfamilyNamが半角スペースのときにバリデーションエラーとなること() {
        UserPatchRequest userPatchRequest = new UserPatchRequest(null, " ");

        Set<ConstraintViolation<UserPatchRequest>> violations = validator.validate(userPatchRequest);

        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(tuple("bothGivenNameAndFamilyNameNotBlank", "givenNameとfamilyNameの両方が空白です"));
    }

    @Test
    public void givenNameが空文字でfamilyNamがnullのときにバリデーションエラーとなること() {
        UserPatchRequest userPatchRequest = new UserPatchRequest("", null);

        Set<ConstraintViolation<UserPatchRequest>> violations = validator.validate(userPatchRequest);

        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(tuple("bothGivenNameAndFamilyNameNotBlank", "givenNameとfamilyNameの両方が空白です"));
    }

    @Test
    public void givenNameが空文字でfamilyNamが半角スペースのときにバリデーションエラーとなること() {
        UserPatchRequest userPatchRequest = new UserPatchRequest("", " ");

        Set<ConstraintViolation<UserPatchRequest>> violations = validator.validate(userPatchRequest);

        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(tuple("bothGivenNameAndFamilyNameNotBlank", "givenNameとfamilyNameの両方が空白です"));
    }

    @Test
    public void givenNameが半角スペースでfamilyNamがnullのときにバリデーションエラーとなること() {
        UserPatchRequest userPatchRequest = new UserPatchRequest(" ", null);

        Set<ConstraintViolation<UserPatchRequest>> violations = validator.validate(userPatchRequest);

        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(tuple("bothGivenNameAndFamilyNameNotBlank", "givenNameとfamilyNameの両方が空白です"));
    }

    @Test
    public void givenNameが半角スペースでfamilyNamが空文字のときにバリデーションエラーとなること() {
        UserPatchRequest userPatchRequest = new UserPatchRequest(" ", "");

        Set<ConstraintViolation<UserPatchRequest>> violations = validator.validate(userPatchRequest);

        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(tuple("bothGivenNameAndFamilyNameNotBlank", "givenNameとfamilyNameの両方が空白です"));
    }

    @Test
    public void givenNameのみがnullのときにバリデーションエラーとならないこと() {
        UserPatchRequest userPatchRequest = new UserPatchRequest(null, "familyName");

        Set<ConstraintViolation<UserPatchRequest>> violations = validator.validate(userPatchRequest);

        assertThat(violations).isEmpty();
    }

    @Test
    public void familyNameのみがnullのときにバリデーションエラーとならないこと() {
        UserPatchRequest userPatchRequest = new UserPatchRequest("givenName", null);

        Set<ConstraintViolation<UserPatchRequest>> violations = validator.validate(userPatchRequest);

        assertThat(violations).isEmpty();
    }

    @Test
    public void givenNameのみが空文字のときにバリデーションエラーとならないこと() {
        UserPatchRequest userPatchRequest = new UserPatchRequest("", "familyName");

        Set<ConstraintViolation<UserPatchRequest>> violations = validator.validate(userPatchRequest);

        assertThat(violations).isEmpty();
    }

    @Test
    public void familyNameのみが空文字のときにバリデーションエラーとならないこと() {
        UserPatchRequest userPatchRequest = new UserPatchRequest("givenName", "");

        Set<ConstraintViolation<UserPatchRequest>> violations = validator.validate(userPatchRequest);

        assertThat(violations).isEmpty();
    }

    @Test
    public void givenNameのみが半角スペースのみのときにバリデーションエラーとならないこと() {
        UserPatchRequest userPatchRequest = new UserPatchRequest(" ", "familyName");

        Set<ConstraintViolation<UserPatchRequest>> violations = validator.validate(userPatchRequest);

        assertThat(violations).isEmpty();
    }

    @Test
    public void familyNameのみが半角スペースのみのときにバリデーションエラーとならないこと() {
        UserPatchRequest userPatchRequest = new UserPatchRequest("givenName", " ");

        Set<ConstraintViolation<UserPatchRequest>> violations = validator.validate(userPatchRequest);

        assertThat(violations).isEmpty();
    }

}
