package org.linlinjava.litemall.core.validator;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({METHOD, FIELD, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = OrderValidator.class)
public @interface Order {
  String message() default "排序类型不支持";

  String[] accepts() default {"desc", "asc"};

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
