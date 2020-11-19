#!/usr/bin/env bash

# BUILD CHECK
BUILD_JAR_PATH=./build/libs/snapshot-0.0.1-SNAPSHOT.jar
if [ ! -f "$BUILD_JAR_PATH" ]; then
  ./gradlew build
fi

# echo source "$PWD"/snapshot_auto_complete.sh >> ~/.bashrc

# RUN
java -jar "$BUILD_JAR_PATH" "$@"
