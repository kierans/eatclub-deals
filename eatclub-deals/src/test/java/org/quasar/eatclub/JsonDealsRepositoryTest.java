package org.quasar.eatclub;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;
import org.quasar.eatclub.data.JsonDealsRepository;

public class JsonDealsRepositoryTest {
  @Test
  public void itShouldLoadJsonFromClasspath() {
    assertThat(new JsonDealsRepository().jsonLoaded(), is(true));
  }
}
