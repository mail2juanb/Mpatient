package com.microdiab.mpatient.exception;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc
public class GlobalHandlerExceptionIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void handleValidationExceptions_shouldReturnBadRequestWithErrors() throws Exception {
        Map<String, Object> invalidPatient = new HashMap<>();
        invalidPatient.put("lastname", "");
        invalidPatient.put("firstname", "");
        invalidPatient.put("dateofbirth", LocalDate.now().plusDays(1));
        invalidPatient.put("gender", "");

        mockMvc.perform(post("/patient")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(invalidPatient))
                        .with(httpBasic("username", "user")))
                .andExpect(status().isBadRequest())
                // Vérifie que la réponse est un tableau JSON
                .andExpect(jsonPath("$").isArray())
                // Vérifie que chaque erreur attendue est présente dans le tableau
                .andExpect(jsonPath("$[?(@.field == 'lastname')].defaultMessage").value("lastname is mandatory"))
                .andExpect(jsonPath("$[?(@.field == 'firstname')].defaultMessage").value("firstname is mandatory"))
                .andExpect(jsonPath("$[?(@.field == 'dateofbirth')].defaultMessage").value("dateofbirth must be in the past"))
                .andExpect(jsonPath("$[?(@.field == 'gender')].defaultMessage").value("gender is mandatory"));
    }


    @Test
    void handleValidationExceptions_shouldReturnBadRequestWithSingleError() throws Exception {
        Map<String, Object> invalidPatient = new HashMap<>();
        invalidPatient.put("lastname", "");
        invalidPatient.put("firstname", "Jean");
        invalidPatient.put("dateofbirth", LocalDate.of(2000, 1, 1));
        invalidPatient.put("gender", "M");

        mockMvc.perform(post("/patient")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(invalidPatient))
                        .with(httpBasic("username", "user")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[?(@.field == 'lastname')].defaultMessage").value("lastname is mandatory"))
                .andExpect(jsonPath("$[?(@.field == 'firstname')]").doesNotExist())
                .andExpect(jsonPath("$[?(@.field == 'dateofbirth')]").doesNotExist())
                .andExpect(jsonPath("$[?(@.field == 'gender')]").doesNotExist());
    }

    @Test
    void handleValidationExceptions_shouldReturnBadRequestWithMultipleErrors() throws Exception {
        Map<String, Object> invalidPatient = new HashMap<>();
        invalidPatient.put("lastname", "");
        invalidPatient.put("firstname", "");
        invalidPatient.put("dateofbirth", LocalDate.now().plusDays(1));
        invalidPatient.put("gender", "");

        mockMvc.perform(post("/patient")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(invalidPatient))
                        .with(httpBasic("username", "user")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[?(@.field == 'lastname')].defaultMessage").value("lastname is mandatory"))
                .andExpect(jsonPath("$[?(@.field == 'firstname')].defaultMessage").value("firstname is mandatory"))
                .andExpect(jsonPath("$[?(@.field == 'dateofbirth')].defaultMessage").value("dateofbirth must be in the past"))
                .andExpect(jsonPath("$[?(@.field == 'gender')].defaultMessage").value("gender is mandatory"));
    }

    @Test
    void handleValidationExceptions_shouldNotBeTriggeredForValidPatient() throws Exception {
        Map<String, Object> validPatient = new HashMap<>();
        validPatient.put("lastname", "Dupont");
        validPatient.put("firstname", "Jean");
        validPatient.put("dateofbirth", LocalDate.of(2000, 1, 1));
        validPatient.put("gender", "M");

        mockMvc.perform(post("/patient")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(validPatient))
                        .with(httpBasic("username", "user")))
                .andExpect(status().isOk());
    }
}
