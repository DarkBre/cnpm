@echo off
chcp 65001 >nul
setlocal

echo Dang khoi dong MySQL Docker cho Homestay Booking...
docker compose up -d mysql

if %errorlevel% neq 0 (
    echo [LOI] Khong khoi dong duoc Docker MySQL. Hay kiem tra Docker Desktop dang chay chua.
    pause
    exit /b %errorlevel%
)

echo.
echo MySQL Docker dang chay:
echo   Host: localhost
echo   Port: 3307
echo   Database: homestaybooking
echo   User: root
echo   Password: 123456
echo.
echo Lan dau khoi dong se tu nap database/migrate.sql va database/seed.sql.
pause
