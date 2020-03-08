package io.hh.lumberjack.metrics.validate;

import io.hh.lumberjack.metrics.proto.MetricProtos;
import io.hh.lumberjack.shared.validate.ProtoValidator;
import io.hh.lumberjack.shared.validate.ValidationResult;

import java.util.Arrays;
import java.util.Objects;

import static java.util.Objects.isNull;

/**
 * Validates {@code MetricProtos.Metric} instances.
 *
 * @author houthacker
 */
public class MetricValidator extends ProtoValidator<MetricProtos.Metric, MetricProtos.Metric.Builder> {

    private static final MetricValidator INSTANCE = new MetricValidator();

    private MetricValidator() {
        super(Arrays.asList(
                rule("metric", Objects::nonNull, "metric must have a value", true),
                rule("metric.name", (metric) -> Objects.nonNull(metric.getName()),
                        "metric.name must have a value"),
                rule("metric.epochSeconds", (metric) -> !metric.hasEpochSeconds(),
                        "metric.epochSeconds must have a value"),
                rule("metric.value", (metric) -> !(metric.hasDoubleValue() || metric.hasLongValue()),
                        "metric.value must have a value"),
                rule("metric.tags", (metric) -> metric.getTagsCount() < 1,
                        "metric must contain at least one tag")
        ));
    }

    public static MetricValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public ValidationResult validate(final MetricProtos.Metric.Builder builder) {
        if (isNull(builder)) {
            return validate((MetricProtos.Metric)null);
        }

        return validate(builder.build());
    }
}
