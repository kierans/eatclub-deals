package org.quasar.data;

import java.util.List;
import java.util.stream.Collectors;

public class DealsBuilder {
  private List<DealBuilder> deals = List.of(new DealBuilder());

  public DealsBuilder addDeal(DealBuilder builder) {
    this.deals.add(builder);

    return this;
  }

  public DealsBuilder withDeals(List<DealBuilder> builders) {
    this.deals = builders;

    return this;
  }

  public String buildAsJson() {
    return """
      {
        "deals": %s
      }
      """
      .formatted(toArray(deals));
  }

  public List<Deal> build() {
    return deals.stream().map(DealBuilder::build).toList();
  }

  private String toArray(final List<DealBuilder> lst) {
    return "[ %s ]".formatted(
      lst.stream().map(DealBuilder::buildAsJson).collect(Collectors.joining(","))
    );
  }
}
