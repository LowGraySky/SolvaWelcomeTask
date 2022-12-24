package kz.lowgraysky.solva.welcometask.controllers;

import kz.lowgraysky.solva.welcometask.entities.Transaction;
import kz.lowgraysky.solva.welcometask.pojos.TransactionPojo;
import kz.lowgraysky.solva.welcometask.pojos.TransactionResponsePojo;
import kz.lowgraysky.solva.welcometask.services.TransactionServiceBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/transaction")
public class TransactionController extends AbstractController{

    private final TransactionServiceBean transactionsService;

    public TransactionController(TransactionServiceBean transactionsService) {
        this.transactionsService = transactionsService;
    }

    @PostMapping(value = "", produces = "application/json;charset=utf-8")
    public ResponseEntity<?> insertTransaction(@Valid @RequestBody TransactionPojo transactionPojo){
        Transaction transaction = transactionsService.transactionPojoToEntity(transactionPojo);
        transactionsService.insert(transaction);
        TransactionResponsePojo responsePojo = new TransactionResponsePojo(
                transaction.getAccountFrom().getAddress(),
                transaction.getAccountTo().getAddress(),
                transaction.getCurrency().getShortName(),
                transaction.getSum(),
                transaction.getExpenseCategory(),
                transaction.getDateTime(),
                transaction.getLimitSum(),
                transaction.getLimitDateTime(),
                transaction.getLimitCurrency().getShortName()
        );
        return new ResponseEntity<>(responsePojo, HttpStatus.OK);
    }

}
