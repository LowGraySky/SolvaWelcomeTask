package kz.lowgraysky.solva.welcometask.services;

import kz.lowgraysky.solva.welcometask.entities.BankAccount;
import kz.lowgraysky.solva.welcometask.entities.enums.BankAccountOwnerType;
import kz.lowgraysky.solva.welcometask.repositories.BankAccountRepository;
import kz.lowgraysky.solva.welcometask.utils.BeanHelper;
import org.springframework.stereotype.Service;

@Service
public class BankAccountServiceBean extends BeanHelper implements BankAccountService{

    private final BankAccountRepository bankAccountRepository;

    public BankAccountServiceBean(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public BankAccount save(BankAccount inst) {
        BankAccount result = bankAccountRepository.save(inst);
        logger.info(String.format("Save %s entity: %s", BankAccount.class, result.toString()));
        return result;
    }

    @Override
    public BankAccount getById(long id) {
        return bankAccountRepository.findById(id).orElse(null);
    }

    @Override
    public BankAccount getByAddress(Long address) {
        return bankAccountRepository.getByAddress(address);
    }

    @Override
    public BankAccount createAccount(Long address, BankAccountOwnerType type) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAddress(address);
        bankAccount.setType(type);
        return save(bankAccount);
    }
}
