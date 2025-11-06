package org.quasar.eatclub.data.json;

import static org.quasar.eatclub.data.json.JsonParser.text;
import static org.quasar.eatclub.data.json.JsonParser.timeText;

import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import org.quasar.eatclub.data.Deal;
import org.quasar.eatclub.data.Restaurant;

public class RestaurantParser {
  public static Restaurant parseRestaurant(final String json) {
    return JsonParser.parseFromString(json, RestaurantParser::parseRestaurant);
  }

  public static Restaurant parseRestaurant(final JsonNode tree) {
    final String objectId = text(tree, "objectId");
    final String name = text(tree, "name");
    final String address1 = text(tree, "address1");
    final String suburb = text(tree, "suburb");
    final List<String> cuisines = parseCuisines(tree);
    final String imageLink = text(tree, "imageLink");
    final LocalTime open = timeText(tree, "open");
    final LocalTime close = timeText(tree, "close");
    final List<Deal> deals = DealsParser.parseDeals(tree);

    return new Restaurant(objectId, name, address1, suburb, cuisines, imageLink, open, close, deals);
  }

  private static List<String> parseCuisines(final JsonNode tree) {
    final JsonNode cuisinesNode = tree.path("cuisines");

    return cuisinesNode.isArray()
      ? cuisinesNode.valueStream().map(JsonNode::asText).toList()
      : List.of();
  }
}
