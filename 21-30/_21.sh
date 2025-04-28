// Write a program to sort 10 the given 10 numbers in ascending order using shell.

#!/bin/bash

echo "Enter 10 numbers separated by spaces:"
read -a numbers  # Read input into an array

# Sort the numbers using built-in sort command
sorted=($(printf "%s\n" "${numbers[@]}" | sort -n))

echo "Sorted numbers:"
printf "%d " "${sorted[@]}"
echo  # Just for a new line