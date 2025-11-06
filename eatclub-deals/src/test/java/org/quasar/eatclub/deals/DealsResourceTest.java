package org.quasar.eatclub.deals;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.List;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class DealsResourceTest {
  @Test
  public void itShouldFindDealsActiveAt1500() {
    final var results = findActiveDealsAt("15:00");

    assertThat(results.size(), is(5));

    assertThat(results.get(0), hasActiveDeal("DEA567C5-F64C-3C03-FF00-E3B24909BE00", "DEA567C5-0000-3C03-FF00-E3B24909BE00"));
    assertThat(results.get(1), hasActiveDeal("DEA567C5-F64C-3C03-FF00-E3B24909BE00", "DEA567C5-1111-3C03-FF00-E3B24909BE00"));
    assertThat(results.get(2), hasActiveDeal("D80263E8-FD89-2C70-FF6B-D854ADB8DB00", "D80263E8-0000-2C70-FF6B-D854ADB8DB00"));
    assertThat(results.get(3), hasActiveDeal("D80263E8-FD89-2C70-FF6B-D854ADB8DB00", "D80263E8-1111-2C70-FF6B-D854ADB8DB00"));
    assertThat(results.get(4), hasActiveDeal("B5713CD0-91BF-40C7-AFC3-7D46D26B00BF", "B5713CD0-0000-40C7-AFC3-7D46D26B00BF"));
  }

  @Test
  public void itShouldFindDealsActiveAt1800() {
    final var results = findActiveDealsAt("18:00");

    assertThat(results.size(), is(9));

    assertThat(results.get(0), hasActiveDeal("DEA567C5-F64C-3C03-FF00-E3B24909BE00", "DEA567C5-0000-3C03-FF00-E3B24909BE00"));
    assertThat(results.get(1), hasActiveDeal("DEA567C5-F64C-3C03-FF00-E3B24909BE00", "DEA567C5-1111-3C03-FF00-E3B24909BE00"));
    assertThat(results.get(2), hasActiveDeal("D80263E8-FD89-2C70-FF6B-D854ADB8DB00", "D80263E8-0000-2C70-FF6B-D854ADB8DB00"));
    assertThat(results.get(3), hasActiveDeal("D80263E8-FD89-2C70-FF6B-D854ADB8DB00", "D80263E8-1111-2C70-FF6B-D854ADB8DB00"));
    assertThat(results.get(4), hasActiveDeal("CDB2B42A-248C-EE20-FF45-8D0A8057E200", "CDB2B42A-0000-EE20-FF45-8D0A8057E200"));
    assertThat(results.get(5), hasActiveDeal("B5713CD0-91BF-40C7-AFC3-7D46D26B00BF", "B5713CD0-0000-40C7-AFC3-7D46D26B00BF"));
    assertThat(results.get(6), hasActiveDeal("B5713CD0-91BF-40C7-AFC3-7D46D26B00BF", "B5713CD0-1111-40C7-AFC3-7D46D26B00BF"));
    assertThat(results.get(7), hasActiveDeal("21076F54-03E7-3115-FF09-75D07FFC7401", "B5913CD0-0000-40C7-AFC3-7D46D26B01BF"));
    assertThat(results.get(8), hasActiveDeal("21076F54-03E7-3115-FF09-75D07FFC7401", "B5713CD0-1111-40C7-AFC3-7D46D26B00BF"));
  }

  @Test
  public void itShouldFindDealsActiveAt2100() {
    final var results = findActiveDealsAt("21:00");

    assertThat(results.size(), is(4));

    assertThat(results.get(0), hasActiveDeal("D80263E8-FD89-2C70-FF6B-D854ADB8DB00", "D80263E8-0000-2C70-FF6B-D854ADB8DB00"));
    assertThat(results.get(1), hasActiveDeal("D80263E8-FD89-2C70-FF6B-D854ADB8DB00", "D80263E8-1111-2C70-FF6B-D854ADB8DB00"));
    assertThat(results.get(2), hasActiveDeal("21076F54-03E7-3115-FF09-75D07FFC7401", "B5913CD0-0000-40C7-AFC3-7D46D26B01BF"));
    assertThat(results.get(3), hasActiveDeal("21076F54-03E7-3115-FF09-75D07FFC7401", "B5713CD0-1111-40C7-AFC3-7D46D26B00BF"));
  }

  private List<ActiveDeal> findActiveDealsAt(String time) {
    return RestAssured
      .when()
      .get("/deals?timeOfDay=%s".formatted(time))
      .then()
      .contentType("application/json")
      .extract()
      .jsonPath().getList(".", ActiveDeal.class);
  }

  private static Matcher<ActiveDeal> hasActiveDeal(
    final String restaurantObjectId,
    final String dealObjectId
  ) {
    return new TypeSafeMatcher<>() {
      @Override
      public void describeTo(final Description description) {
        description
          .appendText("has deal with restaurant id ")
          .appendValue(restaurantObjectId)
          .appendText(" and deal id ")
          .appendValue(dealObjectId);
      }

      @Override
      protected boolean matchesSafely(final ActiveDeal activeDeal) {
        return activeDeal.restaurantObjectId().equals(restaurantObjectId) && activeDeal.dealObjectId().equals(dealObjectId);
      }
    };
  }
}
