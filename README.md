# eatclub-deals

Implementation of EatClub Developer Challenge

## Requirements

- Java 21
- Maven 3.9

## Build & Test

To build and run tests:

```shell
$ mvn clean package
```

## Running

To run the application locally, use Quarkus Dev mode. The application will be available at http://localhost:8080/

```shell
$ mvn quarkus:dev
```

### Deploying to AWS

The application can be deployed to AWS by using the `cdk` module. It will deploy using the AWS profile set on the CLI
via the [AWS env vars][1]

[1]: https://docs.aws.amazon.com/cli/latest/userguide/cli-configure-envvars.html

```shell
$ cd cdk && cdk deploy
```

The application must be built before deploying as the CDK module will use the ZIP file produced by the Maven build.

To assist, a `deploy.sh` script is provided.
