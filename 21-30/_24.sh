# Write a shell script to show the disk partitions and their size and disk usage i.e free space.

#!/bin/bash

# Display disk partitions and their sizes
echo "Disk partitions and their sizes:"
df -h

# Display disk usage (free space)
echo "Disk usage (free space):"
du -h /