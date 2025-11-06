package org.quasar.cdk;

import java.nio.file.Files;
import java.nio.file.Path;

import org.jetbrains.annotations.NotNull;
import software.amazon.awscdk.Duration;
import software.amazon.awscdk.services.lambda.Architecture;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.IFunction;
import software.amazon.awscdk.services.lambda.Runtime;
import software.constructs.Construct;

public class QuarkusLambda extends Construct {
  private static final int ONE_CPU = 1700;

  private final IFunction function;

  public QuarkusLambda(
    @NotNull final Construct scope,
    @NotNull final String functionZip,
    @NotNull final String functionName
  ) {
    super(scope, "%sLambda".formatted(functionName));

    // We can't guarantee that the function.zip file exists; so check
    verifyFunctionZip(functionZip);

    this.function = Function.Builder.create(scope, functionName)
      .runtime(Runtime.JAVA_21)
      .architecture(Architecture.ARM_64)
      .code(Code.fromAsset(functionZip))
      .handler("io.quarkus.amazon.lambda.runtime.QuarkusStreamHandler::handleRequest")
      .memorySize(ONE_CPU)
      .functionName(functionName)
      .timeout(Duration.seconds(10))
      .build();
  }

  public IFunction getFunction() {
    return function;
  }

  /*
   * This checks that the lambda ZIP exists and has the correct name.
   */
  private void verifyFunctionZip(final @NotNull String functionZip) {
    if (!functionZip.endsWith("function.zip")) {
      throw new IllegalArgumentException("File must end with function.zip, but was: " + functionZip);
    }

    if (!Files.exists(Path.of(functionZip))) {
      throw new IllegalArgumentException("function.zip not found at: " + functionZip);
    }
  }
}
