// Write a program using PIPE, to Send file from parent to child over a pipe.
// (unnamed pipe )

#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>

int main() {
    int pipefd[2];
    pipe(pipefd);
    
    if (fork() > 0) { // Parent
        close(pipefd[0]);
        int fd = open("input.txt", O_RDONLY);
        char buf[1024];
        int n;
        while ((n = read(fd, buf, sizeof(buf))) > 0) {
            write(pipefd[1], buf, n);
        }
        close(pipefd[1]);
    } 
    else { // Child
        close(pipefd[1]);
        char buf[1024];
        int n;
        while ((n = read(pipefd[0], buf, sizeof(buf))) > 0) {
            write(STDOUT_FILENO, buf, n);
        }
        close(pipefd[0]);
    }
    
    return 0;
}