#!/bin/bash


WATCH_DIR=src
SERVER_JAR=target/server-application-1.0-SNAPSHOT.jar
SERVER_PID=

trap cleanup INT

cleanup() {
    echo "Stopping server and database..."
    if [[ -n "$SERVER_PID" ]]; then
        kill $SERVER_PID
    fi
    docker stop mtcgdb
    echo "Server and database stopped."
    exit 0
}

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

    Stop the server before deleting the target directory
    if [[ -n "$SERVER_PID" ]]; then
        echo "Stopping server..."
        if ps -p $SERVER_PID > /dev/null; then
            kill $SERVER_PID
            echo "Server stopped."
        else
            echo "Server is not running."
        fi
    fi

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
        new_timestamp=$(find $WATCH_DIR -type f -exec stat -f "%m%t%N" {} + | sort -n | tail -1 | cut -f1)
        if [[ "$new_timestamp" != "$timestamp" ]]; then
            timestamp=$new_timestamp
            compile_project
        fi
        sleep 1
    done
}

start_database() {
    echo "Starting PostgreSQL database..."
    docker start mtcgdb
    docker exec mtcgdb psql -U postgres -d mydb -c '\l'
    echo "Database started."
}

start_database
watch_changes