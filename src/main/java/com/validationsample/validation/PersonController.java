package com.validationsample.validation;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PersonController {

    @PostMapping("/persons")
    public Map<String, String> persons(@RequestBody @Validated PersonRequest personRequest) {
        return Map.of("message", "successfully created");
    }
}
