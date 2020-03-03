package io.hh.lumberjack.shared.validate;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ValidationResultTest {

    @Test
    public void testConstruct() {
        final ValidationResult errorResult = ValidationResult.newBuilder()
                .addValidationError(new ValidationError(ValidationErrorType.NULL_NOT_ALLOWED_HERE, "message"))
                .build();
        final ValidationResult okResult = ValidationResult.newBuilder().build();

        assertThat(okResult.isOK(), is(true));

        assertThat(errorResult.isOK(), is(false));
        assertThat(errorResult.getErrors().size(), is(1));

        final ValidationError error = errorResult.getErrors().get(0);
        assertThat(error.getType(), is(ValidationErrorType.NULL_NOT_ALLOWED_HERE));
        assertThat(error.getMessage(), is("message"));
    }

    @Test
    public void testEquality() {
        final ValidationResult r1 = ValidationResult.newBuilder()
                .addValidationError(new ValidationError(ValidationErrorType.NULL_NOT_ALLOWED_HERE, "message"))
                .build();
        final ValidationResult r2 = ValidationResult.newBuilder()
                .addValidationError(new ValidationError(ValidationErrorType.NULL_NOT_ALLOWED_HERE, "message"))
                .build();
        final ValidationResult r3 = ValidationResult.newBuilder()
                .addValidationError(new ValidationError(ValidationErrorType.NULL_NOT_ALLOWED_HERE, null))
                .build();

        // .equals
        assertThat(r1.equals(null), is(false));
        assertThat(r1.equals(r1), is(true));
        assertThat(r1.equals(r2), is(true));
        assertThat(r2.equals(r1), is(true));
        assertThat(r1.equals(r3), is(false));
        assertThat(r3.equals(r1), is(false));

        // .hashcode
        assertThat(r1.hashCode(), is(r2.hashCode()));
    }
}
