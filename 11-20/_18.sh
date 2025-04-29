# // Write a program to implement shell script for calculator.

#!/bin/bash

# Simple calculator shell script

echo "Welcome to the Simple Calculator"
echo "1. Addition"
echo "2. Subtraction"
echo "3. Multiplication"
echo "4. Division"
echo "5. Exit"

while true; do
    read -p "Enter your choice (1-5): " choice

    case $choice in
        1)
            read -p "Enter first number: " num1
            read -p "Enter second number: " num2
            echo "Result: $((num1 + num2))"
            ;;
        2)
            read -p "Enter first number: " num1
            read -p "Enter second number: " num2
            echo "Result: $((num1 - num2))"
            ;;
        3)
            read -p "Enter first number: " num1
            read -p "Enter second number: " num2
            echo "Result: $((num1 * num2))"
            ;;
        4)
            read -p "Enter first number: " num1
            read -p "Enter second number: " num2
            if [ $num2 -eq 0 ]; then
                echo "Error: Division by zero"
            else
                echo "Result: $((num1 / num2))"
            fi
            ;;
        5)
            echo "Exiting calculator. Goodbye!"
            exit 0
            ;;
        *)
            echo "Invalid choice. Please enter a number between 1 and 5."
            ;;
    esac
done