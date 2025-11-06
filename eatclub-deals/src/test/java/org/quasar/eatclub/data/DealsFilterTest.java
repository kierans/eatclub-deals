package org.quasar.eatclub.data;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.Test;

public class DealsFilterTest {
  private final LocalTime beforeOpening = RestaurantBuilder.DEFAULT_OPEN.minusHours(2);
  private final LocalTime afterClosing = RestaurantBuilder.DEFAULT_CLOSE.plusHours(2);
  private final LocalTime activeTime = RestaurantBuilder.DEFAULT_OPEN.plusHours(1);
  private final List<Restaurant> restaurants =
    new RestaurantsBuilder()
      .withRestaurants(List.of(new RestaurantBuilder().withDeals(new DealsBuilder().build())))
      .build();

  @Test
  public void itShouldKeepDealWhenActiveAtTime() {
    var active = DealsFilter.filterRestaurantsByDetailsActiveAt(restaurants, activeTime);

    assertThat(active, hasSize(1));
  }

  @Test
  public void itShouldRemoveDealWhenTimeIsBeforeStart() {
    var active = DealsFilter.filterRestaurantsByDetailsActiveAt(restaurants, beforeOpening);

    assertThat(active, is(empty()));
  }

  @Test
  public void itShouldRemoveDealWhenTimeIsAfterEnd() {
    var active = DealsFilter.filterRestaurantsByDetailsActiveAt(restaurants, afterClosing);

    assertThat(active, is(empty()));
  }

  @Test
  public void isShouldKeepDealWhenStartTimeIsAtQueryTime() {
    var active = DealsFilter.filterRestaurantsByDetailsActiveAt(restaurants, RestaurantBuilder.DEFAULT_OPEN);

    assertThat(active, hasSize(1));
  }
}
