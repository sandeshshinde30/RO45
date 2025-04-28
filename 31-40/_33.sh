# Write a shell script that will take a filename as input and check if it is executable. 
# 2. Modify the script in the previous question, to remove the execute permissions, if the file is executable.

#!/bin/bash

echo "Enter filename to check:"
read filename

if [ -x "$filename" ]; then
    echo "$filename is executable - removing execute permissions"
    chmod -x "$filename"
    echo "Execute permissions removed"
else
    echo "$filename is not executable"
fi