#include <stdio.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/sem.h>

int main() {
    key_t key = ftok(".", 'S');
    int semid = semget(key, 1, IPC_CREAT | 0666);
    
    if (semid == -1) {
        perror("semget failed");
        return 1;
    }
    
    // Initialize semaphore value to 1
    semctl(semid, 0, SETVAL, 1);
    
    printf("Semaphore created with ID: %d\n", semid);
    printf("Run P operation and V operation programs with this ID\n");
    
    return 0;
}