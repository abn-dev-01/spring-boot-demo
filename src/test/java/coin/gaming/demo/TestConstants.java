package coin.gaming.demo;

import coin.gaming.demo.model.RedirectResponse;
import feign.FeignException;
import feign.Request;
import feign.RequestTemplate;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class TestConstants {
    public final static String FILENAME_PRIVATE_KEY = "./src/main/resources/ssl/private_test_task.pem";
    public final static String FILENAME_PUBLIC_KEY = "./src/main/resources/ssl/public_test_task.pem";

    public final static String mockUrl = "http://yahoo.com";
    public final static String someOneTouchUrl = "http://some-host";
    public final static RedirectResponse mockRedirectResponse = new RedirectResponse();

    protected FeignException.Unauthorized getFeignExceptionUnauthorized(String feignBadRequestMsg) {
        
        Map<String, Collection<String>> feignHeaders = new HashMap<>();

        final var feignRequest =
            Request.create(Request.HttpMethod.POST, someOneTouchUrl, feignHeaders, Request.Body.create(new byte[1]),
                           new RequestTemplate());
        final FeignException.Unauthorized unauthorized =
            new FeignException.Unauthorized(feignBadRequestMsg, feignRequest, new byte[1]);
        return unauthorized;
    }
}
