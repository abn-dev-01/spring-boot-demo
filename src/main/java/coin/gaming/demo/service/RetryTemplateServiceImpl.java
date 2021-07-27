package coin.gaming.demo.service;

import coin.gaming.demo.AppConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

/**
 * Unified the RetryTemplate Service.
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class RetryTemplateServiceImpl implements RetryTemplateService {

    // beans
    private final OneTouchOpenFeignClient oneTouchOpenFeignClient;
    @Qualifier(AppConfig.COMMON_RETRY_QUALIFIER_NAME)
    private final RetryTemplate retryTemplate;

    @Override
    public <T> T retry(RetryCallback<T, Exception> retryCallback) throws Exception {
        LOG.debug("RetryTemplate execution...");
        return retryTemplate.execute(retryCallback);
    }
}
