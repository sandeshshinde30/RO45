# Write a shell script to download a given file from ftp://10.10.13.16 if it exists on ftp.(use lftp, get and mget commands).
#!/bin/bash

FTP_SERVER="192.168.225.81"
FTP_USER="sandesh"
FTP_PASS="sandesh30"
FILE="$1"

if [ -z "$FILE" ]; then
    echo "Error: Please specify a filename to download"
    exit 1
fi

ftp -n -v $FTP_SERVER <<EOF
user $FTP_USER $FTP_PASS
passive
binary
get "$FILE"
bye
EOF

if [ -f "$FILE" ]; then
    echo "File downloaded successfully"
else
    echo "Error: Failed to download file"
    exit 1
fi