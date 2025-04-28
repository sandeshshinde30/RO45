#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

int main() {
    char app_name[100];
    printf("Enter application name to launch (e.g., firefox, gnome-calculator): ");
    scanf("%s", app_name);

    pid_t pid = fork();

    if (pid < 0) {
        // Fork failed
        fprintf(stderr, "Fork failed\n");
        return 1;
    } else if (pid == 0) {
        // Child process
        printf("Child process (PID: %d) launching %s...\n", getpid(), app_name);
        
        // Replace child process with the application
        execlp(app_name, app_name, NULL);
        
        // If execlp returns, there was an error
        fprintf(stderr, "Failed to launch %s\n", app_name);
        exit(1);
    } else {
        // Parent process
        printf("Parent process (PID: %d) waiting...\n", getpid());
        wait(NULL); // Wait for child to complete
        printf("%s closed. Parent exiting.\n", app_name);
    }

    return 0;
}