package com.validationsample.validation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// MockMvcを使うために必要なimport文
@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void ユーザー登録時にgivenNameとfamilyNameがnullの場合は400エラーとなること() throws Exception {
        UserPostRequest userRequest = new UserPostRequest(null, null);
        ResultActions actual = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(userRequest)));
        actual.andExpect(status().isBadRequest())
                .andExpect(content().json("""
                        {
                          "status": "BAD_REQUEST",
                          "message": "validation error",
                          "errors": [
                            {
                              "field": "familyName",
                              "message": "must not be blank"
                            },
                            {
                              "field": "givenName",
                              "message": "must not be blank"
                            }
                          ]
                        }
                        """));
    }

}
