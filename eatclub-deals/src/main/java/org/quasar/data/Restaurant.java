package org.quasar.data;

import java.time.LocalTime;
import java.util.List;

public record Restaurant(
  String objectId,
  String name,
  String address1,
  String suburb,
  List<String> cuisines,
  String imageLink,
  LocalTime open,
  LocalTime close,
  List<Deal> deals
) {
}
