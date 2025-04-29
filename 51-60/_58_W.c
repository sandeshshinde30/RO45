// Write a program using FIFO, to Send file from parent to child over a pipe. (named
// pipe)

#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/stat.h>

int main() {
    mkfifo("filefifo", 0666);
    int fifo = open("filefifo", O_WRONLY);
    int fd = open("input.txt", O_RDONLY);
    
    char buf[1024];
    int n;
    while ((n = read(fd, buf, sizeof(buf))) > 0) {
        write(fifo, buf, n);
    }
    
    close(fd);
    close(fifo);
    return 0;
}