package com.sicnu.common.annotation.ConstraintValidator;

import com.sicnu.common.annotation.MustInt;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * TODO
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/11/2 13:30
 */
public class MustIntConstraintValidator implements ConstraintValidator<MustInt,Integer> {
    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        return true;
    }
}
