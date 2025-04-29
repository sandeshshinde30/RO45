#!/bin/bash

# Generate frequency list of all the commands you have used, and show the top 5
# commands along with their count. (Hint: history command hist will give you a list of
# all commands used.)

echo "Top 5 commands from your history:"
cat ~/.bash_history | awk '{print $1}' | sort | uniq -c | sort -nr | head -n 5