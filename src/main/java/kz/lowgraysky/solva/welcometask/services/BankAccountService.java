package kz.lowgraysky.solva.welcometask.services;

import kz.lowgraysky.solva.welcometask.entities.BankAccount;
import org.springframework.stereotype.Service;

@Service
public interface BankAccountService extends CommonService<BankAccount>{

    BankAccount getByAddress(Long address);
}
