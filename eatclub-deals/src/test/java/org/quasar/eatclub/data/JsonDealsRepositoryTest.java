package org.quasar.eatclub.data;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

public class JsonDealsRepositoryTest {
  @Test
  public void itShouldLoadJsonFromClasspath() {
    assertThat(new JsonDealsRepository().jsonLoaded(), is(true));
  }
}
