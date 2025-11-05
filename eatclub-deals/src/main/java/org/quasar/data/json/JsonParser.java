package org.quasar.data.json;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.Optional;
import java.util.function.Function;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.quasar.data.TimeParser;

public class JsonParser {
  public static <A> A parseFromString(final String json, final Function<JsonNode, A> parser)  {
    try (var stream = createStringStream(json)) {
      return parseFromStream(stream, parser);
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static <A> A parseFromStream(final InputStream stream, final Function<JsonNode, A> parser) {
    final ObjectMapper mapper = new ObjectMapper();

    try {
      final JsonNode root = mapper.readTree(stream);

      return parser.apply(root);
    }
    catch (IOException e) {
      throw new IllegalArgumentException("Can't parse JSON data", e);
    }
  }

  public static boolean booleanText(final JsonNode node, final String field) {
    return Boolean.parseBoolean(text(node, field));
  }

  public static long longText(final JsonNode node, final String field) {
    return Long.parseLong(text(node, field));
  }

  public static Optional<String> maybeText(final JsonNode node, final String field) {
    return Optional.ofNullable(node.get(field))
      .map(JsonNode::asText)
      .map(String::trim);
  }

  public static String text(final JsonNode node, final String field) {
    return maybeText(node, field)
      .orElseThrow(() -> new IllegalStateException("Json data is missing required text field"));
  }

  public static LocalTime timeText(final JsonNode node, final String field) {
    return TimeParser.parseTime(text(node, field));
  }

  private static InputStream createStringStream(final String text) {
    return new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8));
  }
}
