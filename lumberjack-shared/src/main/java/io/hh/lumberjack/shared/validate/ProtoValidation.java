package io.hh.lumberjack.shared.validate;

import com.google.protobuf.GeneratedMessageV3;

/**
 * Validation rule for {@code ProtoValidator}. Register instances of this interface at the {@code ProtoValidator}
 * to use thm when validating a message.
 *
 * @param <T> The actual message type.
 */
@FunctionalInterface
public interface ProtoValidation<T extends GeneratedMessageV3> {

    /**
     * Validates the given proto.
     *
     * @param proto The proto to be validated.
     * @return Whether the given proto is valid.
     */
    boolean isValid(final T proto);
}
