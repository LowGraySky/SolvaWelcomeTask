package kz.lowgraysky.solva.welcometask.services;

import kz.lowgraysky.solva.welcometask.entities.BankAccount;
import kz.lowgraysky.solva.welcometask.exceptions.MissingDataException;
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
        BankAccount bankAccount = bankAccountRepository.getByAddress(address);
        if(bankAccount == null){
            throw new MissingDataException(String.format("No data for %s and %d address", BankAccount.class, address));
        }
        return bankAccount;
    }
}
