@echo off
echo ========================================
echo    JSON Diff Visualization
echo ========================================
echo.

echo Checking frontend dependencies...
if not exist "%~dp0frontend\node_modules" (
    echo Installing frontend dependencies...
    cd /d "%~dp0frontend"
    call npm install
    if %ERRORLEVEL% NEQ 0 (
        echo Failed to install frontend dependencies!
        pause
        exit /b 1
    )
    echo.
)

echo [1/2] Starting Spring Boot backend on port 8080...
start "JSON-Diff Backend" cmd /k "cd /d %~dp0 && mvn spring-boot:run"

timeout /t 5 /nobreak >nul

echo [2/2] Starting Vue.js frontend on port 5173...
start "JSON-Diff Frontend" cmd /k "cd /d %~dp0frontend && npm run dev"

echo.
echo ========================================
echo Both services are starting...
echo Backend:  http://localhost:8080
echo Frontend: http://localhost:5173
echo ========================================
echo.
echo Press any key to close this window...
pause >nul
