package com.example.bigevent.validation;

import com.example.bigevent.anno.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

//为自定义注解State提供校验规则
//ConstraintValidator<给哪个注解提供校验规则, 校验的数据类型>
public class StateValidation implements ConstraintValidator<State, String> {
    /**
     * @param value   将来要校验的数据
     * @param context context in which the constraint is evaluated
     * @return
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return value.equals("已发布") || value.equals("草稿");
    }
}
