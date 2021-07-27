package coin.gaming.demo.service;

import coin.gaming.demo.AppConfig;
import coin.gaming.demo.TestConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.retry.support.RetryTemplate;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class RetryTemplateServiceImplTest extends TestConstants {

    @Autowired
    private RetryTemplateService retryTemplateService;
    @MockBean
    private OneTouchOpenFeignClient oneTouchOpenFeignClient;
    @MockBean
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
            when(commonRetry.execute(content -> oneTouchService.invokeGetGameUrl(signature, stringRequest)))
                .thenReturn(mockRedirectResponse);

            var response = retryTemplateService.retry(
                content -> oneTouchService.invokeGetGameUrl(anyString(), anyString())
            );
            Assertions.assertNotNull(response);
            Assertions.assertEquals(mockUrl, response.getUrl());
        } catch (Throwable e) {
            Assertions.fail("Exception: " + e.getMessage());
        }
    }
}
