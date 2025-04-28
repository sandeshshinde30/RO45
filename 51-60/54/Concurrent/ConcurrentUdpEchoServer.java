import java.net.*;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentUdpEchoServer {
    private static final int PORT = 8080;
    private static final int MAX_THREADS = 10;
    
    public static void main(String[] args) throws IOException {
        ExecutorService pool = Executors.newFixedThreadPool(MAX_THREADS);
        DatagramSocket socket = new DatagramSocket(PORT);
        byte[] buffer = new byte[1024];
        
        System.out.println("Concurrent UDP echo server listening on port " + PORT);
        
        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
            pool.execute(new PacketHandler(socket, packet));
        }
    }
    
    private static class PacketHandler implements Runnable {
        private final DatagramSocket socket;
        private final DatagramPacket packet;
        
        public PacketHandler(DatagramSocket socket, DatagramPacket packet) {
            this.socket = socket;
            this.packet = packet;
        }
        
        public void run() {
            try {
                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println("[" + Thread.currentThread().getName() + "] Received from " + 
                                 packet.getAddress() + ": " + received);
                
                // Echo back
                DatagramPacket echoPacket = new DatagramPacket(
                    packet.getData(),
                    packet.getLength(),
                    packet.getAddress(),
                    packet.getPort()
                );
                socket.send(echoPacket);
            } catch (IOException e) {
                System.err.println("Error handling packet: " + e.getMessage());
            }
        }
    }
}