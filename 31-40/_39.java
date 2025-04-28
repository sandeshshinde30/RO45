// Write a program to implement reader-writers problem using semaphore.

import java.util.concurrent.Semaphore;

class SharedData {
    private int data;
    private int readersCount;
    private Semaphore mutex;
    private Semaphore wrt;

    public SharedData() {
        data = 0;
        readersCount = 0;
        mutex = new Semaphore(1);
        wrt = new Semaphore(1);
    }

    public void read() throws InterruptedException {
        mutex.acquire();
        readersCount++;
        if (readersCount == 1) {
            wrt.acquire();
        }
        mutex.release();

        System.out.println("Reader reads: " + data);

        mutex.acquire();
        readersCount--;
        if (readersCount == 0) {
            wrt.release();
        }
        mutex.release();
    }

    public void write(int newData) throws InterruptedException {
        wrt.acquire();
        data = newData;
        System.out.println("Writer writes: " + newData);
        wrt.release();
    }
}

class Reader extends Thread {
    private SharedData data;

    public Reader(SharedData data) {
        this.data = data;
    }

    public void run() {
        try {
            while (true) {
                data.read();
                Thread.sleep((int) (Math.random() * 100));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Writer extends Thread {
    private SharedData data;

    public Writer(SharedData data) {
        this.data = data;
    }

    public void run() {
        try {
            while (true) {
                int newData = (int) (Math.random() * 100);
                data.write(newData);
                Thread.sleep((int) (Math.random() * 100));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class _39 {
    public static void main(String[] args) {
        SharedData data = new SharedData();

        Reader[] readers = new Reader[5];
        Writer[] writers = new Writer[2];

        for (int i = 0; i < readers.length; i++) {
            readers[i] = new Reader(data);
            readers[i].start();
        }

        for (int i = 0; i < writers.length; i++) {
            writers[i] = new Writer(data);
            writers[i].start();
        }
    }
}
