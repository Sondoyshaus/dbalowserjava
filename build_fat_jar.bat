@echo off
set "JDK_DIR=C:\program files\JDK\jdk-27"
set "JFX_DIR=C:\program files\JFX\javafx-sdk-27\lib"

echo [1/3] Compiling source code...
"%JDK_DIR%\bin\javac.exe" --module-path "%JFX_DIR%" --add-modules javafx.controls,javafx.web SimpleBrowser.java

if %errorlevel% neq 0 (
    echo.
    echo [ERROR] Compilation failed!
    goto :end
)

echo [2/3] Creating manifest file...
echo Main-Class: SimpleBrowser> manifest.txt

echo [3/3] Packing everything into a standalone JAR...
"%JDK_DIR%\bin\jar.exe" --create --file DodongBalodowser.jar --manifest manifest.txt SimpleBrowser.class firefox.css

del manifest.txt

echo.
echo ========================================================
echo Success! Your standalone JAR is ready: DodongBalodowser.jar
echo ========================================================

:end
echo.
pause