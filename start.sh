#!/bin/bash

WATCH_DIR=src
SERVER_JAR=target/server-application-1.0-SNAPSHOT.jar
SERVER_PID=

kill_port_process() {
    echo "Checking if port 1001 is in use..."
    PORT_PID=$(lsof -t -i:10001)
    if [[ -n "$PORT_PID" ]]; then
        echo "Stopping process on port 1001..."
        kill $PORT_PID
        echo "Process stopped."
    else
        echo "Port 1001 is not in use."
    fi
}

compile_project() {
    echo "Detected change. Recompiling..."

    kill_port_process

    # Stop the server before deleting the target directory
    # if [[ -n "$SERVER_PID" ]]; then
    #     echo "Stopping server..."
    #     if ps -p $SERVER_PID > /dev/null; then
    #         kill $SERVER_PID
    #         echo "Server stopped."
    #     else
    #         echo "Server is not running."
    #     fi
    # fi

    # Delete the target directory
    #rm -rf target

    nice mvn package
    
    echo "Compilation complete."

    echo "Starting server..."
    java -jar $SERVER_JAR &
    SERVER_PID=$!
    echo "Server started (PID: $SERVER_PID)."
}

watch_changes() {
    echo "Watching directory: $WATCH_DIR"
    while true; do
        new_timestamp=$(find $WATCH_DIR -type f -printf '%T@ %p\n' | sort -n | tail -1 | cut -f1 -d" ")
        if [[ "$new_timestamp" != "$timestamp" ]]; then
            timestamp=$new_timestamp
            compile_project
        fi
        sleep 1
    done
}

watch_changes