package coin.gaming.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    public final static String COMMON_RETRY_QUALIFIER_NAME = "commonRetry";

    // beans
    private final AppProperties appProperties;

    @Bean
    @Qualifier(AppConfig.COMMON_RETRY_QUALIFIER_NAME)
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();

        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
        fixedBackOffPolicy.setBackOffPeriod(appProperties.getRetryGameUrlTimeout());
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);

        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(appProperties.getRetryGameUrlMaxAttempts());
        retryTemplate.setRetryPolicy(retryPolicy);

        return retryTemplate;
    }
}
