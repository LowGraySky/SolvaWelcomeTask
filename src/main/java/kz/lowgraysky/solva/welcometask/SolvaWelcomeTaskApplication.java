package kz.lowgraysky.solva.welcometask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@SpringBootApplication
public class SolvaWelcomeTaskApplication {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setReadTimeout(Duration.ofMillis(10000))
                .setConnectTimeout(Duration.ofMillis(5000))
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(SolvaWelcomeTaskApplication.class, args);
    }

}
