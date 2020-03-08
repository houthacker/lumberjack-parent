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

    private final String ruleName;

    private final String message;

    private final int hash;

    /**
     * Creates a new {@code ValidationError} of the given type and message.
     *
     * @param ruleName The rule name.
     * @param message The (optional) message.
     */
    public ValidationError(final String ruleName, final String message) {
        this.ruleName = requireNonNull(ruleName, "Cannot create ValidationError: ruleName is null");
        this.message = message;
        this.hash = Objects.hash(ruleName, message);
    }

    /**
     * Returns the name of the failed rule.
     *
     * @return The rule name.
     */
    public String getRuleName() {
        return ruleName;
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
        return ruleName.equals(that.ruleName)
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
