package org.quasar.eatclub.data;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DealsFilter {
  public static List<Restaurant> filterRestaurantsByDetailsActiveAt(
    final List<Restaurant> restaurants,
    final LocalTime time
  ) {
    return restaurants.stream()
      .map(r -> {
        final List<Deal> activeDeals = r.deals()
          .stream()
          .filter(isActiveAt(time, r))
          .collect(Collectors.toList());

        if (activeDeals.isEmpty()) {
          return Optional.<Restaurant>empty();
        }

        return Optional.of(new Restaurant(
          r.objectId(),
          r.name(),
          r.address1(),
          r.suburb(),
          r.cuisines(),
          r.imageLink(),
          r.open(),
          r.close(),
          activeDeals
        ));
      })
      .filter(Optional::isPresent)
      .map(Optional::get)
      .collect(Collectors.toList());
  }

  private static Predicate<Deal> isActiveAt(final LocalTime time, final Restaurant restaurant) {
    return (deal) -> {
      final LocalTime start = deal.getDealStart(restaurant.open());
      final LocalTime end = deal.getDealEnd(restaurant.close());

      // cater for the time when the patron walks in the door right when then the deal becomes active
      // but once the deal ends, it's all over.
      return (time.equals(start) || time.isAfter(start)) && time.isBefore(end);
    };
  }
}
