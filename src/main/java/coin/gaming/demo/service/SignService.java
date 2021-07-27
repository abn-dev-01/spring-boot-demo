package coin.gaming.demo.service;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface SignService {

    String getSignature(String stringRequest);

    String sign(String plainText, PrivateKey privateKey) throws Exception;

    boolean verify(String plainText, String signature, PublicKey publicKey) throws Exception;
}
