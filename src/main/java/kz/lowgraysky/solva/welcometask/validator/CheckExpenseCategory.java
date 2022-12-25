package kz.lowgraysky.solva.welcometask.validator;

import jakarta.validation.Constraint;
import kz.lowgraysky.solva.welcometask.entities.enums.ExpenseCategory;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, PARAMETER})
@Constraint(validatedBy = ExpenseCategoryValidator.class)
@Retention(RUNTIME)
public @interface CheckExpenseCategory {
    String message() default "Expense category must be only 'SERVICE' or 'PRODUCT'.";
    Class<?>[] groups() default { };
    Class<?>[] payload() default { };
    ExpenseCategory value();
}
