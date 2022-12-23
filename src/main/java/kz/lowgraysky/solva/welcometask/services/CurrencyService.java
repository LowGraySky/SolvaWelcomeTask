package kz.lowgraysky.solva.welcometask.services;

import kz.lowgraysky.solva.welcometask.entities.Currency;

public interface CurrencyService extends CommonService<Currency> {

    Currency getByShortName(String shortName);
}
