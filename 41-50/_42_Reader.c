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
    int shmid = shmget(key, SHM_SIZE, 0666);
    char *shm_ptr = (char*)shmat(shmid, NULL, 0);

    printf("Reader Process (PID: %d)\n", getpid());
    
    // Write to output file
    FILE *fp = fopen("output.txt", "w");
    if (fp == NULL) {
        perror("Failed to create output file");
        return 1;
    }
    fprintf(fp, "%s", shm_ptr);
    fclose(fp);
    printf("Data written to output.txt\n");

    // Signal writer we're done
    *shm_ptr = '*';
    
    shmdt(shm_ptr);
    return 0;
}