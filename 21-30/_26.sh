# Write a shell script to download webpage at given url using command(wget)

#!/bin/bash

# Simple Webpage Downloader

echo "Enter the URL to download:"
read url

# Download the webpage with wget
wget "$url"

# Check if download was successful
if [ $? -eq 0 ]; then
    echo -e "\nWebpage downloaded successfully!"
    echo "Saved as: $(ls -t | head -1)"  # Show most recently created file
else
    echo -e "\nFailed to download the webpage"
fi