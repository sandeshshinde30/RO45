// Write a program to demonstrate the wait use with fork sysem call.
#include <stdio.h>
#include <unistd.h>  // for fork() and sleep()
#include <sys/wait.h> // for wait()

int main() {
    printf("I'm the parent (PID: %d)\n", getpid());
    
    // Create a child process
    int pid = fork();
    
    if (pid == 0) {
        // Child process
        printf("I'm the child (PID: %d)\n", getpid());
        sleep(2);  // Child sleeps for 2 seconds
        printf("Child is done!\n");
    } else {
        // Parent process
        printf("Parent waiting for child...\n");
        wait(NULL);  // Wait for child to finish
        printf("Parent is done!\n");
    }
    
    return 0;
}