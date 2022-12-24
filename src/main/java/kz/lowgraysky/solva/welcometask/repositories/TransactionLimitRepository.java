package kz.lowgraysky.solva.welcometask.repositories;

import kz.lowgraysky.solva.welcometask.entities.TransactionLimit;
import kz.lowgraysky.solva.welcometask.entities.enums.ExpenseCategory;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionLimitRepository extends EntityRepository<TransactionLimit>{

    TransactionLimit getByExpenseCategory(ExpenseCategory category);
}
