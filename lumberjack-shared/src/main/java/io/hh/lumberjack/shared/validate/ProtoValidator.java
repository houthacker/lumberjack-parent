package io.hh.lumberjack.shared.validate;

import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.MessageOrBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A dynamic validator for protobuf messages.
 *
 * @param <T> The type of protobuf to be validated.
 *
 * @author houthacker
 */
public abstract class ProtoValidator<T extends GeneratedMessageV3,B extends MessageOrBuilder> {

    private final List<ProtoValidationRule<T>> orderedRules;

    public ProtoValidator(final List<ProtoValidationRule<T>> rules) {
        this.orderedRules = Collections.synchronizedList(Collections.unmodifiableList(rules));
    }

    protected static <T extends GeneratedMessageV3> ProtoValidationRule<T> rule(
            final String name,
            final ProtoValidation<T> validation,
            final String errorMessage) {
        return new ProtoValidationRule<>(name, validation, errorMessage);
    }

    protected static <T extends GeneratedMessageV3> ProtoValidationRule<T> rule(
            final String name,
            final ProtoValidation<T> validation,
            final String errorMessage,
            final boolean finalRule) {
        return new ProtoValidationRule<>(name, validation, errorMessage, finalRule);
    }

    protected List<ProtoValidationRule<T>> validateInternal(final T message) {
        final List<ProtoValidationRule<T>> failedRules = new ArrayList<>();

        for (final ProtoValidationRule<T> rule : orderedRules) {
            if (!rule.validate(message)) {
                failedRules.add(rule);

                if (rule.isStopRule()) {
                    return failedRules;
                }
            }
        }

        return failedRules;
    }

    /**
     * Validates the given message and returns the result. A {@code null} message is considered invalid.
     *
     * @param message The message to validate.
     * @return The validation result.
     */
    public ValidationResult validate(final T message) {
        final List<ProtoValidationRule<T>> failedRules = validateInternal(message);

        if (failedRules.isEmpty()) {
            return ValidationResult.OK;
        }

        final ValidationResult.Builder builder = ValidationResult.newBuilder();

        failedRules.forEach(failedRule -> builder.addValidationError(
                new ValidationError(failedRule.getName(), failedRule.getErrorMessage())));

        return builder.build();
    }

    /**
     * Validates the given builder and returns the result. A {@code null} builder is considered invalid.
     *
     * @param builder The builder to validate.
     * @return The validation result.
     */
    public abstract ValidationResult validate(final B builder);

    /**
     * Returns whether the given message is valid.
     *
     * @param message The message to validate.
     * @return Whether the given message is valid.
     */
    public boolean isValid(final T message) {
        return validate(message).isOK();
    }

    /**
     * Returns whether the given builder is valid.
     *
     * @param builder The builder to validate.
     * @return Whether the given builder is valid.
     */
    public boolean isValid(final B builder) {
        return validate(builder).isOK();
    }
}
