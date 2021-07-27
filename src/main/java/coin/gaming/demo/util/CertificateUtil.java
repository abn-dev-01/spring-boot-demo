package coin.gaming.demo.util;

import org.apache.tomcat.util.codec.binary.Base64;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Certificate utility helper.
 */
public class CertificateUtil {

    public static final String ALGORITHM_RSA = "RSA";

    /**
     * Read private key with given algorithm from a privateKeyFilename.
     *
     * @param privateKeyFilename - is a privateKeyFilename of the Private Key.
     *
     * @return RSAPrivateKey
     *
     * @throws Exception
     */
    public static RSAPrivateKey readPrivateKeyRSA(String privateKeyFilename) throws Exception {

        String key = readFile(privateKeyFilename);

        String privateKeyPEM = key
            .replace("-----BEGIN PRIVATE KEY-----", "")
            .replace("-----BEGIN RSA PRIVATE KEY-----", "")
            .replaceAll(System.lineSeparator(), "")
            .replaceAll("\r\n", "")
            .replaceAll("\n", "")
            .replace("-----END PRIVATE KEY-----", "")
            .replace("-----END RSA PRIVATE KEY-----", "");

        byte[] encoded = Base64.decodeBase64(privateKeyPEM);

        addBouncyCastleProvider();

        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }

    public static RSAPublicKey readPublicKeyRSA(String publicKeyFilename) throws Exception {

        String key = readFile(publicKeyFilename);

        String publicKeyPEM = key
            .replace("-----BEGIN PUBLIC KEY-----", "")
            .replace("-----BEGIN RSA PUBLIC KEY-----", "")
            .replaceAll(System.lineSeparator(), "")
            .replaceAll("\r\n", "")
            .replaceAll("\n", "")
            .replace("-----END PUBLIC KEY-----", "")
            .replace("-----END RSA PUBLIC KEY-----", "");

        addBouncyCastleProvider();

        byte[] encoded = Base64.decodeBase64(publicKeyPEM);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    private static String readFile(String publicKeyFilename) throws IOException {
        return new String(Files.readAllBytes(Paths.get(publicKeyFilename)), Charset.defaultCharset());
    }

    private static void addBouncyCastleProvider() {
        java.security.Security.addProvider(
            new org.bouncycastle.jce.provider.BouncyCastleProvider()
        );
    }
}
