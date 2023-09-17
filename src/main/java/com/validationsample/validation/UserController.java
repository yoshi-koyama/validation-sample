package com.validationsample.validation;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {

    @PostMapping("/users")
    public Map<String, String> names(@RequestBody @Validated UserPostRequest userRequest) {
        return Map.of("message", "successfully created");
    }

    @GetMapping("/users")
    public Map<String, String> names(@Valid UserGetQueryParam userGetQueryParam) {
        return Map.of("message", "name successfully fetched");
    }

    @PatchMapping("/users")
    public Map<String, String> names(@RequestBody @Valid UserPatchRequest userPatchRequest) {
        return Map.of("message", "successfully updated");
    }
}
