package org.quasar.eatclub.data.json;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class JsonParserTest {
  @Test
  public void isShouldParseJsonIntoATree() {
    // just test we don't get an exception
    JsonParser.parseFromString("{}", (stream) -> null);
  }

  @Test
  public void isShouldThrowErrorOnInvalidJson() {
    assertThrows(
      IllegalArgumentException.class,
      () -> JsonParser.parseFromString("abc", (stream) -> null)
    );
  }
}
