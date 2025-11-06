package org.quasar.eatclub.data;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface DealsRepository {
  List<Restaurant> findRestaurantsWithDetailsActiveAt(final LocalTime time);

  Optional<PeakDealsWindow> findPeakTimeForDeals();
}
