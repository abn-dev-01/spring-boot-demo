package coin.gaming.demo.service;

import coin.gaming.demo.model.RedirectResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class OneTouchServiceImpl implements OneTouchService {

    private final OneTouchOpenFeignClient oneTouchOpenFeignClient;

    @Override
    public RedirectResponse invokeGetGameUrl(final String xSignature, final String stringRequest) {
        LOG.debug(" >> Invoked Get Game URL ... ");
        return oneTouchOpenFeignClient.getRedirectUrl(xSignature, stringRequest);
    }
}
