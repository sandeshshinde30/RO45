// Write a shell script to find the given file in the system using find or locate command.

#!/bin/bash

# Simple File Finder Script

echo "Enter the file name to search:"
read filename

# First try locate (faster but depends on updatedb)
echo -e "\nSearching with locate (faster):"
locate -i "$filename" | head -20  # Show first 20 matches, case insensitive

# Then try find (slower but searches in real-time)
echo -e "\nSearching with find (slower but thorough):"
find / -type f -iname "*$filename*" 2>/dev/null | head -20

echo -e "\nNote: For more accurate results, run 'sudo updatedb' first to update the locate database."