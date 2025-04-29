// Write a program to demonstrate IPC using shared memory (shmget, shmat,
// shmdt). In this, one process will send A to Z/1 to 100 as input from user and another
// process will receive it.

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <ctype.h>

#define SHM_SIZE 1024

int main() {
    key_t key = ftok("shmfile", 65);
    int shmid = shmget(key, SHM_SIZE, 0666|IPC_CREAT);
    char *shm_ptr = (char*) shmat(shmid, (void*)0, 0);

    printf("Writer Process (PID: %d)\n", getpid());
    printf("Enter 'A' for A-Z or '1' for 1-100: ");
    char choice;
    scanf("%c", &choice);

    if (toupper(choice) == 'A') {
        // Write A-Z
        for (char c = 'A'; c <= 'Z'; c++) {
            *shm_ptr++ = c;
        }
        *shm_ptr = '\0'; // Null-terminate
        printf("A-Z written to shared memory\n");
    } else if (choice == '1') {
        // Write 1-100
        int offset = 0;
        for (int i = 1; i <= 100; i++) {
            offset += sprintf(shm_ptr + offset, "%d ", i);
        }
        printf("1-100 written to shared memory\n");
    } else {
        printf("Invalid choice\n");
    }

    printf("Press Enter when reader has finished...\n");
    getchar(); getchar(); // Wait

    shmdt(shm_ptr);
    shmctl(shmid, IPC_RMID, NULL);
    return 0;
}