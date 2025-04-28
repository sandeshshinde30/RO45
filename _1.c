// Write a program to use fork system call to create 5 child processes and assign 5 operations to childs.
#include <stdio.h>
#include <unistd.h>
// #include <sys/types.h>
#include <sys/wait.h>

int main() {
    int i;
    pid_t pid;
    
    printf("Parent process (PID: %d) started\n", getpid());
    
    for (i = 1; i <= 5; i++) {
        pid = fork();
        
        if (pid < 0) {
            // Fork failed
            fprintf(stderr, "Fork failed for child %d\n", i);
            return 1;
        } else if (pid == 0) {
            // Child process
            int a = 10 + i;
            int b = 5 + i;
            int result;
            char *operation;
            
            switch(i) {
                case 1:
                    result = a + b;
                    operation = "Addition";
                    break;
                case 2:
                    result = a - b;
                    operation = "Subtraction";
                    break;
                case 3:
                    result = a * b;
                    operation = "Multiplication";
                    break;
                case 4:
                    result = a / b;
                    operation = "Division";
                    break;
                case 5:
                    result = a % b;
                    operation = "Modulus";
                    break;
            }
            
            printf("Child %d (PID: %d): %s of %d and %d is %d\n", 
                   i, getpid(), operation, a, b, result);
            
            // Child process exits after completing its task
            return 0;
        }
    }
    
    // Parent process waits for all children to complete
    for (i = 1; i <= 5; i++) {
        wait(NULL);
    }
    
    printf("Parent process (PID: %d) completed\n", getpid());
    return 0;
}