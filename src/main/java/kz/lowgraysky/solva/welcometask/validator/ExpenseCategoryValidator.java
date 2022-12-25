package kz.lowgraysky.solva.welcometask.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kz.lowgraysky.solva.welcometask.entities.enums.ExpenseCategory;

public class ExpenseCategoryValidator implements ConstraintValidator<CheckExpenseCategory, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s == null) {
            return true;
        }
        return ExpenseCategory.fromId(s) != null;
    }
}
