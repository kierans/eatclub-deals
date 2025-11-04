package org.quasar.cdk;

import org.jetbrains.annotations.NotNull;
import software.amazon.awscdk.CfnOutput;
import software.amazon.awscdk.services.apigateway.LambdaRestApi;
import software.amazon.awscdk.services.lambda.IFunction;
import software.constructs.Construct;

public class APIGateway extends Construct {
  @SuppressWarnings("FieldCanBeLocal")
  private final LambdaRestApi apiGateway;

  public APIGateway(
    @NotNull final Construct scope,
    @NotNull final IFunction function
  ) {
    super(scope, "APIGateway");

    apiGateway = LambdaRestApi.Builder
      .create(this, "RestApiGateway")
      .handler(function)
      .build();

    CfnOutput.Builder.create(this, "RestApiGatewayUrlOutput").value(apiGateway.getUrl()).build();
  }
}
