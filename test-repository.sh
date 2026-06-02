#!/usr/bin/env bash
set -euo pipefail

echo "[1/3] Dang tao danh sach source cho repository test..."
mkdir -p out

SOURCE_LIST="test-repository-sources.txt"

{
  echo "src/com/homestaybooking/connection/Database.java"
  echo "src/com/homestaybooking/shared/constants/DatabaseConstants.java"
  echo "src/com/homestaybooking/shared/domain/AuditInfo.java"
  find src/com/homestaybooking/shared/exceptions -name "*.java" | sort
  find src/com/homestaybooking/shared/repositories -name "*.java" | sort
  echo "src/com/homestaybooking/iam/authorization/Role.java"
  echo "src/com/homestaybooking/iam/users/domain/enums/UserStatus.java"
  echo "src/com/homestaybooking/iam/users/domain/User.java"
  echo "src/com/homestaybooking/iam/users/repositories/UserRepository.java"
  echo "test/com/homestaybooking/iam/users/repositories/UserRepositoryTest.java"
} > "$SOURCE_LIST"

cleanup() {
  rm -f "$SOURCE_LIST"
}

trap cleanup EXIT

echo "[2/3] Dang bien dich repository test..."
javac -J-Xms32m -J-Xmx128m -J-XX:+UseSerialGC \
  -encoding UTF-8 \
  -cp "lib/*" \
  -d out \
  @"$SOURCE_LIST"

echo "[3/3] Dang chay repository test..."
echo "======================================================"

# Git Bash on Windows still uses the Windows Java classpath separator.
java -Xms32m -Xmx128m -XX:+UseSerialGC \
  -cp "out;lib/*" \
  com.homestaybooking.iam.users.repositories.UserRepositoryTest
