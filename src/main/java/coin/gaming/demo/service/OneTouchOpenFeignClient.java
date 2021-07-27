package coin.gaming.demo.service;

import coin.gaming.demo.model.RedirectResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Service
@FeignClient(
    value = OneTouchOpenFeignClient.FEIGN_CLIENT_NAME,
    url = "${coin.gaming.api.onetouch.url}"
)
public interface OneTouchOpenFeignClient {
    public static final String FEIGN_CLIENT_NAME = "one-touch-client";

    @PostMapping(
        path = "${coin.gaming.api.onetouch.game_path}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
        //  X-Signature: RSA-SHA256 is used to sign the request body using the private key. The signature is validated 
        //  using the public key.
    RedirectResponse getRedirectUrl(
        @RequestHeader(name = "xSignature") String xSignature,
        @RequestBody String request
    );
}
