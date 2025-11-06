package org.quasar.eatclub;

import java.time.LocalTime;
import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.quasar.eatclub.data.DealsRepository;

@Path("/deals")
public class DealsResource {
  @Inject
  DealsRepository dealsRepository;

  /*
   * Given the example in the requirements has `timeOfDay` with no am/pm suffix, it is assumed that
   * it is given in 24 hour time.
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<ActiveDeal> getActiveDeals(@QueryParam("timeOfDay") final String timeOfDay) {
    if (timeOfDay == null) {
      throw new IllegalArgumentException("timeOfDay parameter is required");
    }

    var restaurants = this.dealsRepository.findRestaurantsWithDetailsActiveAt(LocalTime.parse(timeOfDay));

    return restaurants.stream()
      .flatMap(r -> r.deals().stream().map(d -> ActiveDeal.toActiveDeal(r, d)))
      .toList();
  }
}
