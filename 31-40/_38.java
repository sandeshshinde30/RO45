// Write program to implement producer consumer problem using semaphore.h in C/JAVA

import java.util.concurrent.Semaphore;

class SharedBuffer {
    private final int[] buffer;
    private final Semaphore mutex;
    private final Semaphore empty;
    private final Semaphore full;
    private int in, out;

    public SharedBuffer(int size) {
        buffer = new int[size];
        mutex = new Semaphore(1);
        empty = new Semaphore(size);
        full = new Semaphore(0);
        in = 0;
        out = 0;
    }

    public void produce(int item) throws InterruptedException {
        empty.acquire();
        mutex.acquire();

        buffer[in] = item;
        in = (in + 1) % buffer.length;

        System.out.println("Produced: " + item);

        mutex.release();
        full.release();
    }

    public int consume() throws InterruptedException {
        full.acquire();
        mutex.acquire();

        int item = buffer[out];
        out = (out + 1) % buffer.length;

        System.out.println("Consumed: " + item);

        mutex.release();
        empty.release();

        return item;
    }

    public void produceAndConsume() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            produce(i);
            Thread.sleep((int) (Math.random() * 100));
            consume();
            Thread.sleep((int) (Math.random() * 100));
        }
    }
}

public class _38 {
    public static void main(String[] args) {
        SharedBuffer buffer = new SharedBuffer(5);
        try {
            buffer.produceAndConsume();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
