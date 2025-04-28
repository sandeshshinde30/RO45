// Write a program to create 3 threads, first thread printing even no, second thread printing odd no. and third thread printing prime no.

public class _15 {
    public static void main(String[] args) {
        // Create and start all three threads
        new EvenNumberThread().start();
        new OddNumberThread().start();
        new PrimeNumberThread().start();
    }
}

// Thread to print even numbers
class EvenNumberThread extends Thread {
    public void run() {
        for (int i = 2; i <= 20; i += 2) {
            System.out.println("Even: " + i);
            try { Thread.sleep(100); } catch (Exception e) {}
        }
    }
}

// Thread to print odd numbers
class OddNumberThread extends Thread {
    public void run() {
        for (int i = 1; i <= 20; i += 2) {
            System.out.println("Odd:  " + i);
            try { Thread.sleep(100); } catch (Exception e) {}
        }
    }
}

// Thread to print prime numbers
class PrimeNumberThread extends Thread {
    public void run() {
        for (int i = 2; i <= 20; i++) {
            if (isPrime(i)) {
                System.out.println("Prime: " + i);
                try { Thread.sleep(100); } catch (Exception e) {}
            }
        }
    }
    
    private boolean isPrime(int num) {
        if (num <= 1) return false;
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) return false;
        }
        return true;
    }
}