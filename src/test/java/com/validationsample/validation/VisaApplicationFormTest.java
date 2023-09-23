package com.validationsample.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class VisaApplicationFormTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void 年齢が17歳であるときにバリデーションエラーとなること() {
        VisaApplicationForm form = new VisaApplicationForm(17);
        var violations = validator.validate(form);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(tuple("age", "無効な年齢です"));
    }

    @Test
    public void 年齢が18歳であるときにバリデーションエラーとならないこと() {
        VisaApplicationForm form = new VisaApplicationForm(18);
        var violations = validator.validate(form);
        assertThat(violations).isEmpty();
    }

    @Test
    public void 年齢が46歳であるときにバリデーションエラーとなること() {
        VisaApplicationForm form = new VisaApplicationForm(17);
        var violations = validator.validate(form);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(tuple("age", "無効な年齢です"));
    }

    @Test
    public void 年齢が45歳であるときにバリデーションエラーとならないこと() {
        VisaApplicationForm form = new VisaApplicationForm(18);
        var violations = validator.validate(form);
        assertThat(violations).isEmpty();
    }

    @Test
    public void 年齢がnullであるときにバリデーションエラーとならないこと() {
        VisaApplicationForm form = new VisaApplicationForm(null);
        var violations = validator.validate(form);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(tuple("age", "無効な年齢です"));
    }

}
