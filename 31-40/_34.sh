# Generate a word frequency list for wonderland.txt. Hint: use grep, tr, sort, uniq (or anything else that you want)
#!/bin/bash

# Simple Word Counter
tr '[:upper:]' '[:lower:]' < wonderland.txt | 
tr -cs '[:alpha:]' '\n' | 
sort | 
uniq -c | 
sort -nr