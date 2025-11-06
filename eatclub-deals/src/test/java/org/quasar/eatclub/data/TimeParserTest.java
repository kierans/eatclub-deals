package org.quasar.eatclub.data;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

public class TimeParserTest {
  @Test
  public void itShouldParseMidnight() {
    assertThat(TimeParser.parseTime("12:00AM"), is(equalTo(LocalTime.MIDNIGHT)));
  }

  @Test
  public void itShouldParseAMCaseInsensitively() {
    final LocalTime time = LocalTime.of(8, 0);

    assertThat(TimeParser.parseTime("8:00AM"), is(equalTo(time)));
    assertThat(TimeParser.parseTime("8:00am"), is(equalTo(time)));
  }

  @Test
  public void itShouldParseMidday() {
    assertThat(TimeParser.parseTime("12:00PM"), is(equalTo(LocalTime.NOON)));
  }

  @Test
  public void itShouldParsePMCaseInsensitively() {
    final LocalTime time = LocalTime.of(16, 0);

    assertThat(TimeParser.parseTime("4:00PM"), is(equalTo(time)));
    assertThat(TimeParser.parseTime("4:00pm"), is(equalTo(time)));
  }

  @Test
  public void itShouldFormatMidnight() {
    assertThat(TimeParser.format(LocalTime.MIDNIGHT), is(equalTo("12:00AM")));
  }

  @Test
  public void itShouldFormatAM() {
    assertThat(TimeParser.format(LocalTime.of(8, 0)), is(equalTo("8:00AM")));
  }

  @Test
  public void itShouldFormatMidday() {
    assertThat(TimeParser.format(LocalTime.NOON), is(equalTo("12:00PM")));
  }

  @Test
  public void itShouldFormatPM() {
    assertThat(TimeParser.format(LocalTime.of(16, 0)), is(equalTo("4:00PM")));
  }
}
