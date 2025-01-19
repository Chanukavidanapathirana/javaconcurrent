package SharedBathroomStall;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Bathroom {
    public static final int MAX_USERS = 100;
    public static final int FLOOR1_NUM_STALLS = 6;

    private static final Semaphore stalls = new Semaphore(FLOOR1_NUM_STALLS, true);
    private static final Semaphore mutex = new Semaphore(1, true); //To assure fairness

    private static final Queue<Integer> stallQueue = new LinkedList<>();

    // Initialize the stall queue
    public static void initializeStalls() {
        for (int i = 1; i <= FLOOR1_NUM_STALLS; i++) {
            stallQueue.add(i);
        }
    }

    // Method to acquire a stall
    public static int acquireStall() throws InterruptedException {
        stalls.acquire();
        mutex.acquire();
        int stallNumber = stallQueue.poll();
        mutex.release();
        return stallNumber;
    }

    // Method to release a stall (synchronized using the mutex)
    public static void releaseStall(int stallNumber) {
        try {
            mutex.acquire();
            stallQueue.add(stallNumber);
            mutex.release();
            stalls.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
