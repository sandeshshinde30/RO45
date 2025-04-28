// Write a program to demonstrate the exit system call use with wait & fork sysem call.

#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>

int main() {
    printf("I'm the parent (PID: %d)\n", getpid());
    
    // Create a child process
    if (fork() == 0) {
        // This is the child
        printf("I'm the child (PID: %d)\n", getpid());
        printf("Child is exiting now!\n");
        _exit(0);  // Child exits here
    } else {
        // This is the parent
        printf("Parent is waiting for child...\n");
        wait(NULL);  // Wait for any child to finish
        printf("Parent is done!\n");
    }
    
    return 0;
}