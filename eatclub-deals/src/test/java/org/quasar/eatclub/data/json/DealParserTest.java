package org.quasar.eatclub.data.json;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.quasar.eatclub.data.DealBuilder;

public class DealParserTest {
  @Test
  public void itShouldParseDealData() {
    var builder = new DealBuilder();
    var deal = DealParser.parseDeal(builder.buildAsJson());

    assertThat(deal.objectId(), is(DealBuilder.DEFAULT_OBJECT_ID));
    assertThat(deal.discount(), is(DealBuilder.DEFAULT_DISCOUNT));
    assertThat(deal.dineIn(), is(DealBuilder.DEFAULT_DINE_IN));
    assertThat(deal.lightning(), is(DealBuilder.DEFAULT_LIGHTNING));
    assertThat(deal.start().get(), is(equalTo(DealBuilder.DEFAULT_START)));
    assertThat(deal.end().get(), is(DealBuilder.DEFAULT_END));
    assertThat(deal.objectId(), is(DealBuilder.DEFAULT_OBJECT_ID));
    assertThat(deal.qtyLeft(), is(equalTo(DealBuilder.DEFAULT_QUANTITY_LEFT)));
  }

  @Test
  public void itShouldIgnoreEmptyStartAndEnd() {
    var builder = new DealBuilder().withNoStart().withNoEnd();
    var deal = DealParser.parseDeal(builder.buildAsJson());

    assertThat(deal.start().isEmpty(), is(true));
    assertThat(deal.end().isEmpty(), is(true));
  }

  @Test
  public void isShouldParseStartAndEndFromOpenAndClose() {
    var builder = new DealBuilder()
      .withNoStart()
      .withNoEnd()
      .withOpen(DealBuilder.DEFAULT_START)
      .withClose(DealBuilder.DEFAULT_END);

    var deal = DealParser.parseDeal(builder.buildAsJson());

    assertThat(deal.start().get(), is(equalTo(DealBuilder.DEFAULT_START)));
    assertThat(deal.end().get(), is(DealBuilder.DEFAULT_END));
  }

  @Test
  public void itShouldThrowErrorIfMandatoryFieldIsMissing() {
    assertThrows(IllegalStateException.class, () -> DealParser.parseDeal("{}"));
  }
}
