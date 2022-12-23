package kz.lowgraysky.solva.welcometask.repositories;

import kz.lowgraysky.solva.welcometask.entities.Currency;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends EntityRepository<Currency> {

    Currency getByShortName(String shortName);
}
