package kz.lowgraysky.solva.welcometask.controllers;

import jakarta.validation.Valid;
import kz.lowgraysky.solva.welcometask.entities.Transaction;
import kz.lowgraysky.solva.welcometask.pojos.TransactionPojo;
import kz.lowgraysky.solva.welcometask.pojos.TransactionResponsePojo;
import kz.lowgraysky.solva.welcometask.services.TransactionLimitServiceBean;
import kz.lowgraysky.solva.welcometask.services.TransactionServiceBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/transaction")
public class TransactionController extends AbstractController{

    private final TransactionServiceBean transactionsService;
    private final TransactionLimitServiceBean transactionLimitService;

    public TransactionController(TransactionServiceBean transactionsService,
                                 TransactionLimitServiceBean transactionLimitService) {
        this.transactionsService = transactionsService;
        this.transactionLimitService = transactionLimitService;
    }

    @PostMapping(value = "", produces = "application/json;charset=utf-8")
    public ResponseEntity<?> insertTransaction(@Valid @RequestBody TransactionPojo transactionPojo){
        Transaction transaction = transactionsService.insertTransactionWithCheckOnLimit(transactionPojo);
        TransactionResponsePojo responsePojo = new TransactionResponsePojo(
                transaction.getAccountFrom().getAddress(),
                transaction.getAccountTo().getAddress(),
                transaction.getCurrency().getShortName(),
                transaction.getSum(),
                transaction.getExpenseCategory(),
                transaction.getDateTime()
        );
        return new ResponseEntity<>(responsePojo, HttpStatus.OK);
    }

}
