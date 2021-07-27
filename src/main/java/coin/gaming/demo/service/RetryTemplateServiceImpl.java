package coin.gaming.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.policy.TimeoutRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

/**
 * Unified the RetryTemplate Service.
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class RetryTemplateServiceImpl implements RetryTemplateService {

    private final OneTouchOpenFeignClient oneTouchOpenFeignClient;

    @Override
    public <T> T retry(int maxAttempts, Long timeout, RetryCallback<T, Exception> retryCallback) throws Exception {
        SimpleRetryPolicy policy = new SimpleRetryPolicy();
        // Set the max retry attempts
        policy.setMaxAttempts(maxAttempts);

        TimeoutRetryPolicy retryPolicy = new TimeoutRetryPolicy();
        retryPolicy.setTimeout(timeout);

        // Use the policy...
        RetryTemplate template = new RetryTemplate();
        template.setRetryPolicy(retryPolicy);
        template.setRetryPolicy(policy);

        return template.execute(retryCallback);
    }
}
