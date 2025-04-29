// Write a program to demonstrate IPC using shared memory (shmget, shmat,
// shmdt). In this, one process will send from file A to Z/1 to 100 as input from user
// and another process will receive it in file. (use same directory and different name
// files)


#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/ipc.h>
#include <sys/shm.h>

#define SHM_SIZE 1024

int main() {
    key_t key = ftok(".", 'S');
    int shmid = shmget(key, SHM_SIZE, 0666|IPC_CREAT);
    char *shm_ptr = (char*)shmat(shmid, NULL, 0);

    printf("Writer Process (PID: %d)\n", getpid());
    
    // Get user choice
    printf("Enter 'A' for A-Z or '1' for 1-100: ");
    char choice;
    scanf(" %c", &choice);

    // Prepare data
    char data[SHM_SIZE] = {0};
    if (toupper(choice) == 'A') {
        for (char c = 'A'; c <= 'Z'; c++) {
            strncat(data, &c, 1);
        }
    } else if (choice == '1') {
        for (int i = 1; i <= 100; i++) {
            char num[4];
            sprintf(num, "%d ", i);
            strcat(data, num);
        }
    } else {
        printf("Invalid choice\n");
        shmdt(shm_ptr);
        shmctl(shmid, IPC_RMID, NULL);
        return 1;
    }

    // Write to shared memory
    strcpy(shm_ptr, data);
    printf("Data written to shared memory\n");

    // Wait for reader to finish
    printf("Waiting for reader to complete...\n");
    while (*shm_ptr != '*') {
        sleep(1);
    }

    shmdt(shm_ptr);
    shmctl(shmid, IPC_RMID, NULL);
    return 0;
}