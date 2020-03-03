package io.hh.lumberjack.shared;

import com.google.protobuf.ByteString;
import com.google.protobuf.Int64Value;
import io.hh.lumberjack.shared.proto.EnumProtos;
import io.hh.lumberjack.shared.proto.MetaProtos;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static java.util.Objects.nonNull;

public final class TestUtil {

    private TestUtil() {}

    public static MetaProtos.TimeInfo.Builder createTimeInfo(
            final Instant acquired,
            final Instant original) {
        final MetaProtos.TimeInfo.Builder builder = MetaProtos.TimeInfo.newBuilder();

        if (nonNull(acquired)) {
            builder.setAcquired(Int64Value.of(acquired.toEpochMilli()));
        }
        if (nonNull(original)) {
            builder.setOriginal(Int64Value.of(original.toEpochMilli()));
        }

        return builder;
    }

    public static MetaProtos.Meta.Builder createMeta(
            final UUID uuid,
            final EnumProtos.Source source,
            final MetaProtos.TimeInfo.Builder timeInfo) {

        final MetaProtos.Meta.Builder builder = MetaProtos.Meta.newBuilder();

        if (nonNull(uuid)) {
            builder.setUuid(ByteString.copyFromUtf8(uuid.toString()));
        }
        if (nonNull(timeInfo)) {
            builder.setTimeInfo(timeInfo);
        }
        if (nonNull(source)) {
            builder.setSource(source);
        }

        return builder;
    }

    public static MetaProtos.Meta.Builder createValidMeta() {
        final Instant acquired = Instant.now();
        final Instant original = acquired.minus(30, ChronoUnit.DAYS);
        return createMeta(UUID.randomUUID(), EnumProtos.Source.SOURCE_TWITTER, createTimeInfo(acquired, original));
    }
}
