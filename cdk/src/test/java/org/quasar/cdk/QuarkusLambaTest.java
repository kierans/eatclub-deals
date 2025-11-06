package org.quasar.cdk;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import software.amazon.awscdk.App;

public class QuarkusLambaTest {
  @Test()
  public void itShouldThrowErrorWhenFunctionZipNameNotCorrect() {
    final String functionZip = "incorrect-zip-name";
    final Exception exception = causeException(functionZip);

    assertThat(
      exception.getMessage(),
      containsString("File must end with function.zip, but was: %s".formatted(functionZip))
    );
  }

  @Test()
  public void itShouldThrowErrorWhenFunctionZipDoesNotExist() {
    final String functionZip = "/tmp/function.zip";
    final Exception exception = causeException(functionZip);

    assertThat(
      exception.getMessage(),
      containsString("function.zip not found at: %s".formatted(functionZip))
    );
  }

  private static Exception causeException(final String functionZip) {
    return assertThrows(IllegalArgumentException.class, () -> new QuarkusLambda(new App(), functionZip, "test"));
  }
}
