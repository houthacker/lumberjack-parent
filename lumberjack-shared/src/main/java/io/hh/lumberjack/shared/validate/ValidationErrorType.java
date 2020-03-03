package io.hh.lumberjack.shared.validate;

/**
 * An enum containing all types of error which can occur when validating an object.
 *
 * @author houthacker
 */
public enum ValidationErrorType {

    /**
     * Indicates an error occurred, but the type of error is unknown.
     */
    UNKNOWN,

    /**
     * Indicates a null value is encountered where it is not allowed.
     */
    NULL_NOT_ALLOWED_HERE
}
