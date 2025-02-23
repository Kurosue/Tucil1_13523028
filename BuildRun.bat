@echo off

set SRC_DIR=src
set BIN_DIR=bin
set MAIN_CLASS=Main

rem Compile Code
javac -d "%BIN_DIR%" "%SRC_DIR%\*.java"

if %errorlevel% equ 0 (
    echo Compilation successful!
    echo Running program...
    java -cp "%BIN_DIR%" %MAIN_CLASS%
) else (
    echo Compilation failed!
)