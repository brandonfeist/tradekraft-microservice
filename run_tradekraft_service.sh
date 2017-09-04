#!/bin/sh

. ./.env.test_dev
export VCAP_SERVICES=$VCAP_SERVICES_SMOKE:x

cd tradekraft-microservice
mvn spring-boot:run
cd ..
