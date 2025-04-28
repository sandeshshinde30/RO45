// Write a program to use alarm and signal sytem call(check i/p from user within time)

#include <stdio.h>
#include <unistd.h>
#include <signal.h>

void timeout_handler() {
    printf("\nToo slow! Time's up!\n");
    _exit(0);  // Exit immediately
}

int main() {
    char name[50];
    
    // Set alarm to trigger after 5 seconds
    signal(SIGALRM, timeout_handler);
    alarm(5);
    
    printf("Quick! Enter your name within 5 seconds: ");
    scanf("%s", name);
    
    // If we get here, input was received in time
    alarm(0);  // Cancel the alarm
    printf("Hello, %s!\n", name);
    
    return 0;
}