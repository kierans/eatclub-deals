package org.quasar.data.json;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.quasar.data.RestaurantBuilder;

public class RestaurantParserTest {
  @Test
  public void itShouldParseRestaurantData() {
    var builder = new RestaurantBuilder();
    var restaurant = RestaurantParser.parseRestaurant(builder.buildAsJson());

    assertThat(restaurant.objectId(), is(RestaurantBuilder.DEFAULT_OBJECT_ID));
    assertThat(restaurant.name(), is(RestaurantBuilder.DEFAULT_NAME));
    assertThat(restaurant.address1(), is(RestaurantBuilder.DEFAULT_ADDRESS1));
    assertThat(restaurant.suburb(), is(RestaurantBuilder.DEFAULT_SUBURB));
    assertThat(restaurant.cuisines(), is(equalTo(RestaurantBuilder.DEFAULT_CUISINES)));
    assertThat(restaurant.imageLink(), is(RestaurantBuilder.DEFAULT_IMAGE_LINK));
    assertThat(restaurant.objectId(), is(RestaurantBuilder.DEFAULT_OBJECT_ID));
    assertThat(restaurant.open(), is(equalTo(RestaurantBuilder.DEFAULT_OPEN)));
    assertThat(restaurant.close(), is(equalTo(RestaurantBuilder.DEFAULT_CLOSE)));
    assertThat(restaurant.deals(), is(RestaurantBuilder.DEFAULT_DEALS));
  }

  @Test
  public void itShouldThrowErrorIfMandatoryFieldIsMissing() {
    assertThrows(IllegalStateException.class, () -> RestaurantParser.parseRestaurant("{}"));
  }
}
