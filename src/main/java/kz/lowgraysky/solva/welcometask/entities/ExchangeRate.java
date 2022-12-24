package kz.lowgraysky.solva.welcometask.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name = "EXCHANGE_RATE", schema = "public")
public class ExchangeRate extends BaseEntity{

    @Column(name = "SYMBOL", nullable = false)
    private String symbol;

    @Column(name = "OPEN", nullable = true)
    private BigDecimal open;

    @Column(name = "CLOSE", nullable = true)
    private BigDecimal close;

    @Column(name = "HIGH", nullable = true)
    private BigDecimal high;

    @Column(name = "LOW", nullable = true)
    private BigDecimal low;

    @Temporal(TemporalType.DATE)
    private LocalDate dateTime;

    public ExchangeRate(String symbol,
                        BigDecimal open,
                        BigDecimal close,
                        BigDecimal high,
                        BigDecimal low,
                        LocalDate dateTime) {
        this.symbol = symbol;
        this.open = open;
        this.close = close;
        this.high = high;
        this.low = low;
        this.dateTime = dateTime;
    }
}
