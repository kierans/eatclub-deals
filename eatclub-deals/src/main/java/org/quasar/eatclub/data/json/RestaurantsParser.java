package org.quasar.eatclub.data.json;

import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import org.quasar.eatclub.data.Restaurant;

public class RestaurantsParser {
  public static List<Restaurant> parseRestaurants(final String json) {
    return JsonParser.parseFromString(json, RestaurantsParser::parseRestaurants);
  }

  public static List<Restaurant> parseRestaurants(final InputStream stream) {
    return JsonParser.parseFromStream(stream, RestaurantsParser::parseRestaurants);
  }

  private static List<Restaurant> parseRestaurants(final JsonNode tree) {
    final JsonNode restaurantsNode = tree.path("restaurants");

    return restaurantsNode.isArray()
      ? restaurantsNode.valueStream().map(RestaurantParser::parseRestaurant).toList()
      : List.of();
  }
}
