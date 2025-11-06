package org.quasar.eatclub.peak;

import java.util.Optional;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.quasar.eatclub.data.DealsRepository;
import org.quasar.eatclub.data.PeakDealsWindow;
import org.quasar.eatclub.data.TimeParser;

@Path("/peak-deals-window")
public class PeakDealsWindowResource {
  @Inject
  DealsRepository dealsRepository;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public PeakDeals getPeakDealsWindow() {
    final Optional<PeakDealsWindow> peakTimeForDeals = dealsRepository.findPeakTimeForDeals();

    return peakTimeForDeals
      .map((window) ->
        new PeakDeals(
          TimeParser.format(window.rangeStart()),
          TimeParser.format(window.rangeEnd())
        )
      )
      .orElseThrow(() -> new RuntimeException("No deals found"));
  }
}

