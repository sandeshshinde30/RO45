# Write a program to implement digital clock using shell script.

#!/bin/bash

# Simple digital clock in shell

clear
echo "Digital Clock"
echo "Press Ctrl+C to exit"

while true; do
    # Get current time and date
    current_time=$(date +"%T")
    current_date=$(date +"%A, %B %d, %Y")
    
    # Move cursor to beginning of line and clear line
    echo -ne "\r\033[K"
    
    # Display time and date
    echo -ne "$current_date | $current_time"
    
    # Wait for 1 second
    sleep 1
done