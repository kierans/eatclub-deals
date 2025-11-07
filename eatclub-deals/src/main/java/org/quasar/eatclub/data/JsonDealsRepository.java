package org.quasar.eatclub.data;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import org.quasar.eatclub.data.json.RestaurantsParser;

// This is so wrong.
@ApplicationScoped
public class JsonDealsRepository implements DealsRepository {
  private final List<Restaurant> restaurants;

  public JsonDealsRepository() {
    this.restaurants = loadFromJson();
  }

  @Override
  public List<Restaurant> findRestaurantsWithDetailsActiveAt(final LocalTime time) {
    return DealsFilter.filterRestaurantsByDetailsActiveAt(restaurants, time);
  }

  @Override
  public Optional<PeakDealsWindow> findPeakTimeForDeals() {
    return PeakDealsWindowFinder.findPeakDealWindow(toTimeRanges());
  }

  public boolean jsonLoaded() {
    return restaurants != null;
  }

  private List<TimeRange> toTimeRanges() {
    return restaurants.stream()
      .flatMap(r ->
        r.deals().stream().map(d ->
          new TimeRange(d.start(), d.end())
        )
      )
      .toList();
  }

  private List<Restaurant> loadFromJson() {
    try (InputStream is = openJsonResource("/data.json")) {
      return RestaurantsParser.parseRestaurants(is);
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private InputStream openJsonResource(final String resource) {
    return Optional.ofNullable(getClass().getResourceAsStream(resource))
      .orElseThrow(() -> new IllegalStateException("Resource not found: " + resource));
  }
}
