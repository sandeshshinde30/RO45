#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <fcntl.h>

int main() {
    int fd = open("lockfile.txt", O_WRONLY | O_CREAT, 0666);
    if (fd == -1) {
        perror("open failed");
        return 1;
    }
    
    printf("Attempting to lock file...\n");
    if (lockf(fd, F_LOCK, 0) == -1) {
        perror("lockf failed");
        close(fd);
        return 1;
    }
    
    printf("File locked! Press Enter to release...\n");
    getchar();
    
    lockf(fd, F_ULOCK, 0);
    printf("File unlocked\n");
    close(fd);
    
    return 0;
}