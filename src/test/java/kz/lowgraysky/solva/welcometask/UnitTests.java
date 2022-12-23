package kz.lowgraysky.solva.welcometask;

import kz.lowgraysky.solva.welcometask.controllers.TransactionController;
import kz.lowgraysky.solva.welcometask.controllers.api.ApiTransactionController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UnitTests {

    @Autowired
    private TransactionController transactionController;

    @Autowired
    private ApiTransactionController apiTransactionController;

    @Test
    public void transactionControllerLoads() throws Exception {
        assertThat(transactionController).isNotNull();
    }

    @Test
    public void apiTransactionControllerLoads() throws Exception {
        assertThat(apiTransactionController).isNotNull();
    }

}
