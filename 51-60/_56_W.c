// Write a program using FIFO, to Send data from parent to child over a pipe. (named
// pipe)

#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <string.h>

int main() {
    mkfifo("myfifo", 0666);
    int fd = open("myfifo", O_WRONLY);
    char *msg = "Hello via FIFO";
    write(fd, msg, strlen(msg)+1);
    close(fd);
    return 0;
}