package io.hh.lumberjack.shared.validate;

import com.google.protobuf.GeneratedMessageV3;

import static java.util.Objects.requireNonNull;

/**
 * Rule to validate protobuf messages.
 *
 * @param <T> The actual message type.
 *
 * @author houthacker
 */
public class ProtoValidationRule<T extends GeneratedMessageV3> {

    private final String name;

    private final ProtoValidation<T> validation;

    private final String errorMessage;

    private final boolean stopRule;

    /**
     * Creates a new {@code ProtoValidationRule}.
     *
     * @param name The name of the rule. Must be unique within its context.
     * @param validation The actual validation to execute.
     * @param errorMessage The error message to report when a validation fails.
     */
    public ProtoValidationRule(final String name, final ProtoValidation<T> validation, final String errorMessage) {
        this(name, validation, errorMessage, false);
    }

    /**
     * Creates a new {@code ProtoValidationRule}, with an option to make the rule a stop rule.
     *
     * @param name The name of the rule. Must be unique within its context.
     * @param validation The actual validation to execute.
     * @param errorMessage The error message to report when a validation fails.
     * @param stopRule Whether this rule is a stop rule.
     */
    public ProtoValidationRule(final String name, final ProtoValidation<T> validation, final String errorMessage,
                               final boolean stopRule) {
        this.name = requireNonNull(name, "Cannot create ProtoValidationRule: name is null");
        this.validation = requireNonNull(validation, "Cannot create ProtoValidationRule: validation is null");
        this.errorMessage = requireNonNull(errorMessage, "Cannot create ProtoValidationRule: errorMessage is null");
        this.stopRule = stopRule;
    }

    /**
     * Returns the rule name.
     *
     * @return The rule name.
     */
    public String getName() {
        return name;
    }

    /**
     * Validates the given message an returns whether is it valid.
     *
     * @param message The message to validate.
     * @return Whether the given message is valid according to this rule.
     */
    public boolean validate(final T message) {
        return validation.isValid(message);
    }

    /**
     * Returns the defined error message for this rule.
     *
     * @return The error message.
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Whether this is a final rule.
     * If a final rule decides a given message is invalid, no other rules are processed after.
     *
     * @return Whether this is a final rule.
     */
    public boolean isStopRule() {
        return stopRule;
    }
}
