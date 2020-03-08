package io.hh.lumberjack.shared.validate;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ValidationErrorTest {

    @Test
    public void testConstruct() {
        final ValidationError error = new ValidationError("meta", "message");

        assertThat(error.getRuleName(), is("meta"));
        assertThat(error.getMessage(), is("message"));
    }

    @Test
    public void testEquality() {
        final ValidationError e1 = new ValidationError("meta", "message");
        final ValidationError e2 = new ValidationError("meta", "message");
        final ValidationError e3 = new ValidationError("meta", null);

        assertThat(e1.equals(null), is(false));
        assertThat(e1.equals(e1), is(true));
        assertThat(e1.equals(e2), is(true));
        assertThat(e2.equals(e1), is(true));
        assertThat(e1.equals(e3), is(false));
        assertThat(e3.equals(e1), is(false));
    }
}
