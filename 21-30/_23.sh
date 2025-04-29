# Write a shell script to find whether given file exist or not in folder or on drive.

#!/bin/bash

# Simple script to check if a file exists

# Check if filename is provided
if [ $# -eq 0 ]; then
    echo "Error: Please provide a filename as argument"
    echo "Usage: $0 <filename>"
    exit 1
fi

filename=$1

# Check in current directory first
if [ -e "$filename" ]; then
    echo "File '$filename' found in current directory"
    exit 0
fi

# If not found, search the entire drive (may take time for large drives)
echo "Searching for '$filename' on the drive..."
result=$(find / -name "$filename" 2>/dev/null | head -n 1)

if [ -n "$result" ]; then
    echo "File found at: $result"
else
    echo "File '$filename' not found on the drive"
fi