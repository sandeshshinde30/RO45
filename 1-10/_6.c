// Write a program to demonstrate the variations exec system call.

#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>

int main() {
    pid_t pid;
    char *args[] = {"ls", "-l", NULL};
    char *env[] = {"PATH=/bin:/usr/bin", NULL};

    printf("Parent process (PID: %d)\n", getpid());

    // Fork a child process
    pid = fork();
    
    if (pid == 0) { // Child process
        printf("\nChild process (PID: %d)\n", getpid());
        
        // Demonstrate different exec variations
        int choice;
        printf("Choose exec variation to demonstrate (1-6):\n");
        printf("1. execl\n2. execlp\n3. execle\n4. execv\n5. execvp\n6. execvpe\n");
        scanf("%d", &choice);

        switch(choice) {
            case 1:
                printf("Using execl:\n");
                execl("/bin/ls", "ls", "-l", NULL);
                break;
            case 2:
                printf("Using execlp:\n");
                execlp("ls", "ls", "-l", NULL);
                break;
            case 3:
                printf("Using execle:\n");
                execle("/bin/ls", "ls", "-l", NULL, env);
                break;
            case 4:
                printf("Using execv:\n");
                execv("/bin/ls", args);
                break;
            case 5:
                printf("Using execvp:\n");
                execvp("ls", args);
                break;
            case 6:
                printf("Using execvpe:\n");
                execvpe("ls", args, env);
                break;
            default:
                printf("Invalid choice\n");
                return 1;
        }
        
        // If exec fails
        perror("exec failed");
        return 1;
    } 
    else if (pid > 0) { // Parent process
        wait(NULL); // Wait for child to complete
        printf("\nChild process finished\n");
    } 
    else { // Fork failed
        perror("fork failed");
        return 1;
    }

    return 0;
}


// // #include <stdio.h>
// #include <unistd.h>
// #include <sys/wait.h>

// int main() {
//     pid_t pid;
//     char *args[] = {"gedit", "test.txt", NULL};
//     char *env[] = {"PATH=/usr/bin", NULL};

//     printf("Parent process (PID: %d)\n", getpid());

//     pid = fork();
    
//     if (pid == 0) { // Child process
//         printf("\nChild process (PID: %d)\n", getpid());
        
//         int choice;
//         printf("Choose exec variation to demonstrate (1-6):\n");
//         printf("1. execl\n2. execlp\n3. execle\n4. execv\n5. execvp\n6. execvpe\n");
//         scanf("%d", &choice);

//         switch(choice) {
//             case 1:
//                 printf("Using execl:\n");
//                 execl("/usr/bin/gedit", "gedit", "test.txt", NULL);
//                 break;
//             case 2:
//                 printf("Using execlp:\n");
//                 execlp("gedit", "gedit", "test.txt", NULL);
//                 break;
//             case 3:
//                 printf("Using execle:\n");
//                 execle("/usr/bin/gedit", "gedit", "test.txt", NULL, env);
//                 break;
//             case 4:
//                 printf("Using execv:\n");
//                 execv("/usr/bin/gedit", args);
//                 break;
//             case 5:
//                 printf("Using execvp:\n");
//                 execvp("gedit", args);
//                 break;
//             case 6:
//                 printf("Using execvpe:\n");
//                 execvpe("gedit", args, env);
//                 break;
//             default:
//                 printf("Invalid choice\n");
//                 return 1;
//         }
        
//         perror("exec failed");
//         return 1;
//     } 
//     else if (pid > 0) {
//         wait(NULL);
//         printf("\nGedit closed. Child process finished\n");
//     } 
//     else {
//         perror("fork failed");
//         return 1;
//     }

//     return 0;
// }