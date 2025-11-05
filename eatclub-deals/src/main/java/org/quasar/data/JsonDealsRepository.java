package org.quasar.data;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import org.quasar.DealsFilter;
import org.quasar.data.json.RestaurantsParser;

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

  public boolean jsonLoaded() {
    return restaurants != null;
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
