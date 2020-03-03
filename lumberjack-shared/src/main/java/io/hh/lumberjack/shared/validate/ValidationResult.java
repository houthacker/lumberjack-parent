package io.hh.lumberjack.shared.validate;


import java.util.*;

import static java.util.Objects.isNull;

/**
 * A class containing a list of errors which are encountered during validation.
 *
 * @author houthacker
 */
public final class ValidationResult {

    /**
     * This {@code ValidationResult} should be used to indicate that the validation did not
     * yield any errors.
     */
    public static final ValidationResult OK = new ValidationResult(Collections.emptyList());

    private final List<ValidationError> errors;

    private final int hash;

    private ValidationResult(final List<ValidationError> errors) {
        this.errors = errors;
        this.hash = Objects.hash(errors);
    }

    /**
     * Returns the list of errors. If this is an {@code OK} result, an empty list will
     * be returned.
     *
     * @return The list of errors.
     */
    public List<ValidationError> getErrors() {
        return errors;
    }

    /**
     * Returns whether this {@code ValidationResult} contains any errors.
     * A {@code null} result is handled as an error result.
     *
     * @return Whether this result contains any errors.
     */
    public boolean isOK() {
        return (this == OK || this.errors.isEmpty());
    }

    /**
     * Creates a new {@code Builder} for {@code ValidationResult} instances.
     *
     * @return the new {@code Builder}.
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ValidationResult that = (ValidationResult) o;

        return Arrays.equals(errors.toArray(new ValidationError[0]), that.errors.toArray(new ValidationError[0]));
    }

    @Override
    public int hashCode() {
        return hash;
    }

    public static class Builder {

        private final List<ValidationError> errors;

        private Builder() {
            errors = new ArrayList<>();
        }

        /**
         * Adds the given error if it is not null.
         *
         * @param error The error to add.
         * @return This builder instance.
         */
        public Builder addValidationError(final ValidationError error) {
            if (!isNull(error)) {
                errors.add(error);
            }

            return this;
        }

        /**
         * If no errors were added, returns {@code ValidationResult.OK}, otherwise creates a new
         * {@code ValidationResult} based on the provided errors.
         *
         * @return The validation result.
         */
        public ValidationResult build() {
            if (errors.isEmpty()) {
                return ValidationResult.OK;
            }

            return new ValidationResult(Collections.unmodifiableList(errors));
        }
    }
}
