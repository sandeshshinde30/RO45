// Write a program to demonstrate the kill system call to send signals between unrelated processes.

#include <stdio.h>
#include <signal.h>
#include <unistd.h>

int main() {
    int pid, signal_num = 10; // SIGUSR1 = 10
    
    printf("Enter Receiver PID: ");
    scanf("%d", &pid);
    
    printf("Sending signal %d to PID %d...\n", signal_num, pid);
    kill(pid, signal_num); // Send SIGUSR1
    
    printf("Signal sent!\n");
    return 0;
}