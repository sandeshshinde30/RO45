// Write a program using PIPE, to Send data from parent to child over a pipe.
// (unnamed pipe

#include <stdio.h>
#include <unistd.h>
#include <string.h>

int main() {
    int pipefd[2];
    char buf[100];
    
    if (pipe(pipefd) == -1) {
        perror("pipe");
        return 1;
    }

    pid_t pid = fork();
    
    if (pid > 0) { // Parent process
        close(pipefd[0]); // Close read end
        char *msg = "Hello from parent";
        write(pipefd[1], msg, strlen(msg)+1);
        close(pipefd[1]);
    } 
    else if (pid == 0) { // Child process
        close(pipefd[1]); // Close write end
        read(pipefd[0], buf, sizeof(buf));
        printf("Child received: %s\n", buf);
        close(pipefd[0]);
    }
    
    return 0;
}