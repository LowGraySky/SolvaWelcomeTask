package kz.lowgraysky.solva.welcometask.controllers.api;

import jakarta.validation.Valid;
import kz.lowgraysky.solva.welcometask.controllers.AbstractController;
import kz.lowgraysky.solva.welcometask.entities.Transaction;
import kz.lowgraysky.solva.welcometask.entities.TransactionLimit;
import kz.lowgraysky.solva.welcometask.entities.enums.ExpenseCategory;
import kz.lowgraysky.solva.welcometask.pojos.NewTransactionLimitPojo;
import kz.lowgraysky.solva.welcometask.pojos.TransactionLimitResponsePojo;
import kz.lowgraysky.solva.welcometask.pojos.TransactionWithLimitResponsePojo;
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

    public ApiTransactionController(TransactionsService transactionsService,
                                    TransactionLimitServiceBean transactionLimitService) {
        this.transactionsService = transactionsService;
        this.transactionLimitService = transactionLimitService;
    }

    @GetMapping("/limit_exceed")
    public ResponseEntity<?> getTransactionWithLimitExceed(){
        List<Transaction> transactions =  transactionsService.getAllTransactionWithTimeLimitExceed();
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
        TransactionLimit limit = transactionLimitService.setNewLimit(
                newTransactionLimitPojo.getAmount(),
                ExpenseCategory.fromId(newTransactionLimitPojo.getExpenseCategory()));
        TransactionLimitResponsePojo responsePojo = new TransactionLimitResponsePojo(
                limit.getAmount(),
                limit.getExpenseCategory(),
                limit.getStandByDate(),
                limit.getCurrency().getShortName()
        );
        return new ResponseEntity<>(responsePojo, HttpStatus.OK);
    }

    @GetMapping("/limits")
    public ResponseEntity<?> getAllLimits(){
        List<TransactionLimit> limits = transactionLimitService.getAllLimits();
        List<TransactionLimitResponsePojo> responsePojos = limits.stream()
                .map( limit -> new TransactionLimitResponsePojo(
                        limit.getAmount(),
                        limit.getExpenseCategory(),
                        limit.getStandByDate(),
                        limit.getCurrency().getShortName()
                ))
                .collect(Collectors.toList());
        return new ResponseEntity<>(responsePojos, HttpStatus.OK);
    }

}
