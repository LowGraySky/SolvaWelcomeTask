package kz.lowgraysky.solva.welcometask.entities;

import jakarta.persistence.*;
import kz.lowgraysky.solva.welcometask.entities.enums.BankAccountOwnerType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "BANK_ACCOUNT", schema = "public")
@NoArgsConstructor
public class BankAccount extends BaseEntity{

    @Column(name = "ADDRESS", length = 10, nullable = false, unique = true)
    private Long address;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", nullable = false)
    private BankAccountOwnerType type;
}
