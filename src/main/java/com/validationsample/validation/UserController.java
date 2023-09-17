package com.validationsample.validation;

import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Validated
public class NameController {

    @PostMapping("/names")
    public Map<String, String> names(@RequestBody @Validated UserRequest userRequest) {
        return Map.of("message", "successfully created");
    }

    @GetMapping("/names")
    public Map<String, String> names(@RequestParam("name") @NotBlank String name) {
        return Map.of("message", "name successfully fetched");
    }
}
