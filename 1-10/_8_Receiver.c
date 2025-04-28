// Write a program to demonstrate the kill system call to send signals between unrelated processes.

#include <stdio.h>
#include <signal.h>
#include <unistd.h>

void handle_signal(int sig) {
    printf("Received signal: %d\n", sig);
}

int main() {
    signal(SIGUSR1, handle_signal); // Catch SIGUSR1 (signal 10)
    printf("Receiver PID: %d\n", getpid());
    printf("Waiting for signals...\n");
    while(1) sleep(1); // Keep running
    return 0;
}