import java.net.*;
import java.io.*;

public class iterativeServer {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Iterative echo server listening on port " + port);
            
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(
                         new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(
                         clientSocket.getOutputStream(), true)) {
                    
                    System.out.println("Client connected: " + clientSocket.getRemoteSocketAddress());
                    
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        System.out.println("Received: " + inputLine);
                        out.println(inputLine); // Echo back
                    }
                }
            }
        }
    }
}