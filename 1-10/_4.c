// Write a program to open any application using vfork sysem call.
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

int main() {
    char app_name[100];
    
    printf("Enter application to launch (e.g., firefox, gedit): ");
    scanf("%99s", app_name);

    pid_t pid = vfork();  // Using vfork instead of fork

    if (pid < 0) {
        // vfork failed
        fprintf(stderr, "vfork failed\n");
        exit(1);
    } else if (pid == 0) {
        // Child process
        printf("Child (PID: %d) launching %s...\n", getpid(), app_name);
        
        // Replace child process with the application
        execlp(app_name, app_name, NULL);
        
        // If execlp returns, there was an error
        fprintf(stderr, "Failed to launch %s\n", app_name);
        _exit(1);  // Must use _exit() with vfork
    } else {
        // Parent process
        printf("Parent (PID: %d) waiting...\n", getpid());
        wait(NULL);  // Wait for child to complete
        printf("%s closed. Parent exiting.\n", app_name);
    }

    return 0;
}