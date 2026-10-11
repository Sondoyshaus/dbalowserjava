@echo off
set "JDK_DIR=C:\program files\JDK\jdk-27"
set "JFX_DIR=C:\program files\JFX\javafx-sdk-27\lib"

:: Runs with software rendering fallback to prevent GPU acceleration crashes
"%JDK_DIR%\bin\java" -Dprism.order=sw --module-path "%JFX_DIR%" --add-modules javafx.controls,javafx.web -jar DodongBalodowser.jar
pause