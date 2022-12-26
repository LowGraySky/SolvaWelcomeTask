package kz.lowgraysky.solva.welcometask.controllers.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import kz.lowgraysky.solva.welcometask.controllers.AbstractController;
import kz.lowgraysky.solva.welcometask.entities.BankAccount;
import kz.lowgraysky.solva.welcometask.entities.Transaction;
import kz.lowgraysky.solva.welcometask.entities.TransactionLimit;
import kz.lowgraysky.solva.welcometask.entities.enums.BankAccountOwnerType;
import kz.lowgraysky.solva.welcometask.entities.enums.ExpenseCategory;
import kz.lowgraysky.solva.welcometask.pojos.NewTransactionLimitPojo;
import kz.lowgraysky.solva.welcometask.pojos.TransactionLimitResponsePojo;
import kz.lowgraysky.solva.welcometask.pojos.TransactionWithLimitResponsePojo;
import kz.lowgraysky.solva.welcometask.services.BankAccountServiceBean;
import kz.lowgraysky.solva.welcometask.services.TransactionLimitServiceBean;
import kz.lowgraysky.solva.welcometask.services.TransactionsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/transaction")
public class ApiTransactionController extends AbstractController {

    private final TransactionsService transactionsService;
    private final TransactionLimitServiceBean transactionLimitService;
    private final BankAccountServiceBean bankAccountService;

    public ApiTransactionController(TransactionsService transactionsService,
                                    TransactionLimitServiceBean transactionLimitService,
                                    BankAccountServiceBean bankAccountService) {
        this.transactionsService = transactionsService;
        this.transactionLimitService = transactionLimitService;
        this.bankAccountService = bankAccountService;
    }

    @GetMapping("/limit_exceed/{client_address}")
    public ResponseEntity<?> getTransactionWithLimitExceed(
            @PathVariable(value = "client_address", required = true)
            @DecimalMax(value = "9999999999", message = "Account address must be not bigger than 9_999_999_999.")
            Long address){
        List<Transaction> transactions =  transactionsService.getAllTransactionWithTimeLimitExceed(address);
        List<TransactionWithLimitResponsePojo> responsePojos = transactions.stream()
                .map(transaction -> new TransactionWithLimitResponsePojo(
                        transaction.getAccountFrom().getAddress(),
                        transaction.getAccountTo().getAddress(),
                        transaction.getCurrency().getShortName(),
                        transaction.getSum(),
                        transaction.getExpenseCategory(),
                        transaction.getDateTime(),
                        transaction.getTransactionLimit().getAmount(),
                        transaction.getTransactionLimit().getStandByDate(),
                        transaction.getTransactionLimit().getCurrency().getShortName()
                ))
                .collect(Collectors.toList());
        return new ResponseEntity<>(responsePojos, HttpStatus.OK);
    }

    @PostMapping("/limit")
    public ResponseEntity<?> setNewLimit(@Valid @RequestBody NewTransactionLimitPojo newTransactionLimitPojo){
        BankAccount bankAccount = bankAccountService.getByAddress(newTransactionLimitPojo.getAccountAddress());
        if(bankAccount == null){
            bankAccount = bankAccountService.createAccount(
                    newTransactionLimitPojo.getAccountAddress(),
                    BankAccountOwnerType.CLIENT);
        }
        TransactionLimit limit = transactionLimitService.setNewLimit(
                newTransactionLimitPojo.getAmount(),
                ExpenseCategory.fromId(newTransactionLimitPojo.getExpenseCategory()),
                bankAccount.getAddress()
        );
        TransactionLimitResponsePojo responsePojo = new TransactionLimitResponsePojo(
                limit.getAmount(),
                limit.getExpenseCategory(),
                limit.getStandByDate(),
                limit.getCurrency().getShortName(),
                limit.getBankAccount().getAddress(),
                limit.getAvailableAmount(),
                limit.getMonth()
        );
        return new ResponseEntity<>(responsePojo, HttpStatus.OK);
    }

    @GetMapping("/limits/{client_address}")
    public ResponseEntity<?> getAllLimits(
            @PathVariable(value = "client_address", required = true)
            @DecimalMax(value = "9999999999", message = "Account address must be not bigger than 9_999_999_999.")
            Long address){
        List<TransactionLimit> limits = transactionLimitService.getAllByBankAccountAddress(address);
        List<TransactionLimitResponsePojo> responsePojos = limits.stream()
                .map( limit -> new TransactionLimitResponsePojo(
                        limit.getAmount(),
                        limit.getExpenseCategory(),
                        limit.getStandByDate(),
                        limit.getCurrency().getShortName(),
                        limit.getBankAccount().getAddress(),
                        limit.getAvailableAmount(),
                        limit.getMonth()
                ))
                .collect(Collectors.toList());
        return new ResponseEntity<>(responsePojos, HttpStatus.OK);
    }

}
