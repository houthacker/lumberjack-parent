package io.hh.lumberjack.shared.validate;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * An error that occurred while validating. This exists to prevent the cost of throwing exceptions while
 * still enabling applications to act on invalid objects.
 *
 * @author houthacker
 */
public class ValidationError {

    private final ValidationErrorType type;

    private final String message;

    private final int hash;

    /**
     * Creates a new {@code ValidationError} of the given type and message.
     *
     * @param type The error type.
     * @param message The (optional) message.
     */
    public ValidationError(final ValidationErrorType type, final String message) {
        this.type = requireNonNull(type, "Cannot create ValidationError: type is null");
        this.message = message;
        this.hash = Objects.hash(type, message);
    }

    /**
     * Returns the specific type of the error.
     *
     * @return The error type.
     */
    public ValidationErrorType getType() {
        return type;
    }

    /**
     * Returns the error message, if there is one.
     *
     * @return The error message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ValidationError that = (ValidationError) o;
        return type == that.type
                && Objects.equals(message, that.message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return hash;
    }
}
