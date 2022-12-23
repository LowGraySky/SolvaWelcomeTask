package kz.lowgraysky.solva.welcometask.services;

import kz.lowgraysky.solva.welcometask.entities.Currency;
import kz.lowgraysky.solva.welcometask.exceptions.MissingDataException;
import kz.lowgraysky.solva.welcometask.repositories.CurrencyRepository;
import org.springframework.stereotype.Service;

@Service
public class CurrencyServiceBean implements CurrencyService{

    private final CurrencyRepository currencyRepository;

    public CurrencyServiceBean(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public Currency save(Currency inst) {
        return null;
    }

    @Override
    public Currency getById(long id) {
        return null;
    }

    @Override
    public Currency getByShortName(String shortName) {
        Currency currency = currencyRepository.getByShortName(shortName);
        if(currency == null){
            throw new MissingDataException(String.format("No data for %s and %s shortname", Currency.class, shortName));
        }
        return currency;
    }
}
