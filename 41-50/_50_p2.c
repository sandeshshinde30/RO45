// Write a 2 programs that will both send and messages and construct the following
// dialog between them
// (Process 1) Sends the message "Are you hearing me?"
// (Process 2) Receives the message and replies "Loud and Clear".
// (Process 1) Receives the reply and then says "I can hear you too".
// IPC:Message Queues:msgget, msgsnd, msgrcv.

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/ipc.h>
#include <sys/msg.h>

struct msg_buffer {
    long msg_type;
    char msg_text[100];
};

int main() {
    key_t key = ftok("progfile", 65);
    int msgid = msgget(key, 0666 | IPC_CREAT);
    struct msg_buffer message;

    // Process 2 receives first message
    msgrcv(msgid, &message, sizeof(message), 1, 0);
    printf("(Process 2) Received: %s\n", message.msg_text);

    // Process 2 sends reply
    message.msg_type = 2;
    strcpy(message.msg_text, "Loud and Clear");
    msgsnd(msgid, &message, sizeof(message), 0);
    printf("(Process 2) Sent: %s\n", message.msg_text);

    // Process 2 receives final message
    msgrcv(msgid, &message, sizeof(message), 1, 0);
    printf("(Process 2) Received: %s\n", message.msg_text);

    return 0;
}