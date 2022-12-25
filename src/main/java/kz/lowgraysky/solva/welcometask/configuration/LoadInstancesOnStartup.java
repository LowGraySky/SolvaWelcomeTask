package kz.lowgraysky.solva.welcometask.configuration;

import kz.lowgraysky.solva.welcometask.entities.Currency;
import kz.lowgraysky.solva.welcometask.repositories.CurrencyRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class LoadInstancesOnStartup implements CommandLineRunner {

    private final CurrencyRepository currencyRepository;

    public LoadInstancesOnStartup(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        currencyRepository.save(new Currency("USD"));
        currencyRepository.save(new Currency("KZT"));
        currencyRepository.save(new Currency("RUB"));
    }
}
