package coin.gaming.demo.service;

import org.springframework.retry.RetryCallback;

public interface RetryTemplateService {
    <T> T retry(RetryCallback<T, Exception> retryCallback) throws Exception;
}
