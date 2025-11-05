package org.quasar.data.json;

import static org.quasar.data.json.JsonParser.booleanText;
import static org.quasar.data.json.JsonParser.longText;
import static org.quasar.data.json.JsonParser.maybeText;
import static org.quasar.data.json.JsonParser.text;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;
import org.quasar.data.Deal;
import org.quasar.data.TimeParser;

public class DealParser {
  public static Deal parseDeal(final String json) {
    return JsonParser.parseFromString(json, DealParser::parseDeal);
  }

  public static Deal parseDeal(final JsonNode tree) {
    final String objectId = text(tree, "objectId");
    final String discount = text(tree, "discount");
    final Boolean dineIn = booleanText(tree, "dineIn");
    final Boolean lightning = booleanText(tree, "lightning");

    // the start might be keyed by 'start' or 'open'
    final Optional<LocalTime> start = parseTime(findFirst(tree, List.of("start", "open")));

    // the end might be keyed by 'end' or 'close'
    final Optional<LocalTime> end = parseTime(findFirst(tree, List.of("end", "close")));

    final Long qtyLeft = longText(tree, "qtyLeft");

    return new Deal(objectId, discount, dineIn, lightning, start, end, qtyLeft);
  }

  private static Optional<LocalTime> parseTime(final Optional<String> timeText) {
    return timeText.map(TimeParser::parseTime);
  }

  /*
   * Looks through the tree for the first non-empty value of the given fields.
   */
  private static Optional<String> findFirst(final JsonNode tree, final List<String> fields) {
    return fields.stream()
      .map((f) -> maybeText(tree, f))
      // returns the first non-empty value found.
      .reduce(Optional.empty(), (a, b) -> a.or(() -> b));
  }
}
