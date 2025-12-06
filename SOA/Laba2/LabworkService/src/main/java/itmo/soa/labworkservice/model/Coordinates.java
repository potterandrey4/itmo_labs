package itmo.soa.labworkservice.model;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ValidCoordinates
public class Coordinates {
    @NotBlank(message = "Coordinate x is required")
    private String x;

    @NotBlank(message = "Coordinate y is required")
    private String y;

    public Coordinates() {
    }

    public Coordinates(String x, String y) {
        this.x = x;
        this.y = y;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }
}

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CoordinatesValidator.class)
@Documented
@interface ValidCoordinates {
    String message() default "Invalid coordinates";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

class CoordinatesValidator implements ConstraintValidator<ValidCoordinates, Coordinates> {
    @Override
    public boolean isValid(Coordinates coordinates, ConstraintValidatorContext context) {
        if (coordinates == null) {
            return true;
        }

        context.disableDefaultConstraintViolation();

        boolean valid = true;

        // Проверка X - должно быть числом типа Double
        if (coordinates.getX() != null) {
            try {
                Double.parseDouble(coordinates.getX());
            } catch (NumberFormatException e) {
                context.buildConstraintViolationWithTemplate("Coordinate x must be a valid number")
                        .addPropertyNode("x")
                        .addConstraintViolation();
                valid = false;
            }
        }

        // Проверка Y - должно быть числом типа Double и > -156
        if (coordinates.getY() != null) {
            try {
                double yValue = Double.parseDouble(coordinates.getY());
                if (yValue <= -156) {
                    context.buildConstraintViolationWithTemplate("Coordinate y must be greater than -156")
                            .addPropertyNode("y")
                            .addConstraintViolation();
                    valid = false;
                }
            } catch (NumberFormatException e) {
                context.buildConstraintViolationWithTemplate("Coordinate y must be a valid number")
                        .addPropertyNode("y")
                        .addConstraintViolation();
                valid = false;
            }
        }

        return valid;
    }
}
