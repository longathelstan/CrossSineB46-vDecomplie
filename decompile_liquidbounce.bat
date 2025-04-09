@echo off
setlocal

rem Tên file JAR và CFR
set "JAR=CrossSine-b46.jar"
set "CFR=cfr.jar"

rem Thư mục đầu ra
set "OUT=decompiled_src"

rem Tạo thư mục đầu ra nếu chưa tồn tại
if not exist "%OUT%" mkdir "%OUT%"

rem Chạy CFR để decompile toàn bộ JAR
java -jar "%CFR%" "%JAR%" --outputdir "%OUT%" --caseinsensitivefs true --comments false --silent true

echo.
echo === Decomplie successfully, Source in "%OUT%" ===
pause
