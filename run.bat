@echo off
chcp 65001 >nul
setlocal EnableExtensions EnableDelayedExpansion

if not defined DB_URL set "DB_URL=jdbc:mysql://127.0.0.1:3307/homestaybooking"
if not defined DB_USER set "DB_USER=root"
if not defined DB_PASSWORD set "DB_PASSWORD=123456"

echo [1/3] Dang tim kiem ma nguon...
if not exist out mkdir out

if exist sources.txt del sources.txt
powershell -NoProfile -ExecutionPolicy Bypass -Command "Get-ChildItem -Path 'src' -Recurse -Filter '*.java' | ForEach-Object { ($_.FullName.Substring((Get-Location).Path.Length + 1) -replace '\\', '/') }" > sources.txt

echo [2/3] Dang bien dich du an...
javac -cp "lib/*" -d out @sources.txt

if %errorlevel% neq 0 (
    echo [LOI] Bien dich that bai! Vui long kiem tra lai code.
    del sources.txt
    pause
    exit /b %errorlevel%
)

del sources.txt

echo [3/3] Bien dich thanh cong. Dang chay chuong trinh...
echo ======================================================
echo DB_URL=!DB_URL!
echo DB_USER=!DB_USER!
echo.

java "-DDB_URL=!DB_URL!" "-DDB_USER=!DB_USER!" "-DDB_PASSWORD=!DB_PASSWORD!" -cp "out;lib/*" com.homestaybooking.Main

echo.
echo ======================================================
pause
