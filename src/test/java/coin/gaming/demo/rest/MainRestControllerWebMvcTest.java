package coin.gaming.demo.rest;

import coin.gaming.demo.AppConfig;
import coin.gaming.demo.TestConstants;
import coin.gaming.demo.model.RedirectResponse;
import coin.gaming.demo.service.OneTouchService;
import coin.gaming.demo.service.RetryTemplateService;
import coin.gaming.demo.service.SignService;
import feign.FeignException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static coin.gaming.demo.AppConfig.SLASH;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = {MainRestController.class, AppConfig.class})
@WebMvcTest
class MainRestControllerWebMvcTest extends TestConstants {
    @Autowired
    private MockMvc mvc;

    @Spy
    private AppConfig appProperties;
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

        mockRedirectResponse.setUrl(mockUrl);
        when(retryTemplateService.retry(any()))
            .thenReturn(mockRedirectResponse);

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
        mockRedirectResponse.setUrl(mockUrl);
        when(retryTemplateService.retry(any()))
            .thenReturn(mockRedirectResponse);

        mvc.perform(
            MockMvcRequestBuilders
                .post(urlRedirectEndpoint)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        )
           .andExpect(status().is3xxRedirection())
           .andExpect(redirectedUrl(mockUrl))
        ;
    }

    @Test
    @DisplayName("Endpoint Redirection test - Unauthorised.")
    void redirectToCoinGamingUrlUnauthorised() throws Exception {

        final var urlRedirectEndpoint =
            MainRestController.ENDPOINT_URL_PATH_GLOBAL + SLASH + MainRestController.ENDPOINT_URL_REDIRECT;
        String expectedResponseJsonString = "{}";

        // mocks
        final var mockRedirectResponse = new RedirectResponse();
        mockRedirectResponse.setUrl(mockUrl);
        final var feignBadRequestMsg = "FEIGN BAD REQUEST";

        final FeignException.Unauthorized unauthorized = getFeignExceptionUnauthorized(feignBadRequestMsg);

        when(retryTemplateService.retry(any()))
            .thenThrow(unauthorized);

        try {
            mvc.perform(
                MockMvcRequestBuilders
                    .post(urlRedirectEndpoint)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
            )
               .andExpect(status().isUnauthorized())
            ;
            Assertions.fail("Exception was not produced.");
        } catch (Exception e) {
            Assertions.assertTrue(e.getCause() instanceof FeignException.Unauthorized);
            Assertions.assertEquals(feignBadRequestMsg, e.getCause().getMessage());
        }
    }
}
