package org.quasar.eatclub.data;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.quasar.eatclub.data.json.JsonCreator;

public class RestaurantBuilder {
  public static final String DEFAULT_OBJECT_ID = "DEA567C5-F64C-3C03-FF00-E3B24909BE00";
  public static final String DEFAULT_NAME = "Masala Kitchen";
  public static final String DEFAULT_ADDRESS1 = "55 Walsh Street";
  public static final String DEFAULT_SUBURB = "Lower East";
  public static final List<String> DEFAULT_CUISINES = List.of("Indian", "Brazilian", "Breakfast");
  public static final String DEFAULT_IMAGE_LINK = "http://a.link.com";
  public static final LocalTime DEFAULT_OPEN = LocalTime.of(15, 0); // "3:00pm"
  public static final LocalTime DEFAULT_CLOSE = LocalTime.of(20, 0); // "9:00pm"
  public static final List<Deal> DEFAULT_DEALS = List.of();

  private String objectId = DEFAULT_OBJECT_ID;
  private String name = DEFAULT_NAME;
  private String address1 = DEFAULT_ADDRESS1;
  private String suburb = DEFAULT_SUBURB;
  private List<String> cuisines = DEFAULT_CUISINES;
  private String imageLink = DEFAULT_IMAGE_LINK;
  private LocalTime open = DEFAULT_OPEN;
  private LocalTime close = DEFAULT_CLOSE;
  private List<Deal> deals = DEFAULT_DEALS;

  public RestaurantBuilder withObjectId(String objectId) {
    this.objectId = objectId;

    return this;
  }

  public RestaurantBuilder withName(String name) {
    this.name = name;

    return this;
  }

  public RestaurantBuilder withAddress1(String address1) {
    this.address1 = address1;

    return this;
  }

  public RestaurantBuilder withSuburb(String suburb) {
    this.suburb = suburb;

    return this;
  }

  public RestaurantBuilder withCuisines(List<String> cuisines) {
    this.cuisines = cuisines;

    return this;
  }

  public RestaurantBuilder withImageLink(String imageLink) {
    this.imageLink = imageLink;

    return this;
  }

  public RestaurantBuilder withOpen(LocalTime open) {
    this.open = open;

    return this;
  }

  public RestaurantBuilder withClose(LocalTime close) {
    this.close = close;

    return this;
  }

  public RestaurantBuilder withDeals(List<Deal> deals) {
    this.deals = deals;

    return this;
  }

  public Restaurant build() {
    return new Restaurant(
      objectId,
      name,
      address1,
      suburb,
      cuisines,
      imageLink,
      open,
      close,
      deals
    );
  }

  public String buildAsJson() {
    return """
        {
          "objectId": "%s",
          "name": "%s",
          "address1": "%s",
          "suburb": "%s",
          "cuisines": %s,
          "imageLink": "%s",
          "open": "%s",
          "close": "%s",
          "deals": %s
        }
      """
      .formatted(
        objectId,
        name,
        address1,
        suburb,
        stringsToArray(cuisines),
        imageLink,
        TimeParser.format(open),
        TimeParser.format(close),
        dealsToArray(deals)
      );
  }

  private String stringsToArray(List<String> lst) {
    return "[ %s ]".formatted(
      lst.stream().map("\"%s\""::formatted).collect(Collectors.joining(","))
    );
  }

  private String dealsToArray(List<Deal> lst) {
    return "[ %s ]".formatted(
      lst.stream().map(this::dealToJson).collect(Collectors.joining(","))
    );
  }

  private String dealToJson(Deal deal) {
    var jsonCreator = new JsonCreator();
    jsonCreator.appendPair("objectId", deal.objectId());
    jsonCreator.appendPair("discount", deal.discount());
    jsonCreator.appendPair("dineIn", deal.dineIn());
    jsonCreator.appendPair("lightning", deal.lightning());
    deal.start().ifPresent(jsonCreator.appendTimePair("start"));
    deal.end().ifPresent(jsonCreator.appendTimePair("end"));
    jsonCreator.appendPair("qtyLeft", deal.qtyLeft(), true);

    return """
      {
        %s
      }
      """.formatted(jsonCreator.build());
  }
}
