package kz.lowgraysky.solva.welcometask.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class ConfigProperties {

    @Value("${api.twelvedata.url}")
    private String EXCHANGE_SERVICE_API_KEY;

    @Value("${api.twelvedata.apikey}")
    private String EXCHANGE_SERVICE_URL;

    public String EXCHANGE_SERVICE_API_KEY() {
        return EXCHANGE_SERVICE_API_KEY;
    }

    public String EXCHANGE_SERVICE_URL() {
        return EXCHANGE_SERVICE_URL;
    }
}
