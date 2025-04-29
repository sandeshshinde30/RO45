#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <fcntl.h>
#include <sys/file.h>

int main() {
    int fd = open("flockfile.txt", O_WRONLY | O_CREAT, 0666);
    if (fd == -1) {
        perror("open failed");
        return 1;
    }
    
    printf("Attempting to lock file with flock...\n");
    if (flock(fd, LOCK_EX) == -1) {
        perror("flock failed");
        close(fd);
        return 1;
    }
    
    printf("File locked with flock! Press Enter to release...\n");
    getchar();
    
    flock(fd, LOCK_UN);
    printf("File unlocked\n");
    close(fd);
    
    return 0;
}