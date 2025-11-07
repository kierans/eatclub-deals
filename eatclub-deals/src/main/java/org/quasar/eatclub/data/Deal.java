package org.quasar.eatclub.data;

import java.time.LocalTime;

/*
 * It is assumed that if a deal does not have a start/end that it's TTL is
 * the opening hours of the restaurant.
 */
public record Deal(
  String objectId,
  String discount,
  Boolean dineIn,
  Boolean lightning,
  LocalTime start,
  LocalTime end,
  Long qtyLeft
) {

}
