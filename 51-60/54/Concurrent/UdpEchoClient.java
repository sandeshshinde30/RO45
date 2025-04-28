import java.net.*;
import java.io.*;
import java.util.Scanner;

public class UdpEchoClient {
    public static void main(String[] args) throws IOException {
        String hostname = "localhost";
        int port = 8080;
        Scanner scanner = new Scanner(System.in);
        
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress address = InetAddress.getByName(hostname);
            System.out.println("UDP echo client started. Type messages (or 'exit' to quit):");
            
            while (true) {
                System.out.print("> ");
                String message = scanner.nextLine();
                
                if ("exit".equalsIgnoreCase(message)) {
                    break;
                }
                
                // Send packet
                byte[] sendBuffer = message.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(
                    sendBuffer, sendBuffer.length, address, port);
                socket.send(sendPacket);
                
                // Receive echo
                byte[] receiveBuffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);
                
                String echoed = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Server echoed: " + echoed);
            }
        }
    }
}