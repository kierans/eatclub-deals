package org.quasar.data;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.time.LocalTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;

public class DealTest {
  public static final LocalTime DEAL_OPEN_TIME = LocalTime.parse("15:00");
  public static final LocalTime DEAL_CLOSE_TIME = LocalTime.parse("18:00");

  @Test
  public void itShouldSelectDealStartWhenRestaurantIsOpenEarlier() {
    final var deal = givenDeal(Optional.of(DEAL_OPEN_TIME), Optional.of(DEAL_CLOSE_TIME));
    final var restaurantOpen = LocalTime.parse("12:00");
    final var selectedStart = deal.getDealStart(restaurantOpen);

    assertThat(selectedStart, is(equalTo(DEAL_OPEN_TIME)));
  }

  @Test
  public void itShouldSelectRestaurantOpenWhenRestaurantIsClosedWhenDealStarts() {
    final var deal = givenDeal(Optional.of(DEAL_OPEN_TIME), Optional.of(DEAL_CLOSE_TIME));
    final var restaurantOpen = LocalTime.parse("16:00");
    final var selectedStart = deal.getDealStart(restaurantOpen);

    assertThat(selectedStart, is(equalTo(restaurantOpen)));
  }

  @Test
  public void itShouldSelectRestaurantOpenWhenNoDealStartIsPresent() {
    final var deal = givenDeal(Optional.empty(), Optional.of(DEAL_CLOSE_TIME));
    final var restaurantOpen = LocalTime.parse("16:00");
    final var selectedStart = deal.getDealStart(restaurantOpen);

    assertThat(selectedStart, is(equalTo(restaurantOpen)));
  }

  @Test
  public void itShouldSelectDealEndWhenRestaurantClosesLater() {
    final var deal = givenDeal(Optional.of(DEAL_OPEN_TIME), Optional.of(DEAL_CLOSE_TIME));
    final var restaurantClose = LocalTime.parse("19:00");
    final var selectedEnd = deal.getDealEnd(restaurantClose);

    assertThat(selectedEnd, is(equalTo(DEAL_CLOSE_TIME)));
  }

  @Test
  public void itShouldSelectRestaurantCloseWhenRestaurantIsClosedWhenDealEnds() {
    final var deal = givenDeal(Optional.of(DEAL_OPEN_TIME), Optional.of(DEAL_CLOSE_TIME));
    final var restaurantClose = LocalTime.parse("16:00");
    final var selectedEnd = deal.getDealEnd(restaurantClose);

    assertThat(selectedEnd, is(equalTo(restaurantClose)));
  }

  @Test
  public void itShouldSelectRestaurantCloseWhenNoDealEndIsPresent() {
    final var deal = givenDeal(Optional.of(DEAL_OPEN_TIME), Optional.empty());
    final var restaurantClose = LocalTime.parse("16:00");
    final var selectedEnd = deal.getDealEnd(restaurantClose);

    assertThat(selectedEnd, is(equalTo(restaurantClose)));
  }

  private Deal givenDeal(Optional<LocalTime> dealStart, Optional<LocalTime> dealEnd) {
    return new Deal("1", "10", true, false, dealStart, dealEnd, 10L);
  }
}
