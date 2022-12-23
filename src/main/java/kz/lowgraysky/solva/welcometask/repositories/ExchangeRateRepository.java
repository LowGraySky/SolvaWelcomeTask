package kz.lowgraysky.solva.welcometask.repositories;

import kz.lowgraysky.solva.welcometask.entities.ExchangeRate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ExchangeRateRepository extends EntityRepository<ExchangeRate>{

    @Query(value = "SELECT e FROM ExchangeRate e where e.symbol = :symbol AND e.dateTime = :dateTime")
    ExchangeRate getByDateTimeAndSymbol(String symbol, LocalDateTime dateTime);
}
