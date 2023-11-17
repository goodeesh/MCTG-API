#!/bin/bash

# Define the directory to watch
WATCH_DIR="src"

# Function to compile the project

is_port_available() {
    netstat -tuln | grep ":$PORT " > /dev/null
    return $?
}
start_server(){
    java -jar target/server-application-1.0-SNAPSHOT.jar &

}

compile_project() {
    pkill -f "java -jar target/server-application-1.0-SNAPSHOT.jar"
    echo "Detected change. Recompiling..."
    mvn clean install
    echo "Compilation complete."
    # Start the server
    start_server
}

# Compile and start the server initially
compile_project

# Watch for changes in the directory
while inotifywait -r -e modify --exclude '\.class$' "$WATCH_DIR"; do
    compile_project
done

