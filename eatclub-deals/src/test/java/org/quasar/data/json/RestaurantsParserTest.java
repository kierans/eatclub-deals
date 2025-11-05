package org.quasar.data.json;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.quasar.data.RestaurantsBuilder;

public class RestaurantsParserTest {
  @Test
  public void itShouldReturnEmptyListWhenNoRestaurantDataExists() {
    var restaurants = RestaurantsParser.parseRestaurants("{}");

    assertThat(restaurants, is(empty()));
  }

  @Test
  public void itShouldReturnEmptyListWhenRestaurantDataEmptyArray() {
    var builder = new RestaurantsBuilder().withRestaurants(List.of());
    var restaurants = RestaurantsParser.parseRestaurants(builder.buildAsJson());

    assertThat(restaurants, is(empty()));
  }

  @Test
  public void itShouldReturnRestaurantData() {
    var builder = new RestaurantsBuilder();
    var restaurants = RestaurantsParser.parseRestaurants(builder.buildAsJson());

    assertThat(restaurants, hasSize(1));
  }
}

