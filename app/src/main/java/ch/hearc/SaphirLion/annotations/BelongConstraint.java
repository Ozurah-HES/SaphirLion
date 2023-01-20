package ch.hearc.SaphirLion.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ch.hearc.SaphirLion.validator.BelongValidator2;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = BelongValidator2.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface BelongConstraint {
    String message() default "Element doest not belong to user";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}