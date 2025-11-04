# Eatclub Deals CDK!

This is a simple CDK app that deploys a simple Eatclub Deals stack.

The `cdk.json` file tells the CDK Toolkit how to execute your app.

It is a [Maven](https://maven.apache.org/) based project, so you can open this project with any Maven compatible Java IDE to build and run tests.

## Useful commands

 * `mvn package`     compile and run tests
 * `cdk ls`          list all stacks in the app
 * `cdk synth`       emits the synthesized CloudFormation template
 * `cdk deploy`      deploy this stack to your default AWS account/region
 * `cdk diff`        compare deployed stack with current state
 * `cdk docs`        open CDK documentation

Enjoy!

# Wait, where is all the tests

For a complex deployment, you should test your CDK code. For example, you might want to enable delete protection on 
your S3 bucket or RDS instance in production, but not in your test environment. You need to test this by having 
different env configuration and checking the Cloud Formation is generated correctly.

However, for this simple example, it's not necessary.
