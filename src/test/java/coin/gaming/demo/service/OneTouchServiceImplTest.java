package coin.gaming.demo.service;

import coin.gaming.demo.AppConfig;
import coin.gaming.demo.TestConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = {OneTouchServiceImpl.class, AppConfig.class})
@WebMvcTest
class OneTouchServiceImplTest extends TestConstants {
    @Autowired
    private OneTouchService oneTouchService;
    @MockBean
    private OneTouchOpenFeignClient oneTouchOpenFeignClient;

    @BeforeEach
    void setUp() {
        mockRedirectResponse.setUrl(mockUrl);
    }

    @Test
    @DisplayName("Invoking Game URL ")
    void invokeGetGameUrl() {

        var signature = "";
        var stringRequest = "";

        when(oneTouchOpenFeignClient.getRedirectUrl(anyString(), anyString()))
            .thenReturn(mockRedirectResponse);

        var respone = oneTouchService.invokeGetGameUrl(signature, stringRequest);

        //import org.junit.jupiter.api.Assertions;
        Assertions.assertNotNull(respone);
    }
}
