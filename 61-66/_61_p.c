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
    struct sembuf sop = {0, -1, 0}; // P operation
    
    printf("Attempting P operation on semaphore %d...\n", semid);
    if (semop(semid, &sop, 1) == -1) {
        perror("semop P failed");
        return 1;
    }
    
    printf("P operation successful! Semaphore locked.\n");
    printf("Critical section entered (sleeping for 5 seconds)...\n");
    sleep(5);
    printf("P operation process exiting (remember to run V operation!)\n");
    
    return 0;
}