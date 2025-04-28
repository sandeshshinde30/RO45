import java.net.*;
import java.io.*;
import java.util.Scanner;

public class ConcurrentEchoClient {
    private static final String HOST = "localhost";
    private static final int PORT = 8080;
    
    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(
                 new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {
            
            System.out.println("Connected to server. Type messages (or 'exit' to quit):");
            
            // Thread for receiving server responses
            new Thread(() -> {
                try {
                    String serverResponse;
                    while ((serverResponse = in.readLine()) != null) {
                        System.out.println("Server: " + serverResponse);
                    }
                } catch (IOException e) {
                    System.out.println("Disconnected from server");
                }
            }).start();
            
            // Main thread for user input
            String userInput;
            while (!(userInput = scanner.nextLine()).equalsIgnoreCase("exit")) {
                out.println(userInput);
            }
            
        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + HOST);
        } catch (IOException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
    }
}