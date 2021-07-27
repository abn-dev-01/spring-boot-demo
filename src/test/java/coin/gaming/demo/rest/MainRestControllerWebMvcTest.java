package coin.gaming.demo.rest;

import coin.gaming.demo.AppProperties;
import coin.gaming.demo.model.RedirectResponse;
import coin.gaming.demo.service.OneTouchService;
import coin.gaming.demo.service.RetryTemplateService;
import coin.gaming.demo.service.SignService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static coin.gaming.demo.Constants.SLASH;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.Mockito.when;


@AutoConfigureMockMvc
@ContextConfiguration(classes = {MainRestController.class, AppProperties.class})
@WebMvcTest
class MainRestControllerWebMvcTest {
    @Autowired
    private MockMvc mvc;

    @Spy
    private AppProperties appProperties;
    @MockBean
    private RetryTemplateService retryTemplateService;
    @MockBean
    private SignService signService;
    @MockBean
    private OneTouchService oneTouchService;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Endpoint Redirection test - is 3xx redirection.")
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

    @Test
    @DisplayName("Endpoint Redirection test - getting URL for redirection.")
    void redirectToCoinGamingUrlCheckRedirect() throws Exception {

        final var urlRedirectEndpoint =
            MainRestController.ENDPOINT_URL_PATH_GLOBAL + SLASH + MainRestController.ENDPOINT_URL_REDIRECT;
        String expectedResponseJsonString = "{}";

        // mocks
        final var mockRedirectResponse = new RedirectResponse();
        final var mockUrl = "http://yahoo.com";

        mockRedirectResponse.setUrl(mockUrl);
        when(retryTemplateService.retry(anyInt(), anyLong(), any()))
            .thenReturn(mockRedirectResponse);

        mvc.perform(
            MockMvcRequestBuilders
                .post(urlRedirectEndpoint)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        )
           .andExpect(status().is3xxRedirection())
           .andExpect(
               redirectedUrl(mockUrl)
           )
        ;
    }
}
