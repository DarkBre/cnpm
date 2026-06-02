@echo off
chcp 65001 >nul

if not exist out-test mkdir out-test

set /p DB_PASSWORD=Nhap password MySQL user root: 

javac -cp "lib/*" -d out-test ^
  src\com\homestaybooking\connection\Database.java ^
  src\com\homestaybooking\shared\repositories\ICreateRepository.java ^
  src\com\homestaybooking\shared\repositories\IReadRepository.java ^
  src\com\homestaybooking\shared\repositories\IUpdateRepository.java ^
  src\com\homestaybooking\shared\repositories\IDeleteRepository.java ^
  src\com\homestaybooking\shared\repositories\BaseRepository.java ^
  test\com\homestaybooking\shared\repositories\BaseRepositorySmokeTest.java

if %errorlevel% neq 0 (
    echo [ERROR] Compile failed.
    pause
    exit /b %errorlevel%
)

java -DDB_PASSWORD="%DB_PASSWORD%" -cp "out-test;lib/*" com.homestaybooking.shared.repositories.BaseRepositorySmokeTest

pause
