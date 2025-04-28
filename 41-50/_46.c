/* Write a program to ensure that function f1 should executed before executing function f2
using semaphore. (Ex. Program should ask for username before entering password). */

#include <stdio.h>
#include <pthread.h>
#include <semaphore.h>

sem_t sem;

void* f1(void* arg) {
    printf("Enter username: ");
    char username[50];
    scanf("%s", username);
    
    sem_post(&sem);  // Signal that username is entered
    return NULL;
}

void* f2(void* arg) {
    sem_wait(&sem);  // Wait for username to be entered first
    
    printf("Enter password: ");
    char password[50];
    scanf("%s", password);
    
    printf("Login successful!\n");
    return NULL;
}

int main() {
    pthread_t t1, t2;
    sem_init(&sem, 0, 0);  // Initialize semaphore to 0
    
    pthread_create(&t1, NULL, f1, NULL);
    pthread_create(&t2, NULL, f2, NULL);
    
    pthread_join(t1, NULL);
    pthread_join(t2, NULL);
    
    sem_destroy(&sem);
    return 0;
}