package org.quasar.cdk;

import software.constructs.Construct;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;

public class EatClubDealsStack extends Stack {
  public EatClubDealsStack(final Construct scope, final String id, final StackProps props) {
    super(scope, id, props);

    var lambda = new QuarkusLambda(
      this,
      "../eatclub-deals/target/function.zip",
      "EatClubDeals"
    );

    @SuppressWarnings("unused")
    var gateway = new APIGateway(this, lambda.getFunction());
  }
}
