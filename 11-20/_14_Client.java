// Write a multithreaded program in JAVA for chatting.

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class _14_Client {
    public static void main(String[] args) throws IOException {
        Socket server = new Socket("localhost", 1234);
        System.out.println("Connected to server!");
        
        // Create and start threads
        new ReaderThread(server).start();
        new WriterThread(server).start();
    }
}