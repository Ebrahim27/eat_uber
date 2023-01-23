package com.ebi.snap_food.anotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AttackProtectionCreate.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface AttackProtection {

    String message() default  "{fieldInvalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
