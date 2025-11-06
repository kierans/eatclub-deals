package org.quasar.eatclub.data.json;

import static org.quasar.eatclub.data.json.DealsParser.parseDeals;
import static org.quasar.eatclub.data.json.JsonParser.text;
import static org.quasar.eatclub.data.json.JsonParser.timeText;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import org.quasar.eatclub.data.Restaurant;

public class RestaurantParser {
  public static Restaurant parseRestaurant(final String json) {
    return JsonParser.parseFromString(json, RestaurantParser::parseRestaurant);
  }

  public static Restaurant parseRestaurant(final JsonNode tree) {
    return new Restaurant(
      text(tree, "objectId"),
      text(tree, "name"),
      text(tree, "address1"),
      text(tree, "suburb"),
      parseCuisines(tree),
      text(tree, "imageLink"),
      timeText(tree, "open"),
      timeText(tree, "close"),
      parseDeals(tree)
    );
  }

  private static List<String> parseCuisines(final JsonNode tree) {
    final JsonNode cuisinesNode = tree.path("cuisines");

    return cuisinesNode.isArray()
      ? cuisinesNode.valueStream().map(JsonNode::asText).toList()
      : List.of();
  }
}
