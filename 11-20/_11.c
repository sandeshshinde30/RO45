//Write a program for alarm clock using alarm and signal system call.


#include <stdio.h>
#include <unistd.h>
#include <signal.h>
#include <stdlib.h>

void alarm_handler(int signum) {
    printf("\nALARM! Wake up!\n");
    exit(0);
}

int main(int argc, char *argv[]) {
    if (argc != 2) {
        printf("Usage: %s <seconds>\n", argv[0]);
        return 1;
    }

    int seconds = atoi(argv[1]);
    if (seconds <= 0) {
        printf("Please enter a positive number of seconds\n");
        return 1;
    }

    // Set up signal handler
    signal(SIGALRM, alarm_handler);

    printf("Alarm set for %d seconds\n", seconds);
    alarm(seconds); // Set the alarm

    // Keep the program running until alarm goes off
    while(1) {
        pause(); // Wait for any signal
    }

    return 0;
}