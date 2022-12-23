package kz.lowgraysky.solva.welcometask.entities;

import jakarta.persistence.*;
import kz.lowgraysky.solva.welcometask.entities.enums.BankAccountOwnerType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "BANK_ACCOUNT_OWNER", schema = "public")
@NoArgsConstructor
public class BankAccountOwner extends BaseEntity{

    @Enumerated(EnumType.STRING)
    @Column(name = "OWNER_TYPE", nullable = false)
    private BankAccountOwnerType ownerType;
}
