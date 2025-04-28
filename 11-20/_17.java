// Write a multithreaded program for producer-consumer problem in JAVA.

import java.util.LinkedList;

public class _17 {
    public static void main(String[] args) {
        LinkedList<Integer> buffer = new LinkedList<>();
        int capacity = 5;
        
        // Producer thread
        new Thread(() -> {
            int item = 0;
            while (true) {
                synchronized (buffer) {
                    while (buffer.size() == capacity) {
                        try { buffer.wait(); } 
                        catch (InterruptedException e) { e.printStackTrace(); }
                    }
                    
                    System.out.println("Produced: " + item);
                    buffer.add(item++);
                    buffer.notify();
                    
                    try { Thread.sleep(1000); } 
                    catch (InterruptedException e) { e.printStackTrace(); }
                }
            }
        }).start();
        
        // Consumer thread
        new Thread(() -> {
            while (true) {
                synchronized (buffer) {
                    while (buffer.isEmpty()) {
                        try { buffer.wait(); } 
                        catch (InterruptedException e) { e.printStackTrace(); }
                    }
                    
                    int item = buffer.removeFirst();
                    System.out.println("Consumed: " + item);
                    buffer.notify();
                    
                    try { Thread.sleep(1000); } 
                    catch (InterruptedException e) { e.printStackTrace(); }
                }
            }
        }).start();
    }
}