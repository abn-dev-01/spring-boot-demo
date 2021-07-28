package coin.gaming.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CoinGamingDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoinGamingDemoApplication.class, args);
    }
}
