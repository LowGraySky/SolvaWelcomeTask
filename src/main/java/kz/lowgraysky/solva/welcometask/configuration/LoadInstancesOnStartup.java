package kz.lowgraysky.solva.welcometask.configuration;

import kz.lowgraysky.solva.welcometask.entities.Currency;
import kz.lowgraysky.solva.welcometask.entities.TransactionLimit;
import kz.lowgraysky.solva.welcometask.entities.enums.ExpenseCategory;
import kz.lowgraysky.solva.welcometask.repositories.CurrencyRepository;
import kz.lowgraysky.solva.welcometask.repositories.TransactionLimitRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Component
public class LoadInstancesOnStartup implements CommandLineRunner {

    private final ConfigProperties configProperties;
    private final CurrencyRepository currencyRepository;
    private final TransactionLimitRepository transactionLimitRepository;

    public LoadInstancesOnStartup(ConfigProperties configProperties,
                                  CurrencyRepository currencyRepository,
                                  TransactionLimitRepository transactionLimitRepository) {
        this.configProperties = configProperties;
        this.currencyRepository = currencyRepository;
        this.transactionLimitRepository = transactionLimitRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Currency USDCurrency = new Currency("USD");
        currencyRepository.save(new Currency("KZT"));
        currencyRepository.save(new Currency("RUB"));
        currencyRepository.save(USDCurrency);
        transactionLimitRepository.save(new TransactionLimit(
                configProperties.SERVICE_MONTH_LIMIT() == null ?
                        BigDecimal.ZERO : configProperties.SERVICE_MONTH_LIMIT(),
                ExpenseCategory.SERVICE,
                ZonedDateTime.now(),
                USDCurrency
        ));
        transactionLimitRepository.save(new TransactionLimit(
                configProperties.PRODUCT_MONTH_LIMIT() == null ?
                        BigDecimal.ZERO : configProperties.PRODUCT_MONTH_LIMIT(),
                ExpenseCategory.PRODUCT,
                ZonedDateTime.now(),
                USDCurrency
        ));
    }
}
