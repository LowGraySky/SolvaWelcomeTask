package kz.lowgraysky.solva.welcometask.controllers.api;

import kz.lowgraysky.solva.welcometask.controllers.AbstractController;
import kz.lowgraysky.solva.welcometask.entities.TransactionLimit;
import kz.lowgraysky.solva.welcometask.entities.enums.ExpenseCategory;
import kz.lowgraysky.solva.welcometask.pojos.NewTransactionLimitPojo;
import kz.lowgraysky.solva.welcometask.pojos.TransactionLimitResponsePojo;
import kz.lowgraysky.solva.welcometask.services.TransactionLimitServiceBean;
import kz.lowgraysky.solva.welcometask.services.TransactionsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
        return new ResponseEntity<>(
                transactionsService.getAllTransactionWithTimeLimitExceed(),
                HttpStatus.OK
        );
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
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
