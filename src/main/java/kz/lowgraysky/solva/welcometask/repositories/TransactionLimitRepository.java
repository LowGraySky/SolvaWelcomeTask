package kz.lowgraysky.solva.welcometask.repositories;

import kz.lowgraysky.solva.welcometask.entities.TransactionLimit;
import kz.lowgraysky.solva.welcometask.entities.enums.ExpenseCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionLimitRepository extends EntityRepository<TransactionLimit>{

    @Query(value = "" +
            "SELECT l FROM TransactionLimit l " +
            "WHERE l.expenseCategory = :category " +
            "AND l.standByDate = (SELECT max(t.standByDate) FROM TransactionLimit t WHERE t.expenseCategory = :category )")
    TransactionLimit getByExpenseCategoryAndMaxStandBy(ExpenseCategory category);
}
