package org.quasar;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;
import org.quasar.data.JsonDealsRepository;

public class JsonDealsRepositoryTest {
  @Test
  public void itShouldLoadJsonFromClasspath() {
    assertThat(new JsonDealsRepository().jsonLoaded(), is(true));
  }
}
