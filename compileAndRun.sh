#!/bin/bash

SRC_DIR="src"
BIN_DIR="bin"
MAIN_CLASS="Main"

# Compile Code
javac -d "$BIN_DIR" "$SRC_DIR"/*.java

if [ $? -eq 0 ]; then
    echo "Compilation successful!"
    echo "Running program..."
    java -cp "$BIN_DIR" "$MAIN_CLASS"
else
    echo "Compilation failed!"
fi

