#!/usr/bin/env bash
set -euo pipefail

export DB_URL="${DB_URL:-jdbc:mysql://localhost:3307/homestaybooking?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Bangkok}"
export DB_USER="${DB_USER:-root}"
export DB_PASSWORD="${DB_PASSWORD:-123456}"

echo "[1/3] Dang tim kiem ma nguon..."
mkdir -p out
find src -name "*.java" | sort > sources.txt

echo "[2/3] Dang bien dich du an..."
javac -cp "lib/*" -d out @sources.txt
rm -f sources.txt

echo "[3/3] Bien dich thanh cong. Dang chay chuong trinh..."
echo "======================================================"
echo "DB_URL=$DB_URL"
echo "DB_USER=$DB_USER"
echo

java -DDB_URL="$DB_URL" -DDB_USER="$DB_USER" -DDB_PASSWORD="$DB_PASSWORD" -cp "out;lib/*" com.homestaybooking.Main
