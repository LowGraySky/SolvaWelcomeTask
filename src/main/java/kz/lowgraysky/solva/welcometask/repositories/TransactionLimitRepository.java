package kz.lowgraysky.solva.welcometask.repositories;

import kz.lowgraysky.solva.welcometask.entities.TransactionLimit;
import kz.lowgraysky.solva.welcometask.entities.enums.ExpenseCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionLimitRepository extends EntityRepository<TransactionLimit>{

//    @Query(value =
//            "SELECT " +
//            "   * " +
//            "FROM public.transaction_limit " +
//            "   WHERE stand_by = ( " +
//            "       SELECT " +
//            "           max(stand_by) " +
//            "       FROM transaction_limit " +
//            "           WHERE expense_category = :category " +
//            "   ) " +
//            "   AND expense_category = :category ", nativeQuery = true)

    @Query(value = "" +
            "select l from TransactionLimit l " +
            "where l.expenseCategory = :category " +
            "AND l.standByDate = (select max(t.standByDate) from TransactionLimit t where t.expenseCategory = :category )")
    TransactionLimit getByExpenseCategoryAndMaxStandBy(ExpenseCategory category);
}
