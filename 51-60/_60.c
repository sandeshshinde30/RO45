// Write a program to illustrate the semaphore concept. Use fork so that 2 process running
// simultaneously and communicate via semaphore. (give diff between sem.h/semaphore.h)

#include <stdio.h>
#include <unistd.h>
#include <pthread.h>
#include <semaphore.h>

sem_t sem;

void process() {
    sem_wait(&sem);
    printf("Process %d entered critical section\n", getpid());
    sleep(2); // Simulate work
    printf("Process %d exiting critical section\n", getpid());
    sem_post(&sem);
}

int main() {
    sem_init(&sem, 1, 1); // 1 = shared between processes
    
    if (fork() == 0) {
        process(); // Child process
        return 0;
    }
    
    process(); // Parent process
    wait(NULL);
    sem_destroy(&sem);
    return 0;
}