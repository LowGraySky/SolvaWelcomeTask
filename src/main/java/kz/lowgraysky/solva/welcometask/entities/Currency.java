package kz.lowgraysky.solva.welcometask.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "CURRENCY", schema = "public")
public class Currency extends BaseEntity{

    @Column(name = "SHORT_NAME", length = 3, nullable = false, unique = true)
    private String shortName;

    public Currency(String shortName){
        this.shortName = shortName;
    }
}
