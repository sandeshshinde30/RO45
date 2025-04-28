// Write programs to simulate linux commands cat, ls, cp, mv, head etc.

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// 1. cat - Show file contents
void my_cat(char *filename) {
    FILE *file = fopen(filename, "r");
    if (!file) {
        printf("Cannot open %s\n", filename);
        return;
    }
    
    char c;
    while ((c = fgetc(file)) != EOF) {
        putchar(c);
    }
    fclose(file);
}

// 2. ls - List files (simplest version)
void my_ls() {
    system("ls"); // Just use the real ls command
}

// 3. cp - Copy file
void my_cp(char *source, char *dest) {
    FILE *src = fopen(source, "rb");
    FILE *dst = fopen(dest, "wb");
    
    if (!src || !dst) {
        printf("File error\n");
        return;
    }
    
    char buf[1024];
    int bytes;
    while ((bytes = fread(buf, 1, 1024, src))) {
        fwrite(buf, 1, bytes, dst);
    }
    
    fclose(src);
    fclose(dst);
}

// 4. mv - Move/Rename file
void my_mv(char *oldname, char *newname) {
    if (rename(oldname, newname)) {
        printf("Move failed\n");
    }
}

// 5. head - Show first 10 lines
void my_head(char *filename) {
    FILE *file = fopen(filename, "r");
    if (!file) {
        printf("Cannot open %s\n", filename);
        return;
    }
    
    char line[256];
    for (int i = 0; i < 10 && fgets(line, 256, file); i++) {
        printf("%s", line);
    }
    fclose(file);
}

// Main menu
int main() {
    char command[10];
    char file1[100], file2[100];
    
    printf("Enter command (cat/ls/cp/mv/head): ");
    scanf("%s", command);
    
    if (strcmp(command, "cat") == 0) {
        printf("Enter filename: ");
        scanf("%s", file1);
        my_cat(file1);
    }
    else if (strcmp(command, "ls") == 0) {
        my_ls();
    }
    else if (strcmp(command, "cp") == 0) {
        printf("Enter source and destination: ");
        scanf("%s %s", file1, file2);
        my_cp(file1, file2);
    }
    else if (strcmp(command, "mv") == 0) {
        printf("Enter old and new name: ");
        scanf("%s %s", file1, file2);
        my_mv(file1, file2);
    }
    else if (strcmp(command, "head") == 0) {
        printf("Enter filename: ");
        scanf("%s", file1);
        my_head(file1);
    }
    else {
        printf("Unknown command\n");
    }
    
    return 0;
}