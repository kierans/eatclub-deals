package org.quasar.eatclub.data;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/*
 * The "peak deals window" is the time window that has the most deals available.
 *
 * This class uses an event-sweeping algorithm approach to find the time window that has the most deals available.
 * It does this by creating a list of events that represent the start and end of each time range and sorting the list
 * by time.
 *
 * The algorithm then reduces the list, keeping track of the number of available deals at any given event. If the number
 * of available deals is greater than the current max, then it's the new max, and the algorithm records the start time
 * of the window and the number of available deals.
 *
 * When the current available deal count is lower than the max, that event represents the end of the window and the
 * time of the end event is recorded.
 *
 * The result is a PeakDealsWindow object that contains the start and end times of the peak deals window and the number
 * of available deals in that window.
 *
 * For example, given the following time ranges:
 *
 * TimeRange[start=15:00, end=21:00]
 * TimeRange[start=15:00, end=21:00]
 * TimeRange[start=12:00, end=23:00]
 * TimeRange[start=12:00, end=23:00]
 * TimeRange[start=18:00, end=21:00]
 * TimeRange[start=14:00, end=21:00]
 * TimeRange[start=17:00, end=21:00]
 * TimeRange[start=16:00, end=22:00]
 * TimeRange[start=16:00, end=22:00]
 * TimeRange[start=08:00, end=15:00]
 * TimeRange[start=08:00, end=15:00]
 *
 * we end up with the following timeline
 *
 * Time:  08:00   12:00   14:00   15:00   16:00   17:00   18:00   21:00   22:00   23:00
 *        |       |       |       |       |       |       |       |       |       |
 *
 * Deal 0                         |===============================|
 * Deal 1                         |===============================|
 * Deal 2         |===============================================================|
 * Deal 3         |===============================================================|
 * Deal 4                                                 |=======|
 * Deal 5                 |=======================================|
 * Deal 6                                         |===============|
 * Deal 7                                 |===============================|
 * Deal 8                                 |===============================|
 * Deal 9 |=======================|
 * Deal10 |=======================|
 *
 * Count: 2       4       5       5       7       8       9       4        2       0
 *
 * Sweeping through the timeline and counting the number of deals available at each event, we find that for this
 * dataset the peak deal window is 6pm-9pm with 9 deals available in that window.
 *
 * If there are no time ranges, then the result is an empty Optional.
 */
public class PeakDealsWindowFinder {
  public static Optional<PeakDealsWindow> findPeakDealWindow(final List<TimeRange> ranges) {
    if (ranges.isEmpty()) {
      return Optional.empty();
    }

    return Optional.of(sweepEventsForPeakDealWindow(toEvents(ranges)));
  }

  private static PeakDealsWindow sweepEventsForPeakDealWindow(final Stream<Event> events) {
    var result = events.reduce(
      new PeakDealWindowSweeperState(),
      (state, event) -> {
        if (event.type.isStartEvent()) {
          state.currentActive++;

          if (state.currentActive > state.maxActive) {
            state.maxActive = state.currentActive;
            state.peakStart = event.time;

            // clear the end time because we can't know the end yet.
            state.peakEnd = null;
          }
        }

        if (event.type.isEndEvent()) {
          if (state.currentActive == state.maxActive && state.peakStart != null) {
            state.peakEnd = event.time;
          }

          state.currentActive--;
        }

        return state;
      },
      (state, x) -> state
    );

    return new PeakDealsWindow(result.peakStart, result.peakEnd, result.maxActive);
  }

  private static Stream<Event> toEvents(final List<TimeRange> ranges) {
    return ranges.stream()
      .flatMap((range) ->
        Stream.of(
          new Event(range.start(), EventType.START_EVENT),
          new Event(range.end(), EventType.END_EVENT)
        ))
      .sorted();
  }

  private enum EventType {
    START_EVENT,
    END_EVENT;

    public boolean isStartEvent() {
      return this == START_EVENT;
    }

    public boolean isEndEvent() {
      return this == END_EVENT;
    }
  }

  private static class Event implements Comparable<Event> {
    LocalTime time;
    EventType type;

    public Event(LocalTime time, EventType type) {
      this.time = time;
      this.type = type;
    }

    @Override
    public int compareTo(Event other) {
      int timeCompare = this.time.compareTo(other.time);

      return timeCompare != 0
        ? timeCompare
        // If times are equal, process start events before end events for a consistent order.
        : this.type.compareTo(other.type);
    }
  }

  private static class PeakDealWindowSweeperState {
    int currentActive = 0;
    int maxActive = 0;
    LocalTime peakStart = null;
    LocalTime peakEnd = null;
  }
}
