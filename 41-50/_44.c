/* Write a program in which different processes will perform different operation on shared
memory. Operation: create memory, delete, attach/ detach(using shmget, shmat, shmdt). */

#include <stdio.h>
#include <stdlib.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <sys/wait.h>
#include <unistd.h>

#define SHM_SIZE 1024  // Size of shared memory segment

// Process 1: Creates shared memory
void create_memory() {
    key_t key = ftok(".", 'S');
    int shmid = shmget(key, SHM_SIZE, IPC_CREAT | 0666);
    
    if (shmid == -1) {
        perror("shmget failed");
        exit(1);
    }
    printf("Process %d: Created shared memory (ID: %d)\n", getpid(), shmid);
}

// Process 2: Attaches and writes to shared memory
void attach_write() {
    key_t key = ftok(".", 'S');
    int shmid = shmget(key, SHM_SIZE, 0666);
    
    if (shmid == -1) {
        perror("shmget in attach failed");
        exit(1);
    }
    
    char *shm_ptr = (char*)shmat(shmid, NULL, 0);
    if (shm_ptr == (char*)-1) {
        perror("shmat failed");
        exit(1);
    }
    
    sprintf(shm_ptr, "Hello from process %d", getpid());
    printf("Process %d: Attached and wrote to shared memory\n", getpid());
    
    shmdt(shm_ptr);
}

// Process 3: Attaches and reads from shared memory
void attach_read() {
    key_t key = ftok(".", 'S');
    int shmid = shmget(key, SHM_SIZE, 0666);
    
    if (shmid == -1) {
        perror("shmget in read failed");
        exit(1);
    }
    
    char *shm_ptr = (char*)shmat(shmid, NULL, 0);
    if (shm_ptr == (char*)-1) {
        perror("shmat failed");
        exit(1);
    }
    
    printf("Process %d: Read from shared memory: %s\n", getpid(), shm_ptr);
    
    shmdt(shm_ptr);
}

// Process 4: Deletes shared memory
void delete_memory() {
    key_t key = ftok(".", 'S');
    int shmid = shmget(key, SHM_SIZE, 0666);
    
    if (shmid == -1) {
        perror("shmget in delete failed");
        exit(1);
    }
    
    if (shmctl(shmid, IPC_RMID, NULL) == -1) {
        perror("shmctl delete failed");
        exit(1);
    }
    printf("Process %d: Deleted shared memory\n", getpid());
}

int main() {
    // Process 1: Create shared memory
    if (fork() == 0) {
        create_memory();
        exit(0);
    }
    wait(NULL);
    
    // Process 2: Attach and write
    if (fork() == 0) {
        attach_write();
        exit(0);
    }
    wait(NULL);
    
    // Process 3: Attach and read
    if (fork() == 0) {
        attach_read();
        exit(0);
    }
    wait(NULL);
    
    // Process 4: Delete
    if (fork() == 0) {
        delete_memory();
        exit(0);
    }
    wait(NULL);
    
    return 0;
}