#!/bin/bash

SRC_DIR="src"
BIN_DIR="bin"
MAIN_CLASS="Main"

# Create bin directory if it doesn't exist
mkdir -p "$BIN_DIR"

# Compile Java file
javac -d "$BIN_DIR" "$SRC_DIR/$MAIN_CLASS.java"

# Check if compilation was successful
if [ $? -eq 0 ]; then
    echo "Compilation successful!"
    echo "Running program..."
    java -cp "$BIN_DIR" "$MAIN_CLASS"
else
    echo "Compilation failed!"
fi

