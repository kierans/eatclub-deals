package org.quasar.eatclub.data;

import java.time.LocalTime;

public record PeakDealsWindow(
  LocalTime rangeStart,
  LocalTime rangeEnd,
  int maxActiveDeals
) {

}
