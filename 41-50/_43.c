/* Write a program to demonstrate IPC using shared memory (shmget, shmat, shmdt). In
this, one process will take numbers as input from user and second process will sort the
numbers and put back to shared memory. Third process will display the shared memory. */

#include <stdio.h>
#include <stdlib.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <sys/wait.h>  // Added for wait()
#include <unistd.h>

#define SIZE 5  // Reduced size for simplicity

int main() {
    // Create shared memory
    int shmid = shmget(IPC_PRIVATE, SIZE * sizeof(int), 0666|IPC_CREAT);
    int *nums = (int*)shmat(shmid, NULL, 0);

    // Initialize to zero
    for (int i = 0; i < SIZE; i++) nums[i] = 0;

    // Input Process
    if (fork() == 0) {
        printf("Enter %d numbers: ", SIZE);
        for (int i = 0; i < SIZE; i++) {
            scanf("%d", &nums[i]);
        }
        exit(0);
    }
    wait(NULL);  // Parent waits for input

    // Sort Process
    if (fork() == 0) {
        // Simple bubble sort
        for (int i = 0; i < SIZE-1; i++) {
            for (int j = 0; j < SIZE-i-1; j++) {
                if (nums[j] > nums[j+1]) {
                    int temp = nums[j];
                    nums[j] = nums[j+1];
                    nums[j+1] = temp;
                }
            }
        }
        exit(0);
    }
    wait(NULL);  // Parent waits for sort

    // Display Process
    printf("Sorted numbers: ");
    for (int i = 0; i < SIZE; i++) {
        printf("%d ", nums[i]);
    }
    printf("\n");

    // Clean up
    shmdt(nums);
    shmctl(shmid, IPC_RMID, NULL);
    
    return 0;
}