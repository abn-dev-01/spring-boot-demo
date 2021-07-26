package coin.gaming.demo.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static coin.gaming.demo.Constants.SLASH;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {MainRestController.class})
@WebMvcTest
class MainRestControllerWebMvcTest {
    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Endpoint Redirection test.")
    void redirectToCoinGamingUrl() throws Exception {
        final var urlRedirectEndpoint =
            MainRestController.ENDPOINT_URL_PATH_GLOBAL + SLASH + MainRestController.ENDPOINT_URL_REDIRECT;
        mvc.perform(
            MockMvcRequestBuilders
                .post(urlRedirectEndpoint)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        )
           .andExpect(status().is3xxRedirection())
        ;
    }
}
