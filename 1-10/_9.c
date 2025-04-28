// Write a program to demonstrate the kill system call to send signals between related processes(fork).

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>
#include <sys/types.h>
#include <sys/wait.h>

void child_handler(int sig) {
    printf("Child process received signal: %d\n", sig);
}

void parent_handler(int sig) {
    printf("Parent process received signal: %d\n", sig);
}

int main() {
    pid_t pid = fork();
    
    if (pid < 0) {
        perror("Fork failed");
        exit(1);
    }
    
    if (pid == 0) { // Child process
        signal(SIGUSR1, child_handler);
        printf("Child process waiting for signal... (PID: %d)\n", getpid());
        pause(); // Wait for signal
        printf("Child process exiting.\n");
    } else { // Parent process
        signal(SIGUSR2, parent_handler);
        printf("Parent process (PID: %d), child PID: %d\n", getpid(), pid);
        
        sleep(1); // Give child time to set up handler
        
        // Parent sends signal to child
        printf("Parent sending SIGUSR1 to child...\n");
        kill(pid, SIGUSR1);
        
        // Wait a bit then child will send signal back
        sleep(1);
        
        // Child should have exited by now
        wait(NULL);
        printf("Parent process exiting.\n");
    }
    
    return 0;
}