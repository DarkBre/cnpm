#!/usr/bin/env bash
set -euo pipefail

echo "[1/2] Compiling source and feature tests..."
mkdir -p out

SOURCE_LIST="test-feature-sources.txt"

find src test -name "*.java" | sort > "$SOURCE_LIST"

cleanup() {
  rm -f "$SOURCE_LIST"
}

trap cleanup EXIT

javac -J-Xms32m -J-Xmx128m -J-XX:+UseSerialGC \
  -encoding UTF-8 \
  -cp "lib/*" \
  -d out \
  @"$SOURCE_LIST"

echo "[2/2] Running feature workflow tests..."
echo "======================================================"

java -Xms32m -Xmx128m -XX:+UseSerialGC \
  -cp "out;lib/*" \
  com.homestaybooking.features.FeatureWorkflowTest
