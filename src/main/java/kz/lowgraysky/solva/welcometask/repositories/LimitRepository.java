package kz.lowgraysky.solva.welcometask.repositories;

import kz.lowgraysky.solva.welcometask.entities.enums.ExpenseCategory;
import kz.lowgraysky.solva.welcometask.entities.TransactionLimit;
import org.springframework.stereotype.Repository;

@Repository
public interface LimitRepository extends EntityRepository<TransactionLimit> {

    //TransactionLimit getByExpenseCategory(ExpenseCategory category);
}
