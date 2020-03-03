package io.hh.lumberjack.shared.validate;

import io.hh.lumberjack.shared.TestUtil;
import io.hh.lumberjack.shared.proto.EnumProtos;
import io.hh.lumberjack.shared.proto.MetaProtos;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ValidateMetaTest {

    @Test
    public void testValidateWithNullObject() {
        final MetaProtos.Meta meta = null;
        final MetaProtos.Meta.Builder builder = null;

        final ValidationResult metaResult = ValidateMeta.validate(meta);
        final ValidationResult builderResult = ValidateMeta.validate(builder);

        // Assert that metaResult and builderResult are equal, and continue validation on either.
        assertThat(metaResult, equalTo(builderResult));
        assertThat(metaResult.isOK(), is(false));
        assertThat(ValidateMeta.isValid(meta), is(false));

        final List<ValidationError> errors = metaResult.getErrors();
        assertNotNull(errors, "Expect non-null list of errors");
        assertThat(errors.size(), is(1));

        final ValidationError error = errors.get(0);
        assertNotNull(error, "Expect non-null error");
        assertThat(error.getType(), is(ValidationErrorType.NULL_NOT_ALLOWED_HERE));
        assertThat(error.getMessage(), is("meta"));
    }

    @Test
    public void testValidateWithoutUUID() {
        final Instant acquired = Instant.now();
        final Instant original = acquired.minus(30, ChronoUnit.DAYS);
        final EnumProtos.Source source = EnumProtos.Source.TWITTER;

        // Create meta
        final MetaProtos.TimeInfo.Builder validTimeInfo = TestUtil.createTimeInfo(acquired, original);
        final MetaProtos.Meta.Builder metaWithoutUuid = TestUtil.createMeta(null, source, validTimeInfo);

        // MetaProtos.Meta without uuid
        final ValidationResult result = ValidateMeta.validate(metaWithoutUuid);
        assertThat(result.isOK(), is(false));
        assertThat(ValidateMeta.isValid(metaWithoutUuid), is(false));
        assertThat(ValidateMeta.isValid(metaWithoutUuid.build()), is(false));

        final List<ValidationError> errors = result.getErrors();
        assertNotNull(errors, "Expect non-null list of errors");
        assertThat(errors.size(), is(1));

        final ValidationError error = errors.get(0);
        assertNotNull(error, "Expect non-null error");
        assertThat(error.getType(), is(ValidationErrorType.NULL_NOT_ALLOWED_HERE));
        assertThat(error.getMessage(), is("meta.uuid"));
    }

    @Test
    public void testValidateWithoutSource() {
        final UUID uuid = UUID.randomUUID();
        final Instant acquired = Instant.now();
        final Instant original = acquired.minus(30, ChronoUnit.DAYS);

        // Create meta
        final MetaProtos.TimeInfo.Builder validTimeInfo = TestUtil.createTimeInfo(acquired, original);
        final MetaProtos.Meta.Builder metaWithoutSource = TestUtil.createMeta(uuid, null, validTimeInfo);

        // MetaProtos.Meta without source
        final ValidationResult result = ValidateMeta.validate(metaWithoutSource);
        assertThat(result.isOK(), is(false));
        assertThat(ValidateMeta.isValid(metaWithoutSource), is(false));
        assertThat(ValidateMeta.isValid(metaWithoutSource.build()), is(false));

        final List<ValidationError> errors = result.getErrors();
        assertNotNull(errors, "Expect non-null list of errors");
        assertThat(errors.size(), is(1));

        final ValidationError error = errors.get(0);
        assertNotNull(error, "Expect non-null error");
        assertThat(error.getType(), is(ValidationErrorType.NULL_NOT_ALLOWED_HERE));
        assertThat(error.getMessage(), is("meta.source"));
    }

    @Test
    public void testValidateWithoutTimeInfo() {
        final UUID uuid = UUID.randomUUID();
        final EnumProtos.Source source = EnumProtos.Source.TWITTER;

        // Create meta
        final MetaProtos.Meta.Builder metaWithoutTimeInfo = TestUtil.createMeta(uuid, source, null);

        // MetaProtos.Meta without timeInfo
        final ValidationResult result = ValidateMeta.validate(metaWithoutTimeInfo);
        assertThat(result.isOK(), is(false));
        assertThat(ValidateMeta.isValid(metaWithoutTimeInfo), is(false));
        assertThat(ValidateMeta.isValid(metaWithoutTimeInfo.build()), is(false));

        final List<ValidationError> errors = result.getErrors();
        assertNotNull(errors, "Expect non-null list of errors");
        assertThat(errors.size(), is(1));

        final ValidationError error = errors.get(0);
        assertNotNull(error, "Expect non-null error");
        assertThat(error.getType(), is(ValidationErrorType.NULL_NOT_ALLOWED_HERE));
        assertThat(error.getMessage(), is("meta.timeInfo"));
    }

    @Test
    public void testValidateWithoutTimeInfoAcquired() {
        final UUID uuid = UUID.randomUUID();
        final Instant acquired = Instant.now();
        final Instant original = acquired.minus(30, ChronoUnit.DAYS);
        final EnumProtos.Source source = EnumProtos.Source.TWITTER;

        // Create meta
        final MetaProtos.TimeInfo.Builder timeInfoWithoutAcquired = TestUtil.createTimeInfo(null, original);
        final MetaProtos.Meta.Builder metaWithInvalidTimeInfo   = TestUtil.createMeta(uuid, source, timeInfoWithoutAcquired);

        // MetaProtos.Meta without timeInfo.acquired
        final ValidationResult result = ValidateMeta.validate(metaWithInvalidTimeInfo);
        assertThat(result.isOK(), is(false));
        assertThat(ValidateMeta.isValid(metaWithInvalidTimeInfo), is(false));
        assertThat(ValidateMeta.isValid(metaWithInvalidTimeInfo.build()), is(false));

        final List<ValidationError> errors = result.getErrors();
        assertNotNull(errors, "Expect non-null list of errors");
        assertThat(errors.size(), is(1));

        final ValidationError error = errors.get(0);
        assertNotNull(error, "Expect non-null error");
        assertThat(error.getType(), is(ValidationErrorType.NULL_NOT_ALLOWED_HERE));
        assertThat(error.getMessage(), is("meta.timeInfo.acquired"));
    }

    @Test
    public void testValidateWithoutMandatoryValues() {
        // Create meta
        final MetaProtos.Meta.Builder meta = TestUtil.createMeta(null, null, null);

        final ValidationResult result = ValidateMeta.validate(meta);
        assertThat(result.isOK(), is(false));
        assertThat(ValidateMeta.isValid(meta), is(false));
        assertThat(ValidateMeta.isValid(meta.build()), is(false));

        final List<ValidationError> errors = result.getErrors();
        assertNotNull(errors, "Expect non-null list of errors");
        assertThat(errors.size(), is(3));

        assertThat(errors.stream().anyMatch(ve -> "meta.uuid".equals(ve.getMessage())), is(true));
        assertThat(errors.stream().anyMatch(ve -> "meta.source".equals(ve.getMessage())), is(true));
        assertThat(errors.stream().anyMatch(ve -> "meta.timeInfo".equals(ve.getMessage())), is(true));
    }

    @Test
    public void testValidateWithNullOptionalValues() {
        final UUID uuid = UUID.randomUUID();
        final Instant acquired = Instant.now();
        final EnumProtos.Source source = EnumProtos.Source.TWITTER;

        final MetaProtos.TimeInfo.Builder timeInfo = TestUtil.createTimeInfo(acquired, null);
        final MetaProtos.Meta.Builder meta = TestUtil.createMeta(uuid, source, timeInfo);

        final ValidationResult result = ValidateMeta.validate(meta);
        assertThat(result.isOK(), is(true));
        assertThat(ValidateMeta.isValid(meta), is(true));
        assertThat(ValidateMeta.isValid(meta.build()), is(true));
    }
}
