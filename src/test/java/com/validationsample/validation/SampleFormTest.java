package com.validationsample.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class SampleFormTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void 名前が1文字未満である時にバリデーションエラーとなること() {
        SampleForm form = new SampleForm("", 20, "123-4567", "RED");
        var violations = validator.validate(form);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(tuple("name", "1 から 20 の間のサイズにしてください"));
    }

    @Test
    public void 名前が1文字である時にバリデーションエラーとならないこと() {
        SampleForm form = new SampleForm("a", 20, "123-4567", "RED");
        var violations = validator.validate(form);
        assertThat(violations).isEmpty();
    }

    @Test
    public void 名前が20文字である時にバリデーションエラーとならないこと() {
        SampleForm form = new SampleForm("a".repeat(20), 20, "123-4567", "RED");
        var violations = validator.validate(form);
        assertThat(violations).isEmpty();
    }

    @Test
    public void 名前が21文字である時にバリデーションエラーとならないこと() {
        SampleForm form = new SampleForm("a".repeat(21), 20, "123-4567", "RED");
        var violations = validator.validate(form);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(tuple("name", "1 から 20 の間のサイズにしてください"));
    }

    @Test
    public void 色にREDを指定した場合はバリデーションエラーにならないこと() {
        SampleForm form = new SampleForm("name", 20, "123-4567", "RED");

        var violations = validator.validate(form);
        assertThat(violations).isEmpty();
    }

    @Test
    public void 色にORANGEを指定した場合はバリデーションエラーにならないこと() {
        SampleForm form = new SampleForm("name", 20, "123-4567", "ORANGE");

        var violations = validator.validate(form);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(tuple("color", "有効なカラーを指定してください（RED, BLUE, GREENのいずれか）。"));
    }

}
