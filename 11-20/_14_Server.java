// Write a multithreaded program in JAVA for chatting.

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class _14_Server {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(1234);
        System.out.println("Server waiting for connection...");
        
        Socket client = server.accept();
        System.out.println("Client connected!");
        
        // Create and start threads
        new ReaderThread(client).start();
        new WriterThread(client).start();
    }
}

class ReaderThread extends Thread {
    private Socket socket;
    
    public ReaderThread(Socket socket) {
        this.socket = socket;
    }
    
    public void run() {
        try {
            Scanner in = new Scanner(socket.getInputStream());
            while (in.hasNextLine()) {
                System.out.println("Client: " + in.nextLine());
            }
        } catch (IOException e) {
            System.out.println("Client disconnected");
        }
    }
}

class WriterThread extends Thread {
    private Socket socket;
    
    public WriterThread(Socket socket) {
        this.socket = socket;
    }
    
    public void run() {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner console = new Scanner(System.in);
            
            while (true) {
                out.println(console.nextLine());
            }
        } catch (IOException e) {
            System.out.println("Connection lost");
        }
    }
}