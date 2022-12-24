package kz.lowgraysky.solva.welcometask.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;

@Data
@Entity
@Table(name = "BANK_ACCOUNT", schema = "public")
@NoArgsConstructor
public class BankAccount extends BaseEntity{

    @Digits(integer=10, fraction=2)
    @Column(name = "ADDRESS", length = 10, nullable = false, unique = true)
    private Long address;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BANK_ACCOUNT_OWNER_ID", nullable = false)
    private BankAccountOwner bankAccountOwner;
}
