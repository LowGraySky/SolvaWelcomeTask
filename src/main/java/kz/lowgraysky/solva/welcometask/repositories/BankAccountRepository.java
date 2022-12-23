package kz.lowgraysky.solva.welcometask.repositories;

import kz.lowgraysky.solva.welcometask.entities.BankAccount;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends EntityRepository<BankAccount>{

    BankAccount getByAddress(Long address);
}
