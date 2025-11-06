package org.quasar.eatclub.data;

import java.time.LocalTime;

public record TimeRange(
  LocalTime start,
  LocalTime end
) {
}
