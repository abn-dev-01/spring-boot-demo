package coin.gaming.demo.service;

import coin.gaming.demo.exception.InternalServerErrorException;
import coin.gaming.demo.util.CertificateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
@Log4j2
@RequiredArgsConstructor
public class SignServiceImpl implements SignService {

    // beans
    //  > need it for loading certificates...
    private final ResourceLoader resourceLoader;

    // values 
    @Value("${coin.gaming.cert.private_key}")
    private String privateKeyFilename;

    @Override
    public String getSignature(String stringRequest) {
        try {
//            var privateKeyResource = resourceLoader.getResource("classpath:" + privateKeyFilename);
//            final var path = privateKeyResource.getURI().getPath();
            final var path = new ClassPathResource(privateKeyFilename).getFile();
            LOG.debug("Path of PrivateKey: {}", () -> path);

            var privateKey = CertificateUtil.readPrivateKeyRSA(path.getAbsolutePath());
            return this.sign(stringRequest, privateKey);
        } catch (Exception e) {
            throw new InternalServerErrorException("Signature failed. " + e.getMessage());
        }
    }

    @Override
    public String sign(String plainText, PrivateKey privateKey) throws Exception {
        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(privateKey);
        privateSignature.update(plainText.getBytes(UTF_8));

        byte[] signature = privateSignature.sign();

        return Base64.getEncoder().encodeToString(signature);
    }

    @Override
    public boolean verify(String plainText, String signature, PublicKey publicKey) throws Exception {
        Signature publicSignature = Signature.getInstance("SHA256withRSA");
        publicSignature.initVerify(publicKey);
        publicSignature.update(plainText.getBytes(UTF_8));

        byte[] signatureBytes = Base64.getDecoder().decode(signature);

        return publicSignature.verify(signatureBytes);
    }
}
