package coin.gaming.demo.service;

import coin.gaming.demo.TestConstants;
import coin.gaming.demo.util.CertificateUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.security.PrivateKey;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SignServiceImpl.class})
class SignServiceImplTest extends TestConstants {

    @Autowired
    public SignService signService;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("SignService test.")
    void sign() throws Exception {
        var someText = "some text here";

        // sign a text 
        var privateKey = CertificateUtil.readPrivateKeyRSA(FILENAME_PRIVATE_KEY);
        var signature = signService.sign(someText, privateKey);
        Assertions.assertNotNull(signature, "Signing a text failed.");

        // verify a text 
        var publicKey = CertificateUtil.readPublicKeyRSA(FILENAME_PUBLIC_KEY);
        var verifyResult = signService.verify(someText, signature, publicKey);
        Assertions.assertTrue(verifyResult, "Verification failed.");
    }

    @Test
    void verify() {
    }
}
