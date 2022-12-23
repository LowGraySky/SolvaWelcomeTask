package kz.lowgraysky.solva.welcometask.repositories;

import kz.lowgraysky.solva.welcometask.entities.BankAccountOwner;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountOwnerRepository extends EntityRepository<BankAccountOwner>{

    @Query(value = "SELECT o FROM BankAccountOwner o JOIN BankAccount b ON b.bankAccountOwner.id = o.id WHERE b.address = :address")
    BankAccountOwner getOwnerByBankAccountAddress(Long address);
}
