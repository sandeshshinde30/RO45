
// Write a program using PIPE, to convert uppercase to lowercase filter to read command/
// from file
#include <stdio.h>
#include <unistd.h>
#include <ctype.h>

int main() {
    int pipefd[2];
    pipe(pipefd);
    
    if (fork() > 0) { // Parent - writer
        close(pipefd[0]);
        char c;
        while ((c = getchar()) != EOF) {
            write(pipefd[1], &c, 1);
        }
        close(pipefd[1]);
    } 
    else { // Child - filter
        close(pipefd[1]);
        char c;
        while (read(pipefd[0], &c, 1) > 0) {
            putchar(tolower(c));
        }
        close(pipefd[0]);
    }
    
    return 0;
}