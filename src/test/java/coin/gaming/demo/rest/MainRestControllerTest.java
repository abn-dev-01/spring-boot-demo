package coin.gaming.demo.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MainRestControllerTest {
    @Autowired
    private MainRestController mainRestController;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("MainRestController is not null.")
    void contextLoads() throws Exception {
        assertThat(mainRestController).isNotNull();
    }
}
