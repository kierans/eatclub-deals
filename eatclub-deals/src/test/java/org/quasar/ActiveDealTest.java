package org.quasar;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;
import org.quasar.data.DealBuilder;
import org.quasar.data.RestaurantBuilder;
import org.quasar.data.TimeParser;

public class ActiveDealTest {
  @Test
  public void itShouldCombineRestaurantAndDealData() {
    var activeDeal = ActiveDeal.toActiveDeal(
      new RestaurantBuilder().build(),
      new DealBuilder().build()
    );

    assertThat(activeDeal.restaurantObjectId(), is(RestaurantBuilder.DEFAULT_OBJECT_ID));
    assertThat(activeDeal.restaurantName(), is(RestaurantBuilder.DEFAULT_NAME));
    assertThat(activeDeal.restaurantAddress1(), is(RestaurantBuilder.DEFAULT_ADDRESS1));
    assertThat(activeDeal.restaurantSuburb(), is(RestaurantBuilder.DEFAULT_SUBURB));
    assertThat(activeDeal.restaurantOpen(), is(TimeParser.format(RestaurantBuilder.DEFAULT_OPEN)));
    assertThat(activeDeal.restaurantClose(), is(TimeParser.format(RestaurantBuilder.DEFAULT_CLOSE)));
    assertThat(activeDeal.dealObjectId(), is(DealBuilder.DEFAULT_OBJECT_ID));
    assertThat(activeDeal.discount(), is(DealBuilder.DEFAULT_DISCOUNT));
    assertThat(activeDeal.dineIn(), is(DealBuilder.DEFAULT_DINE_IN.toString()));
    assertThat(activeDeal.lightning(), is(DealBuilder.DEFAULT_LIGHTNING.toString()));
    assertThat(activeDeal.qtyLeft(), is(DealBuilder.DEFAULT_QUANTITY_LEFT.toString()));
  }
}
