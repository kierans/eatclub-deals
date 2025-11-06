package org.quasar.eatclub.data.json;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import org.quasar.eatclub.data.Deal;

public class DealsParser {
  public static List<Deal> parseDeals(final String json) {
    return JsonParser.parseFromString(json, DealsParser::parseDeals);
  }

  public static List<Deal> parseDeals(final JsonNode tree) {
    final JsonNode dealsNode = tree.path("deals");

    return dealsNode.isArray()
      ? dealsNode.valueStream().map(DealParser::parseDeal).toList()
      : List.of();
  }
}
