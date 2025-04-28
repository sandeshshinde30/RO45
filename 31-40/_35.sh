

# Write a bash script that takes 2 or more arguments,
# i)All arguments are filenames
# ii)If fewer than two arguments are given, print an error messageiii)If the files do not exist, print error message
# iv)Otherwise concatenate files

#!/bin/bash


if [[  "$#" -lt 2 ]]
then
   echo "invalid arguments"
   exit 1
fi

for file in "$@"
do
   if [[ ! -f "${file}" ]] && [[ -e "${file}" ]]
   then
        echo "invalid file name"
        exit 1
   fi
done


fileName="merge.txt"

for file in "$@"
do
   cat "$file" >> "$fileName"
done




