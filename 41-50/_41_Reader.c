// Write a program to demonstrate IPC using shared memory (shmget, shmat,
// shmdt). In this, one process will send A to Z/1 to 100 as input from user and another
// process will receive it.

#include <stdio.h>
#include <stdlib.h>
#include <sys/ipc.h>
#include <sys/shm.h>

#define SHM_SIZE 1024

int main() {
    key_t key = ftok("shmfile", 65);
    int shmid = shmget(key, SHM_SIZE, 0666|IPC_CREAT);
    char *shm_ptr = (char*) shmat(shmid, (void*)0, 0);

    printf("Reader Process (PID: %d)\n", getpid());
    printf("Data read from shared memory:\n%s\n", shm_ptr);

    shmdt(shm_ptr);
    return 0;
}