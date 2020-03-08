package io.hh.lumberjack.shared.validate.meta;

import io.hh.lumberjack.shared.proto.EnumProtos;
import io.hh.lumberjack.shared.proto.MetaProtos;
import io.hh.lumberjack.shared.validate.ProtoValidator;
import io.hh.lumberjack.shared.validate.ValidationResult;

import java.util.Arrays;
import java.util.Objects;

import static java.util.Objects.isNull;

/**
 * Validates {@code MetaProtos.Meta} instances.
 *
 * @author houthacker
 */
public final class MetaValidator extends ProtoValidator<MetaProtos.Meta, MetaProtos.Meta.Builder> {

    private static final MetaValidator INSTANCE = new MetaValidator();

    private MetaValidator() {
        super(Arrays.asList(
                rule("meta", Objects::nonNull, "meta must have a value",true),
                rule("meta.uuid", (meta) -> !meta.getUuid().isEmpty(),"meta.uuid must have a value"),
                rule("meta.source", (meta) -> meta.getSource() != EnumProtos.Source.SOURCE_UNSPECIFIED,
                        "meta.source must be specified"),
                rule("meta.timeInfo", MetaProtos.Meta::hasTimeInfo,"meta.timeInfo must have a value"),
                rule("meta.timeInfo.acquired", (meta) -> meta.getTimeInfo().hasAcquired(),
                        "meta.tineInfo.acquired must have a value")
        ));
    }

    public static MetaValidator getInstance() {
        return INSTANCE;
    }

    /**
     * Convenience method to check the validity of the given {@code Meta.Builder}.
     *
     * @param meta The meta to validate.
     * @return The validation result.
     */
    @Override
    public ValidationResult validate(final MetaProtos.Meta.Builder meta) {
        if (isNull(meta)) {
            return validate((MetaProtos.Meta)null);
        }

        return validate(meta.build());

    }
}
