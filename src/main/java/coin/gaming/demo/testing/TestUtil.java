package coin.gaming.demo.testing;

import org.springframework.http.MediaType;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Helper for Testing.
 */
public class TestUtil {

    private TestUtil() {
    }

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName(StandardCharsets.UTF_8.toString())
    );

}
