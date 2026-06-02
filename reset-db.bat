@echo off
chcp 65001 >nul
setlocal

echo CANH BAO: Lenh nay se xoa volume MySQL Docker va tao lai database tu migrate.sql + seed.sql.
set /p CONFIRM=Go RESET de tiep tuc: 

if /I not "%CONFIRM%"=="RESET" (
    echo Da huy.
    pause
    exit /b 0
)

docker compose down -v
if %errorlevel% neq 0 (
    echo [LOI] Khong xoa duoc Docker volume.
    pause
    exit /b %errorlevel%
)

docker compose up -d mysql
if %errorlevel% neq 0 (
    echo [LOI] Khong khoi dong lai duoc Docker MySQL.
    pause
    exit /b %errorlevel%
)

echo Da reset database Docker. Cho container init xong roi chay app.
pause
