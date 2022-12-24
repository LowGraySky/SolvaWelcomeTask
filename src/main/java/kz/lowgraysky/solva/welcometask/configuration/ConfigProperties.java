package kz.lowgraysky.solva.welcometask.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.math.BigDecimal;

@Configuration
@PropertySource("classpath:application.properties")
public class ConfigProperties {

    @Value("${api.twelvedata.url}")
    private String EXCHANGE_SERVICE_API_KEY;

    @Value("${api.twelvedata.apikey}")
    private String EXCHANGE_SERVICE_URL;

    @Value("${limit.product.month_limit}")
    private BigDecimal PRODUCT_MONTH_LIMIT;

    @Value("${limit.service.month_limit}")
    private BigDecimal SERVICE_MONTH_LIMIT;

    public String EXCHANGE_SERVICE_API_KEY() {
        return EXCHANGE_SERVICE_API_KEY;
    }

    public String EXCHANGE_SERVICE_URL() {
        return EXCHANGE_SERVICE_URL;
    }

    public BigDecimal PRODUCT_MONTH_LIMIT() {
        return PRODUCT_MONTH_LIMIT;
    }

    public BigDecimal SERVICE_MONTH_LIMIT() {
        return SERVICE_MONTH_LIMIT;
    }
}
