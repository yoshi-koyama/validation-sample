package com.validationsample.validation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.validator.HibernateValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;


class PersonRequestTest {

    @Nested
    public class PersonRequestSerializationTest {
        private final ObjectMapper objectMapper = new ObjectMapper();

        @Test
        public void personIsSerialized() throws JsonProcessingException {
            PersonRequest personRequest = new PersonRequest("Clark", "Kent");

            String serializedPerson = objectMapper.writeValueAsString(personRequest);

            assertThatJson(serializedPerson).isEqualTo("""
                    {
                      "first_name": "Clark",
                      "last_name": "Kent"
                    }
                    """);
        }
    }

    @Nested
    public class PersonRequestValidationTest {

        private static Validator validator;

        @BeforeAll
        public static void setUpValidator() {
            JacksonPropertyNodeNameProvider propertyNodeNameProvider = new JacksonPropertyNodeNameProvider();
            ValidatorFactory factory = Validation.byProvider(HibernateValidator.class)
                    .configure()
                    .propertyNodeNameProvider(propertyNodeNameProvider)
                    .buildValidatorFactory();
            validator = factory.getValidator();
        }

        @Test
        public void first_nameとlast_nameがnullのときにバリデーションエラーとなること() {
            PersonRequest personRequest = new PersonRequest(null, null);
            var violations = validator.validate(personRequest);
            assertThat(violations).hasSize(2);
            assertThat(violations)
                    .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                    .containsExactlyInAnyOrder(
                            tuple("first_name", "null は許可されていません"),
                            tuple("last_name", "null は許可されていません"));
        }

    }

}
