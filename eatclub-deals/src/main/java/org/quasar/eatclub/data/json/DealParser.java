package org.quasar.eatclub.data.json;

import static org.quasar.eatclub.data.json.JsonParser.booleanText;
import static org.quasar.eatclub.data.json.JsonParser.longText;
import static org.quasar.eatclub.data.json.JsonParser.maybeText;
import static org.quasar.eatclub.data.json.JsonParser.text;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import com.fasterxml.jackson.databind.JsonNode;
import org.quasar.eatclub.data.Deal;
import org.quasar.eatclub.data.TimeParser;

public class DealParser {
  public static Deal parseDeal(
    final RestaurantParser.RestaurantParserState restaurantParserState,
    final String json
  ) {
    return JsonParser.parseFromString(json, (node) -> parseDeal(restaurantParserState, node));
  }

  public static Deal parseDeal(
    final RestaurantParser.RestaurantParserState restaurantParserState,
    final JsonNode tree
  ) {
    return new Deal(
      text(tree, "objectId"),
      text(tree, "discount"),
      booleanText(tree, "dineIn"),
      booleanText(tree, "lightning"),

      getDealStart(
        restaurantParserState.open(),

        // the start might be keyed by 'start' or 'open'
        parseTime(findFirst(tree, List.of("start", "open")))
      ),

      getDealEnd(
        restaurantParserState.close(),

        // the end might be keyed by 'end' or 'close'
        parseTime(findFirst(tree, List.of("end", "close")))
      ),

      longText(tree, "qtyLeft")
    );
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

  /*
   * For a Deal to be considered available, its start time (if any) must be after the restaurant's open time.
   * You can't have a deal that starts before the restaurant opens.
   */
  private static LocalTime getDealStart(
    final LocalTime restaurantOpen,
    final Optional<LocalTime> start
  ) {
    return selectTime(start, isAfter(restaurantOpen), restaurantOpen);
  }

  /*
   * A Deal's end time is either its own end time (if any) or the restaurant's close time.
   * You can't have a deal that's available for a closed restaurant.
   */
  private static LocalTime getDealEnd(
    final LocalTime restaurantClose,
    final Optional<LocalTime> end
  ) {
    return selectTime(end, isBefore(restaurantClose), restaurantClose);
  }

  private static LocalTime selectTime(
    final Optional<LocalTime> time,
    final Predicate<LocalTime> predicate,
    final LocalTime def
  ) {
    return time.filter(predicate).orElse(def);
  }

  private static Predicate<LocalTime> isBefore(final LocalTime first) {
    return (second) -> second.isBefore(first);
  }

  private static Predicate<LocalTime> isAfter(final LocalTime first) {
    return (second) -> second.isAfter(first);
  }
}
