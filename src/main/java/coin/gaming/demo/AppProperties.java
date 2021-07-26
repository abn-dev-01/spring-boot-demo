package coin.gaming.demo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@RequiredArgsConstructor
public class AppProperties {

    @Value("${coin.gaming.retry.game_url.max_attempts}")
    private int retryGameUrlMaxAttempts;

    @Value("${coin.gaming.retry.game_url.timeout}")
    private long retryGameUrlTimeout;
}
