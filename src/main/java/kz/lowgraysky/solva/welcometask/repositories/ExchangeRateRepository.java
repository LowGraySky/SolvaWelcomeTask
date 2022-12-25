package kz.lowgraysky.solva.welcometask.repositories;

import kz.lowgraysky.solva.welcometask.entities.ExchangeRate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ExchangeRateRepository extends EntityRepository<ExchangeRate>{

    @Query(value = "SELECT DISTINCT e FROM ExchangeRate e where e.symbol = :symbol AND e.dateTime = :date")
    ExchangeRate getByDateTimeAndSymbol(String symbol, LocalDate date);

    @Query(value =
            "SELECT e FROM ExchangeRate e " +
            "WHERE e.symbol = :symbol " +
            "AND e.close IS NOT NULL " +
            "AND e.dateTime = ( " +
            "   SELECT max(r.dateTime) FROM ExchangeRate r " +
            "   WHERE r.symbol = :symbol " +
            "   AND r.close IS NOT NULL " +
            ")")
    ExchangeRate getBySymbolAndLastDateAndCloseIsNotNull(String symbol);
}
