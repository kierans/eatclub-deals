package org.quasar;

import org.quasar.data.Deal;
import org.quasar.data.Restaurant;
import org.quasar.data.TimeParser;

public record ActiveDeal(
  String restaurantObjectId,
  String restaurantName,
  String restaurantAddress1,
  String restaurantSuburb,
  String restaurantOpen,
  String restaurantClose,
  String dealObjectId,
  String discount,
  String dineIn,
  String lightning,
  String qtyLeft
) {
  public static ActiveDeal toActiveDeal(final Restaurant restaurant, final Deal deal) {
    return new ActiveDeal(
      restaurant.objectId(),
      restaurant.name(),
      restaurant.address1(),
      restaurant.suburb(),
      TimeParser.format(restaurant.open()),
      TimeParser.format(restaurant.close()),
      deal.objectId(),
      deal.discount(),
      deal.dineIn().toString(),
      deal.lightning().toString(),
      deal.qtyLeft().toString()
    );
  }
}
