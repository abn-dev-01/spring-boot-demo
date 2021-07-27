package coin.gaming.demo.util;

import coin.gaming.demo.TestConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CertificateUtilTest extends TestConstants {

    @Test
    @DisplayName("Read a PrivateKey from a file is not null.")
    void readPrivateKeyFileTest() throws Exception {
        var privateKey = CertificateUtil.readPrivateKeyRSA(FILENAME_PRIVATE_KEY);

        Assertions.assertNotNull(privateKey);
    }

    @Test
    @DisplayName("Read a PublicKey from a file is not null.")
    void readPublicKeyRSA() throws Exception {
        var publicKey = CertificateUtil.readPublicKeyRSA(FILENAME_PUBLIC_KEY);

        Assertions.assertNotNull(publicKey);
    }
}
