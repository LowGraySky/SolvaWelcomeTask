package kz.lowgraysky.solva.welcometask.repositories;

import kz.lowgraysky.solva.welcometask.entities.TransactionLimit;
import kz.lowgraysky.solva.welcometask.entities.enums.ExpenseCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Month;
import java.util.List;

@Repository
public interface TransactionLimitRepository extends EntityRepository<TransactionLimit>{

    List<TransactionLimit> getAllByBankAccountAddress(Long address);

    @Query(value = "" +
            "SELECT l FROM TransactionLimit l " +
            "WHERE l.expenseCategory = :category " +
            "AND l.bankAccount.address = :address " +
            "AND l.month = :month " +
            "AND l.standByDate = (" +
            "   SELECT max(t.standByDate) FROM TransactionLimit t" +
            "   WHERE t.expenseCategory = :category " +
            "   AND t.bankAccount.address = :address " +
            "   AND t.month = :month " +
            ")" )
    TransactionLimit getByExpenseCategoryAndMaxStandBy(ExpenseCategory category, Long address, Month month);
}
