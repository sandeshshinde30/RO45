#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <string.h>
#include <ctype.h>

#define FIFO1 "/tmp/fifo1"
#define FIFO2 "/tmp/fifo2"
#define BUFFER_SIZE 1024

void convert_case(char *str, int to_upper) {
    for (int i = 0; str[i]; i++) {
        str[i] = to_upper ? toupper(str[i]) : tolower(str[i]);
    }
}

int main() {
    int fd_write, fd_read;
    char buffer[BUFFER_SIZE];
    
    printf("Choose mode:\n1. Uppercase Converter\n2. Lowercase Converter\n> ");
    int choice;
    scanf("%d", &choice);
    getchar(); // Consume newline
    
    if (choice == 1) {
        // Uppercase converter (lower -> UPPER)
        mkfifo(FIFO1, 0666);
        mkfifo(FIFO2, 0666);
        
        fd_write = open(FIFO1, O_WRONLY);
        fd_read = open(FIFO2, O_RDONLY);
        
        printf("Type lowercase text (Ctrl+C to exit):\n");
        while (1) {
            printf("> ");
            fgets(buffer, BUFFER_SIZE, stdin);
            convert_case(buffer, 1); // Convert to uppercase
            write(fd_write, buffer, strlen(buffer)+1);
            
            // Read response
            if (read(fd_read, buffer, BUFFER_SIZE) > 0) {
                printf("Received: %s", buffer);
            }
        }
    }
    else if (choice == 2) {
        // Lowercase converter (UPPER -> lower)
        mkfifo(FIFO1, 0666);
        mkfifo(FIFO2, 0666);
        
        fd_read = open(FIFO1, O_RDONLY);
        fd_write = open(FIFO2, O_WRONLY);
        
        printf("Type uppercase text (Ctrl+C to exit):\n");
        while (1) {
            printf("> ");
            fgets(buffer, BUFFER_SIZE, stdin);
            convert_case(buffer, 0); // Convert to lowercase
            write(fd_write, buffer, strlen(buffer)+1);
            
            // Read response
            if (read(fd_read, buffer, BUFFER_SIZE) > 0) {
                printf("Received: %s", buffer);
            }
        }
    }
    
    close(fd_write);
    close(fd_read);
    unlink(FIFO1);
    unlink(FIFO2);
    return 0;
}