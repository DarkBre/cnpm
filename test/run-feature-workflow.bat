@echo off
chcp 65001 >nul

echo [1/2] Compiling source and feature tests...
if not exist out mkdir out

(
    for /r src %%F in (*.java) do echo %%F
    for /r test %%F in (*.java) do echo %%F
) > test-feature-sources.txt

javac -J-Xms32m -J-Xmx128m -J-XX:+UseSerialGC -encoding UTF-8 -cp "lib/*" -d out @test-feature-sources.txt

if %errorlevel% neq 0 (
    echo [ERROR] Feature test compilation failed.
    del test-feature-sources.txt
    exit /b %errorlevel%
)

del test-feature-sources.txt

echo [2/2] Running feature workflow tests...
echo ======================================================
java -Xms32m -Xmx128m -XX:+UseSerialGC -cp "out;lib/*" com.homestaybooking.features.FeatureWorkflowTest
exit /b %errorlevel%
