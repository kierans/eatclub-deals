package org.quasar.data;

import java.time.LocalTime;
import java.util.List;

public interface DealsRepository {
  List<Restaurant> findRestaurantsWithDetailsActiveAt(final LocalTime time);
}
