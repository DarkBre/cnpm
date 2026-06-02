@echo off
chcp 65001 >nul
setlocal

if "%DB_NAME%"=="" set "DB_NAME=homestaybooking"
if "%DB_USER%"=="" set "DB_USER=root"
if "%DB_PASSWORD%"=="" set "DB_PASSWORD=123456"

echo Dang import database\seed.sql vao Docker database %DB_NAME%...
docker compose exec -T mysql mysql -u%DB_USER% -p%DB_PASSWORD% %DB_NAME% < database\seed.sql

if %errorlevel% neq 0 (
    echo [LOI] Import seed that bai. Hay chay start-db.bat truoc va doi container healthy.
    pause
    exit /b %errorlevel%
)

echo Import seed thanh cong.
pause
