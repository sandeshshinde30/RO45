// Write a multithread program in linux to use the pthread library.

#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <stdbool.h>
#include <unistd.h> // for sleep()

#define MAX_NUM 20

// Shared mutex for synchronization
pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;

// Function to check if a number is prime
bool is_prime(int num) {
    if (num <= 1) return false;
    for (int i = 2; i * i <= num; i++) {
        if (num % i == 0) return false;
    }
    return true;
}

// Thread function to print even numbers
void* print_evens(void* arg) {
    for (int i = 2; i <= MAX_NUM; i += 2) {
        pthread_mutex_lock(&mutex);
        printf("Even: %d\n", i);
        pthread_mutex_unlock(&mutex);
        sleep(1); // Slow down output for demonstration
    }
    return NULL;
}

// Thread function to print odd numbers
void* print_odds(void* arg) {
    for (int i = 1; i <= MAX_NUM; i += 2) {
        pthread_mutex_lock(&mutex);
        printf("Odd:  %d\n", i);
        pthread_mutex_unlock(&mutex);
        sleep(1); // Slow down output for demonstration
    }
    return NULL;
}

// Thread function to print prime numbers
void* print_primes(void* arg) {
    for (int i = 2; i <= MAX_NUM; i++) {
        if (is_prime(i)) {
            pthread_mutex_lock(&mutex);
            printf("Prime: %d\n", i);
            pthread_mutex_unlock(&mutex);
            sleep(1); // Slow down output for demonstration
        }
    }
    return NULL;
}

int main() {
    pthread_t t1, t2, t3;

    // Create three threads
    pthread_create(&t1, NULL, print_evens, NULL);
    pthread_create(&t2, NULL, print_odds, NULL);
    pthread_create(&t3, NULL, print_primes, NULL);

    // Wait for all threads to complete
    pthread_join(t1, NULL);
    pthread_join(t2, NULL);
    pthread_join(t3, NULL);

    // Clean up mutex
    pthread_mutex_destroy(&mutex);

    return 0;
}