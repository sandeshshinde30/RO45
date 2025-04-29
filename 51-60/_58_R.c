// Write a program using FIFO, to Send file from parent to child over a pipe. (named
// pipe)

#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>

int main() {
    int fifo = open("filefifo", O_RDONLY);
    char buf[1024];
    int n;
    while ((n = read(fifo, buf, sizeof(buf))) > 0) {
        write(STDOUT_FILENO, buf, n);
    }
    close(fifo);
    unlink("filefifo");
    return 0;
}