package kz.lowgraysky.solva.welcometask.controllers.api;

import kz.lowgraysky.solva.welcometask.controllers.AbstractController;
import kz.lowgraysky.solva.welcometask.services.TransactionsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/transaction")
public class ApiTransactionController extends AbstractController {

    private final TransactionsService transactionsService;

    public ApiTransactionController(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    @GetMapping("/limit_exceed")
    public ResponseEntity<?> getTransactionWithLimitExceed(){
        return new ResponseEntity<>(
                transactionsService.getAllTransactionWithTimeLimitExceed(),
                HttpStatus.OK
        );
    }

    @PostMapping("/limit")
    public ResponseEntity<?> setNewLimit(){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/limits")
    public ResponseEntity<?> getAllLimits(){
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
