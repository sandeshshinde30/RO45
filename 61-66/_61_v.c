#include <stdio.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/sem.h>

int main(int argc, char *argv[]) {
    if (argc != 2) {
        printf("Usage: %s <semaphore_id>\n", argv[0]);
        return 1;
    }
    
    int semid = atoi(argv[1]);
    struct sembuf sop = {0, 1, 0}; // V operation
    
    printf("Attempting V operation on semaphore %d...\n", semid);
    if (semop(semid, &sop, 1) == -1) {
        perror("semop V failed");
        return 1;
    }
    
    printf("V operation successful! Semaphore unlocked.\n");
    return 0;
}