package org.quasar.eatclub.data.json;

import java.time.LocalTime;
import java.util.function.Consumer;

import org.quasar.eatclub.data.TimeParser;

public class JsonCreator {
  private final StringBuilder sb = new StringBuilder();

  public <A> void appendPair(final String field, final A value) {
    appendPair(field, value, false);
  }

  public <A> void appendPair(final String field, final A value, final boolean last) {
    sb.append(asKey(field)).append(jsonString(value));

    if (!last) {
      sb.append(",");
    }
  }

  public Consumer<LocalTime> appendTimePair(final String field) {
    return (t) -> appendPair(field, TimeParser.format(t));
  }

  public String build() {
    return sb.toString();
  }

  private String asKey(final String field) {
    return "%s: ".formatted(jsonString(field));
  }

  private <A> String jsonString(final A value) {
    return "\"%s\"".formatted(value.toString());
  }
}
