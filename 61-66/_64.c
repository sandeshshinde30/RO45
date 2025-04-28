// Using FIFO as named pipe use read and write system calls to establish communication (IPC) between two ends.

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <string.h>

#define FIFO_NAME "/tmp/my_fifo"
#define BUFFER_SIZE 100

int main() {
    int fd;
    char buffer[BUFFER_SIZE];
    
    // Create the FIFO (named pipe)
    mkfifo(FIFO_NAME, 0666);
    
    printf("Choose mode:\n1. Writer\n2. Reader\n> ");
    int choice;
    scanf("%d", &choice);
    getchar(); // Consume newline
    
    if (choice == 1) {
        // Writer process
        fd = open(FIFO_NAME, O_WRONLY);
        
        while (1) {
            printf("Enter message (or 'exit' to quit): ");
            fgets(buffer, BUFFER_SIZE, stdin);
            
            if (strcmp(buffer, "exit\n") == 0) break;
            
            write(fd, buffer, strlen(buffer)+1);
        }
        
        close(fd);
    }
    else if (choice == 2) {
        // Reader process
        fd = open(FIFO_NAME, O_RDONLY);
        
        while (1) {
            int bytes = read(fd, buffer, BUFFER_SIZE);
            if (bytes > 0) {
                printf("Received: %s", buffer);
            }
        }
        
        close(fd);
    }
    
    // Clean up
    unlink(FIFO_NAME);
    return 0;
}