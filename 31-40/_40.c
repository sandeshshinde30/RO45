#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <unistd.h>
#include <errno.h>

#define MAX_MSG_SIZE 100

struct msg_buffer {
    long msg_type;
    char msg_text[MAX_MSG_SIZE];
};

int main() {
    key_t key = ftok(".", 'A');
    int msgid = msgget(key, 0666 | IPC_CREAT);
    if (msgid == -1) {
        perror("msgget failed");
        exit(1);
    }

    printf("Chat Program (PID: %d, Type 'exit' to quit)\n", getpid());

    // Determine if we're the first or second instance
    struct msqid_ds buf;
    msgctl(msgid, IPC_STAT, &buf);
    int is_first = (buf.msg_qnum == 0);

    if (is_first) {
        printf("You are User 1 (send first)\n");
    } else {
        printf("You are User 2 (reply to messages)\n");
    }

    struct msg_buffer message;
    message.msg_type = 1; // Using fixed message type for simplicity

    while (1) {
        if (is_first) {
            // User 1 sends first
            printf("User1: ");
            fgets(message.msg_text, MAX_MSG_SIZE, stdin);
            if (msgsnd(msgid, &message, sizeof(message.msg_text), 0) == -1) {
                perror("msgsnd failed");
                break;
            }
            if (strncmp(message.msg_text, "exit", 4) == 0) break;

            // Then wait for reply
            printf("Waiting for reply...\n");
            if (msgrcv(msgid, &message, sizeof(message.msg_text), 1, 0) == -1) {
                perror("msgrcv failed");
                break;
            }
            printf("User2: %s", message.msg_text);
            if (strncmp(message.msg_text, "exit", 4) == 0) break;
        } else {
            // User 2 receives first
            printf("Waiting for message...\n");
            if (msgrcv(msgid, &message, sizeof(message.msg_text), 1, 0) == -1) {
                perror("msgrcv failed");
                break;
            }
            printf("User1: %s", message.msg_text);
            if (strncmp(message.msg_text, "exit", 4) == 0) break;

            // Then send reply
            printf("User2: ");
            fgets(message.msg_text, MAX_MSG_SIZE, stdin);
            if (msgsnd(msgid, &message, sizeof(message.msg_text), 0) == -1) {
                perror("msgsnd failed");
                break;
            }
            if (strncmp(message.msg_text, "exit", 4) == 0) break;
        }
    }

    // Clean up (only the last process should do this)
    msgctl(msgid, IPC_STAT, &buf);
    if (buf.msg_qnum == 0) {
        msgctl(msgid, IPC_RMID, NULL);
        printf("Chat session ended\n");
    }

    return 0;
}