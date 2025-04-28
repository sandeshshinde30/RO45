// Write a program to use vfork system call(login name by child and password by parent)
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h> // For _exit()

int main() {
    char login[50];
    char password[50];

    if (vfork() == 0) {  // Child process
        printf("Child: Enter login name: ");
        scanf("%s", login);
        _exit(0); // Child must exit using _exit() after completing
    } else {
        // Parent process after child exits
        printf("Parent: Enter password: ");
        scanf("%s", password);
    }

    printf("\nLogin Name: %s\nPassword: %s\n", login, password);
    return 0;
}
