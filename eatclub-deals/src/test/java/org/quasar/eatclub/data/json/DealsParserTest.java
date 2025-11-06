package org.quasar.eatclub.data.json;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.quasar.eatclub.data.DealsBuilder;

public class DealsParserTest {
  @Test
  public void itShouldReturnEmptyListWhenNoDealDataExists() {
    var deals = DealsParser.parseDeals("{}");

    assertThat(deals, is(empty()));
  }

  @Test
  public void itShouldReturnEmptyListWhenDealsDataEmptyArray() {
    var builder = new DealsBuilder().withDeals(List.of());
    var deals = DealsParser.parseDeals(builder.buildAsJson());

    assertThat(deals, is(empty()));
  }

  @Test
  public void itShouldReturnDealsData() {
    var builder = new DealsBuilder();
    var deals = DealsParser.parseDeals(builder.buildAsJson());

    assertThat(deals, hasSize(1));
  }
}
