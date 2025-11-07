package org.quasar.eatclub.data;

import java.time.LocalTime;
import java.util.Optional;

import org.quasar.eatclub.data.json.JsonCreator;

public class DealBuilder {
  public static final String DEFAULT_OBJECT_ID = "DEA567C5-0000-3C03-FF00-E3B24909BE00";
  public static final String DEFAULT_DISCOUNT = "50";
  public static final Boolean DEFAULT_DINE_IN = true;
  public static final Boolean DEFAULT_LIGHTNING = false;
  public static final LocalTime DEFAULT_START = LocalTime.of(15, 0); // 3:00pm
  public static final LocalTime DEFAULT_END = LocalTime.of(20, 0); // 8:00pm
  public static final Long DEFAULT_QUANTITY_LEFT = 5L;

  private String objectId = DEFAULT_OBJECT_ID;
  private String discount = DEFAULT_DISCOUNT;
  private Boolean dineIn = DEFAULT_DINE_IN;
  private Boolean lightning = DEFAULT_LIGHTNING;
  private Optional<LocalTime> start = Optional.of(DEFAULT_START);
  private Optional<LocalTime> end = Optional.of(DEFAULT_END);
  private Optional<LocalTime> open = Optional.empty();
  private Optional<LocalTime> close = Optional.empty();
  private Long qtyLeft = DEFAULT_QUANTITY_LEFT;

  public DealBuilder withObjectId(String objectId) {
    this.objectId = objectId;

    return this;
  }

  public DealBuilder withDiscount(String discount) {
    this.discount = discount;

    return this;
  }

  public DealBuilder withDineIn(Boolean dineIn) {
    this.dineIn = dineIn;

    return this;
  }

  public DealBuilder withLightening(Boolean lightening) {
    this.lightning = lightening;

    return this;
  }

  public DealBuilder withStart(LocalTime start) {
    this.start = Optional.of(start);

    return this;
  }

  public DealBuilder withNoStart() {
    this.start = Optional.empty();

    return this;
  }

  public DealBuilder withEnd(LocalTime end) {
    this.end = Optional.of(end);

    return this;
  }

  public DealBuilder withNoEnd() {
    this.end = Optional.empty();

    return this;
  }

  public DealBuilder withOpen(LocalTime open) {
    this.open = Optional.of(open);

    return this;
  }

  public DealBuilder withClose(LocalTime close) {
    this.close = Optional.of(close);

    return this;
  }

  public DealBuilder withQtyLeft(Long qtyLeft) {
    this.qtyLeft = qtyLeft;

    return this;
  }

  public Deal build() {
    var start = this.start.orElse(DEFAULT_START);
    var end = this.end.orElse(DEFAULT_END);

    return new Deal(objectId, discount, dineIn, lightning, start, end, qtyLeft);
  }

  public String buildAsJson() {
    var jsonCreator = new JsonCreator();
    jsonCreator.appendPair("objectId", objectId);
    jsonCreator.appendPair("discount", discount);
    jsonCreator.appendPair("dineIn", dineIn);
    jsonCreator.appendPair("lightning", lightning);
    start.ifPresent(jsonCreator.appendTimePair("start"));
    end.ifPresent(jsonCreator.appendTimePair("end"));
    open.ifPresent(jsonCreator.appendTimePair("open"));
    close.ifPresent(jsonCreator.appendTimePair("close"));
    jsonCreator.appendPair("qtyLeft", qtyLeft, true);

    return """
      {
        %s
      }
      """.formatted(jsonCreator.build());
  }
}
