@echo off
chcp 65001 > nul

cls
echo +---------------------------------------------+
echo ^| Harry Potter Adventure - Mini Project OOP   ^|
echo ^|                                             ^|
echo ^| Platform: Console (Text-Based)              ^|
echo ^| Author: Kelompok Blackpink                  ^|
echo +---------------------------------------------+
echo Compiling all source files...

javac -d . src/hogwarts/Main.java
javac -d . src/hogwarts/model/*.java
javac -d . src/hogwarts/item/*.java
javac -d . src/hogwarts/npc/*.java
javac -d . src/hogwarts/battle/*.java
javac -d . src/hogwarts/story/*.java

if errorlevel 1 (
    echo +---------------------------------------------+
    echo ^| ERROR: Compilation FAILED!                  ^|
    echo ^| Check your code or folder structure.        ^|
    echo +---------------------------------------------+
    pause
    exit /b 1
)

echo.
echo +---------------------------------------------+
echo ^| SUCCESS: Compilation completed!             ^|
echo ^| Starting the game...                        ^|
echo +----------------------------------------------+

java -Dfile.encoding=UTF-8 hogwarts.Main

echo.
echo +---------------------------------------------+
echo ^| Game Selesai                                ^|
echo +---------------------------------------------+
pause