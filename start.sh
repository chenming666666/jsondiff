#!/bin/bash

echo "========================================"
echo "   JSON Diff Visualization"
echo "========================================"
echo ""

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"

# Set JAVA_HOME for macOS
if [ -z "$JAVA_HOME" ]; then
    if [ -d "/opt/homebrew/Cellar/openjdk" ]; then
        export JAVA_HOME="$(ls -d /opt/homebrew/Cellar/openjdk/*/libexec/openjdk.jdk/Contents/Home | head -1)"
    elif command -v /usr/libexec/java_home &> /dev/null; then
        export JAVA_HOME="$("/usr/libexec/java_home" 2>/dev/null)"
    fi
    if [ -n "$JAVA_HOME" ]; then
        echo "Using JAVA_HOME: $JAVA_HOME"
    fi
fi

echo "Checking frontend dependencies..."
if [ ! -d "$SCRIPT_DIR/frontend/node_modules" ]; then
    echo "Installing frontend dependencies..."
    cd "$SCRIPT_DIR/frontend" || exit 1
    npm install
    if [ $? -ne 0 ]; then
        echo "Failed to install frontend dependencies!"
        exit 1
    fi
    echo ""
fi

echo "[1/2] Starting Spring Boot backend on port 8080..."
cd "$SCRIPT_DIR" || exit 1
mvn spring-boot:run &
BACKEND_PID=$!

echo "[2/2] Starting Vue.js frontend on port 5173..."
cd "$SCRIPT_DIR/frontend" || exit 1
npm run dev &
FRONTEND_PID=$!

echo ""
echo "========================================"
echo "Both services are starting..."
echo "Backend:  http://localhost:8080"
echo "Frontend: http://localhost:5173"
echo "========================================"
echo ""
echo "Press Ctrl+C to stop all services..."

trap "kill $BACKEND_PID $FRONTEND_PID 2>/dev/null; echo 'Services stopped.'; exit" INT TERM

wait
