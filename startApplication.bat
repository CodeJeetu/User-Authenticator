@echo off

REM Change directory to JDK 17's bin directory
cd "C:\Program Files\Java\jdk-17\bin"

REM RUN THE jar replace this path  E:\Spring_Project\User-Authenticatior\target to actual downloaded directory
java.exe -XX:+UseG1GC -XX:+UseStringDeduplication -Xms512m -Xmx1024m -jar "E:\Spring_Project\User-Authenticatior\target\User-Authenticatior-Api.jar"

REM Pause to keep the terminal window open after execution (optional)
pause
