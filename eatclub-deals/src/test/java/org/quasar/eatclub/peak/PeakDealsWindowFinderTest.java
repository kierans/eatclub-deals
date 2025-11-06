package org.quasar.eatclub.peak;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.quasar.eatclub.data.PeakDealsWindow;
import org.quasar.eatclub.data.PeakDealsWindowFinder;
import org.quasar.eatclub.data.TimeRange;

public class PeakDealsWindowFinderTest {
  @Test
  public void itShouldReturnNothingWhenEmptyListOfTimeGiven() {
    assertThat(PeakDealsWindowFinder.findPeakDealWindow(List.of()), is(equalTo(Optional.empty())));
  }

  @Test
  public void itShouldFindPeakDealsWindow() {
    final Optional<PeakDealsWindow> peakDealWindow = PeakDealsWindowFinder.findPeakDealWindow(givenTimeRanges());
    assertThat(peakDealWindow.isPresent(), is(true));

    var window = peakDealWindow.get();
    assertThat(window.rangeStart(), is(equalTo(LocalTime.of(18, 0))));
    assertThat(window.rangeEnd(), is(equalTo(LocalTime.of(21, 0))));
    assertThat(window.maxActiveDeals(), is(9));
  }

  /*
   * This data set exercises the algorithm by:
   * - Being unordered in time
   * - Having overlapping deals with different start and end times
   * - Deals that start and end at the same time (tests sorting)
   */
  private List<TimeRange> givenTimeRanges() {
    return List.of(
      new TimeRange(LocalTime.of(15, 0), LocalTime.of(21, 0)),
      new TimeRange(LocalTime.of(15, 0), LocalTime.of(21, 0)),
      new TimeRange(LocalTime.of(12, 0), LocalTime.of(23, 0)),
      new TimeRange(LocalTime.of(12, 0), LocalTime.of(23, 0)),
      new TimeRange(LocalTime.of(18, 0), LocalTime.of(21, 0)),
      new TimeRange(LocalTime.of(14, 0), LocalTime.of(21, 0)),
      new TimeRange(LocalTime.of(17, 0), LocalTime.of(21, 0)),
      new TimeRange(LocalTime.of(16, 0), LocalTime.of(22, 0)),
      new TimeRange(LocalTime.of(16, 0), LocalTime.of(22, 0)),
      new TimeRange(LocalTime.of(8, 0), LocalTime.of(15, 0)),
      new TimeRange(LocalTime.of(8, 0), LocalTime.of(15, 0))
    );
  }
}
