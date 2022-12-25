package kz.lowgraysky.solva.welcometask.services;

import kz.lowgraysky.solva.welcometask.entities.BankAccount;
import kz.lowgraysky.solva.welcometask.entities.enums.BankAccountOwnerType;
import org.springframework.stereotype.Service;

@Service
public interface BankAccountService extends CommonService<BankAccount>{

    BankAccount getByAddress(Long address);

    BankAccount createAccount(Long address, BankAccountOwnerType type);
}
