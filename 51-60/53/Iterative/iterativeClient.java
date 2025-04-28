import java.net.*;
import java.io.*;
import java.util.Scanner;

public class iterativeClient {
    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 8080;
        
        try (Socket socket = new Socket(hostname, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(
                 new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {
            
            System.out.println("Connected to echo server. Type 'exit' to quit.");
            
            while (true) {
                System.out.print("Enter message: ");
                String userInput = scanner.nextLine();
                
                if ("exit".equalsIgnoreCase(userInput)) {
                    break;
                }
                
                out.println(userInput);  // Send to server
                String response = in.readLine();  // Wait for echo
                System.out.println("Server echoed: " + response);
            }
            
        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + hostname);
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
        }
    }
}