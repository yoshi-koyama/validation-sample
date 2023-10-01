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

@AutoConfigureMockMvc
@SpringBootTest
class PersonControllerTest {

    @Autowired
    MockMvc mockMvc;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void Person登録時にfirst_nameとlast_nameがnullの場合は400エラーとなること() throws Exception {
        PersonRequest personRequest = new PersonRequest(null, null);
        ResultActions actual = mockMvc.perform(post("/persons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(personRequest)));
        actual.andExpect(status().isBadRequest())
                .andExpect(content().json("""
                        {
                          "status": "BAD_REQUEST",
                          "message": "validation error",
                          "errors": [
                            {
                              "field": "first_name",
                              "message": "null は許可されていません"
                            },
                            {
                              "field": "last_name",
                              "message": "null は許可されていません"
                            }
                          ]
                        }
                        """));
    }

}
