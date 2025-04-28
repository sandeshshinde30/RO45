import java.net.*;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentEchoServer {
    private static final int PORT = 8080;
    private static final int MAX_THREADS = 10;
    
    public static void main(String[] args) throws IOException {
        ExecutorService pool = Executors.newFixedThreadPool(MAX_THREADS);
        
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Concurrent echo server listening on port " + PORT);
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New connection from: " + clientSocket.getRemoteSocketAddress());
                pool.execute(new ClientHandler(clientSocket));
            }
        }
    }
    
    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;
        
        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }
        
        public void run() {
            try (BufferedReader in = new BufferedReader(
                     new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(
                     clientSocket.getOutputStream(), true)) {
                
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println("[" + clientSocket.getRemoteSocketAddress() + "] " + inputLine);
                    out.println("ECHO: " + inputLine); // Enhanced echo response
                }
            } catch (IOException e) {
                System.err.println("Client error: " + e.getMessage());
            } finally {
                try {
                    clientSocket.close();
                    System.out.println("Client disconnected: " + clientSocket.getRemoteSocketAddress());
                } catch (IOException e) {
                    System.err.println("Error closing socket: " + e.getMessage());
                }
            }
        }
    }
}