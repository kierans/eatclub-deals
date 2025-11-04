#! /bin/bash

set -e
echo "building ..."
cd eatclub-deals && mvn clean package

echo "deploying ..."
cd ../cdk && mvn clean package && cdk deploy
