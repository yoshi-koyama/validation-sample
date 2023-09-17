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

/**
 * UserPostRequestに対するテスト
 * 下記ドキュメントを参考にしている
 * ただし、JUnit4を前提にしたコードなので適宜変更している
 * https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/#_validating_constraints
 */
class UserPostRequestTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void givenNameとfamilyNameがnullのときにバリデーションエラーとなること() {
        UserPostRequest userPostRequest = new UserPostRequest(null, null);
        Set<ConstraintViolation<UserPostRequest>> violations = validator.validate(userPostRequest);
        assertThat(violations).hasSize(2);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(tuple("givenName", "空白は許可されていません"), tuple("familyName", "空白は許可されていません"));
    }

    @Test
    public void givenNameとfamilyNameが空文字のときにバリデーションエラーとなること() {
        UserPostRequest userPostRequest = new UserPostRequest("", "");
        Set<ConstraintViolation<UserPostRequest>> violations = validator.validate(userPostRequest);
        assertThat(violations).hasSize(2);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(tuple("givenName", "空白は許可されていません"), tuple("familyName", "空白は許可されていません"));
    }

    @Test
    public void givenNameとfamilyNameが半角スペースのみのときにバリデーションエラーとなること() {
        UserPostRequest userPostRequest = new UserPostRequest(" ", " ");
        Set<ConstraintViolation<UserPostRequest>> violations = validator.validate(userPostRequest);
        assertThat(violations).hasSize(2);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(tuple("givenName", "空白は許可されていません"), tuple("familyName", "空白は許可されていません"));
    }

    @Test
    public void givenNameとfamilyNameに値があるときバリデーションエラーとならないこと() {
        UserPostRequest userPostRequest = new UserPostRequest("a", "b");
        Set<ConstraintViolation<UserPostRequest>> violations = validator.validate(userPostRequest);
        assertThat(violations).isEmpty();
    }

}
