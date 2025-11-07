package org.quasar.eatclub.data.json;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import org.quasar.eatclub.data.Deal;
import org.quasar.eatclub.data.json.RestaurantParser.RestaurantParserState;

public class DealsParser {
  public static List<Deal> parseDeals(
    final RestaurantParserState restaurantParserState,
    final String json
  ) {
    return JsonParser.parseFromString(json, (node) -> parseDeals(restaurantParserState, node));
  }

  public static List<Deal> parseDeals(
    final RestaurantParserState restaurantParserState,
    final JsonNode tree
  ) {
    final JsonNode dealsNode = tree.path("deals");

    return dealsNode.isArray()
      ? dealsNode.valueStream().map((node) -> DealParser.parseDeal(restaurantParserState, node)).toList()
      : List.of();
  }
}
