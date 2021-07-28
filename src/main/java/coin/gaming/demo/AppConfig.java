package coin.gaming.demo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
@RequiredArgsConstructor
@Getter
public class AppConfig {

    public static final String COMMON_RETRY_QUALIFIER_NAME = "commonRetry";
    
    public static final String SLASH = "/";
    public static final String ENDPOINT_URL_ROOT = "/api";

    @Value("${coin.gaming.retry.game_url.max_attempts}")
    private int retryGameUrlMaxAttempts;

    @Value("${coin.gaming.retry.game_url.timeout}")
    private long retryGameUrlTimeout;


    @Bean
    @Qualifier(AppConfig.COMMON_RETRY_QUALIFIER_NAME)
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();

        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
        fixedBackOffPolicy.setBackOffPeriod(retryGameUrlTimeout);
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);

        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(retryGameUrlMaxAttempts);
        retryTemplate.setRetryPolicy(retryPolicy);

        return retryTemplate;
    }
}
