// Write a program using FIFO, to Send data from parent to child over a pipe. (named
// pipe)

#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>

int main() {
    int fd = open("myfifo", O_RDONLY);
    char buf[100];
    read(fd, buf, sizeof(buf));
    printf("Received: %s\n", buf);
    close(fd);
    unlink("myfifo");
    return 0;
}