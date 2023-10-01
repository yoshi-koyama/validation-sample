package com.validationsample.validation;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidatorConfig {

    @Bean
    public Validator validator() {
        JacksonPropertyNodeNameProvider propertyNodeNameProvider = new JacksonPropertyNodeNameProvider();
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                .propertyNodeNameProvider(propertyNodeNameProvider)
                .buildValidatorFactory();
        return validatorFactory.getValidator();
    }
}
