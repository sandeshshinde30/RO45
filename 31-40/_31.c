// Take any txt file and count word frequencies in a file.(hint : file handling + basics )

#include <stdio.h>
#include <ctype.h>
#include <string.h>
#include <stdlib.h>

#define MAX_WORDS 1000
#define MAX_WORD_LENGTH 50

struct WordCount {
    char word[MAX_WORD_LENGTH];
    int count;
};

int main() {
    char filename[100];
    printf("Enter the name of the file: ");
    scanf("%s", filename);

    FILE *file = fopen(filename, "r");
    if (file == NULL) {
        printf("Error opening file.\n");
        return 1;
    }

    struct WordCount words[MAX_WORDS];
    int total_words = 0;
    char temp_word[MAX_WORD_LENGTH];

    while (fscanf(file, "%s", temp_word) != EOF) {
        // Clean the word (remove punctuation and convert to lowercase)
        char clean_word[MAX_WORD_LENGTH] = "";
        int clean_index = 0;
        
        for (int i = 0; temp_word[i] != '\0'; i++) {
            if (isalpha(temp_word[i])) {
                clean_word[clean_index++] = tolower(temp_word[i]);
            }
        }
        clean_word[clean_index] = '\0';

        if (strlen(clean_word) == 0) continue;

        // Check if word already exists in our array
        int found = 0;
        for (int i = 0; i < total_words; i++) {
            if (strcmp(words[i].word, clean_word) == 0) {
                words[i].count++;
                found = 1;
                break;
            }
        }

        // If not found, add it to the array
        if (!found && total_words < MAX_WORDS) {
            strcpy(words[total_words].word, clean_word);
            words[total_words].count = 1;
            total_words++;
        }
    }

    // Print the word frequencies
    printf("\nWord frequencies:\n");
    for (int i = 0; i < total_words; i++) {
        printf("%s: %d\n", words[i].word, words[i].count);
    }

    fclose(file);
    return 0;
}