package coin.gaming.demo.service;

import coin.gaming.demo.AppConfig;
import coin.gaming.demo.TestConstants;
import coin.gaming.demo.model.RedirectResponse;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.test.context.TestPropertySource;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource(
    properties = {
        "coin.gaming.retry.game_url.timeout=10",
        "coin.gaming.retry.game_url.max_attempts=5"
    }
)
@Log4j2
class RetryTemplateServiceImplTestAttempts extends TestConstants {

    @Autowired
    private RetryTemplateService retryTemplateService;
    @MockBean
    private OneTouchOpenFeignClient oneTouchOpenFeignClient;
    @SpyBean
    private OneTouchService oneTouchService;
    @Autowired
    @Qualifier(AppConfig.COMMON_RETRY_QUALIFIER_NAME)
    private RetryTemplate commonRetry;

    @BeforeEach
    void setUp() {
        mockRedirectResponse.setUrl(mockUrl);
    }

    @Test
    @DisplayName("Testing how Retry works.")
    void retryTest() throws Exception {
        var stringRequest = "";
        var signature = "";
        try {
            final RetryCallback<RedirectResponse, Exception> redirectResponseExceptionRetryCallback =
                content -> oneTouchService.invokeGetGameUrl(anyString(), anyString());

            final var feinErrorMessage = "UNAUTHORIZED";

            when(commonRetry.execute(redirectResponseExceptionRetryCallback))
                .thenReturn(mockRedirectResponse);
            when(oneTouchOpenFeignClient.getRedirectUrl(anyString(), anyString()))
                .thenThrow(getFeignExceptionUnauthorized(feinErrorMessage));

            RedirectResponse response = null;
            try {
                response = retryTemplateService.retry(
                    redirectResponseExceptionRetryCallback
                );
            } catch (Exception e) {
                LOG.info("FeignException is here.");
            }

            // everything is OK - only one invoke should be here
            int expectedTimesAttempts = 5 + 1;
            verify(oneTouchService, times(expectedTimesAttempts))
                .invokeGetGameUrl(anyString(), anyString());
        } catch (Throwable e) {
            Assertions.fail("Exception: " + e.getMessage());
        }
    }
}
