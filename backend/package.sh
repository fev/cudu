#!/bin/bash
set -euo pipefail
export CUDU_VERSION=$(git describe --tags --dirty=-dev --always)
./gradlew clean build --daemon -Djava.awt.headless=true

