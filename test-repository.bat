@echo off
chcp 65001 >nul

echo [1/3] Dang tao danh sach source cho repository test...
if not exist out mkdir out

(
    echo src\com\homestaybooking\connection\Database.java
    echo src\com\homestaybooking\shared\constants\DatabaseConstants.java
    echo src\com\homestaybooking\shared\domain\AuditInfo.java
    for %%F in (src\com\homestaybooking\shared\exceptions\*.java) do echo %%F
    for %%F in (src\com\homestaybooking\shared\repositories\*.java) do echo %%F
    echo src\com\homestaybooking\iam\authorization\Role.java
    echo src\com\homestaybooking\iam\users\domain\enums\UserStatus.java
    echo src\com\homestaybooking\iam\users\domain\User.java
    echo src\com\homestaybooking\iam\users\repositories\UserRepository.java
    echo test\com\homestaybooking\iam\users\repositories\UserRepositoryTest.java
) > test-repository-sources.txt

echo [2/3] Dang bien dich repository test...
javac -J-Xms32m -J-Xmx128m -J-XX:+UseSerialGC -encoding UTF-8 -cp "lib/*" -d out @test-repository-sources.txt

if %errorlevel% neq 0 (
    echo [LOI] Bien dich repository test that bai.
    del test-repository-sources.txt
    exit /b %errorlevel%
)

del test-repository-sources.txt

echo [3/3] Dang chay repository test...
echo ======================================================
java -Xms32m -Xmx128m -XX:+UseSerialGC -cp "out;lib/*" com.homestaybooking.iam.users.repositories.UserRepositoryTest
exit /b %errorlevel%
