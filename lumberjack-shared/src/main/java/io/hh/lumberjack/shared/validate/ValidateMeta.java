package io.hh.lumberjack.shared.validate;

import io.hh.lumberjack.shared.proto.EnumProtos;
import io.hh.lumberjack.shared.proto.MetaProtos;

import static java.util.Objects.isNull;

/**
 * Validates {@code MetaProtos.Meta} instances.
 *
 * @author houthacker
 */
public final class ValidateMeta {

    private ValidateMeta() {
        /* Prevent instantiation for utility classes */
    }

    /**
     * Validates the given meta and returns a result containing all validation errors that were encountered
     * during validation.
     *
     * @param meta The meta to validate.
     * @return The validation result.
     */
    public static ValidationResult validate(final MetaProtos.Meta meta) {
        final ValidationResult.Builder builder = ValidationResult.newBuilder();

        if (isNull(meta)) {
            builder.addValidationError(new ValidationError(ValidationErrorType.NULL_NOT_ALLOWED_HERE, "meta"));
        } else {
            if (meta.getUuid().isEmpty()) {
                builder.addValidationError(new ValidationError(ValidationErrorType.NULL_NOT_ALLOWED_HERE,
                        "meta.uuid"));
            }
            if (meta.getSource() == EnumProtos.Source.SOURCE_UNSPECIFIED
                    || meta.getSource() == EnumProtos.Source.UNRECOGNIZED) {
                builder.addValidationError(new ValidationError(ValidationErrorType.NULL_NOT_ALLOWED_HERE,
                        "meta.source"));
            }
            if (!meta.hasTimeInfo()) {
                builder.addValidationError(new ValidationError(ValidationErrorType.NULL_NOT_ALLOWED_HERE,
                        "meta.timeInfo"));
            } else if (!meta.getTimeInfo().hasAcquired()) {
                builder.addValidationError(new ValidationError(ValidationErrorType.NULL_NOT_ALLOWED_HERE,
                        "meta.timeInfo.acquired"));
            }
        }

        return builder.build();
    }

    /**
     * Convenience method to check the validity of the given {@code Meta.Builder}.
     *
     * @param meta The meta to validate.
     * @return The validation result.
     */
    public static ValidationResult validate(final MetaProtos.Meta.Builder meta) {
        if (isNull(meta)) {
            return validate((MetaProtos.Meta)null);
        }

        return validate(meta.build());

    }

    /**
     * Convenience method to check if the given meta is valid. If the given meta is {@code null},
     * it is considered invalid.
     *
     * @param meta The meta to check.
     * @return Whether the given meta is valid.
     */
    public static boolean isValid(final MetaProtos.Meta meta) {
        return validate(meta).isOK();
    }

    /**
     * Convenience method to check if the given {@code Meta.Builder} is valid. If the given meta is {@code null},
     * it is considered invalid.
     *
     * @param meta The meta to check.
     * @return Whether the given meta is valid.
     */
    public static boolean isValid(final MetaProtos.Meta.Builder meta) {
        return isValid(meta.build());
    }
}
