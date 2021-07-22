package coin.gaming.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CoinGamingDemoApplicationTest {
    @Test
    @DisplayName("A simple sanity check test that will fail if the application context cannot start.")
    void contextLoads() {
        Assertions.assertTrue(true);
    }
}
