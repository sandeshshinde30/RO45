# // write shell script with FIFO/mknod (named pipe) us for communication (IPC)

#!/bin/bash

# Create a named pipe
PIPE=/tmp/my_pipe
mkfifo $PIPE 2>/dev/null || true

# Function for the writer
writer() {
    echo "Writer started. Type messages (Ctrl+C to exit):"
    while true; do
        read -r message
        echo "WRITER: $message" > $PIPE
    done
}

# Function for the reader
reader() {
    echo "Reader started (waiting for messages)..."
    while true; do
        if read line <$PIPE; then
            echo "READER received: $line"
        fi
    done
}

# Menu to choose mode
echo "Select mode:"
echo "1. Writer"
echo "2. Reader"
echo "3. Both (in different terminals)"
read -p "Choice: " choice

case $choice in
    1) writer ;;
    2) reader ;;
    3) 
        echo "Run this script in two terminals:"
        echo "Terminal 1: Choose 1 (Writer)"
        echo "Terminal 2: Choose 2 (Reader)"
        ;;
    *) echo "Invalid choice" ;;
esac

# Cleanup (not reached in reader/writer loops)
rm -f $PIPE