#!/usr/bin/env sh

heroku create <app_name> --region eu
heroku addons:create heroku-postgresql:hobby-dev --app <app_name>