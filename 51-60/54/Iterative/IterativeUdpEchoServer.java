import java.net.*;
import java.io.*;

public class IterativeUdpEchoServer {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        byte[] buffer = new byte[1024];
        
        try (DatagramSocket socket = new DatagramSocket(port)) {
            System.out.println("Iterative UDP echo server listening on port " + port);
            
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet); // Blocks until packet received
                
                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Received from " + packet.getAddress() + ": " + received);
                
                // Echo back to client
                DatagramPacket echoPacket = new DatagramPacket(
                    packet.getData(), 
                    packet.getLength(),
                    packet.getAddress(),
                    packet.getPort()
                );
                socket.send(echoPacket);
            }
        }
    }
}