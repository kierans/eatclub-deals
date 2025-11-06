package org.quasar.eatclub.data;

import java.util.List;
import java.util.stream.Collectors;

public class RestaurantsBuilder {
  private List<RestaurantBuilder> restaurants = List.of(new RestaurantBuilder());

  public RestaurantsBuilder addRestaurant(RestaurantBuilder builder) {
    this.restaurants.add(builder);

    return this;
  }

  public RestaurantsBuilder withRestaurants(List<RestaurantBuilder> builders) {
    this.restaurants = builders;

    return this;
  }

  public String buildAsJson() {
    return """
      {
        "restaurants": %s
      }
      """
      .formatted(toArray(restaurants));
  }

  public List<Restaurant> build() {
    return restaurants.stream().map(RestaurantBuilder::build).toList();
  }

  private String toArray(final List<RestaurantBuilder> lst) {
    return "[ %s ]".formatted(
      lst.stream().map(RestaurantBuilder::buildAsJson).collect(Collectors.joining(","))
    );
  }
}
