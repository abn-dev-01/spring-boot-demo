package coin.gaming.demo.service;

import coin.gaming.demo.model.RedirectResponse;

public interface OneTouchService {
    RedirectResponse invokeGetGameUrl(String xSignature, String stringRequest);
}
