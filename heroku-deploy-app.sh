#!/usr/bin/env sh

mvnv clean
mvnv package
docker build -t <app_name> .
heroku container:push web --app <app_name>
heroku container:release web --app <app_name>