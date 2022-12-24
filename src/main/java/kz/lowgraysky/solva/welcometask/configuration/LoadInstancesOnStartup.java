package kz.lowgraysky.solva.welcometask.configuration;

import kz.lowgraysky.solva.welcometask.entities.Currency;
import kz.lowgraysky.solva.welcometask.entities.TransactionLimit;
import kz.lowgraysky.solva.welcometask.entities.enums.ExpenseCategory;
import kz.lowgraysky.solva.welcometask.repositories.CurrencyRepository;
import kz.lowgraysky.solva.welcometask.repositories.LimitRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class LoadInstancesOnStartup implements CommandLineRunner {

    private final ConfigProperties configProperties;
    private final CurrencyRepository currencyRepository;
    private final LimitRepository limitRepository;

    public LoadInstancesOnStartup(ConfigProperties configProperties,
                                  CurrencyRepository currencyRepository,
                                  LimitRepository limitRepository) {
        this.configProperties = configProperties;
        this.currencyRepository = currencyRepository;
        this.limitRepository = limitRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        currencyRepository.save(new Currency("KZT"));
        currencyRepository.save(new Currency("RUB"));
        currencyRepository.save(new Currency("USD"));
        limitRepository.save(new TransactionLimit(configProperties.SERVICE_MONTH_LIMIT(), ExpenseCategory.SERVICE));
        limitRepository.save(new TransactionLimit(configProperties.PRODUCT_MONTH_LIMIT(), ExpenseCategory.PRODUCT));
    }
}
