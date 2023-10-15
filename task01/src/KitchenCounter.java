import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class KitchenCounter {
    private final Lock monitor = new ReentrantLock();
    private final Condition notEmpty = monitor.newCondition();
    private final Condition notFull = monitor.newCondition();
    private final int capacity;
    private int currentUnitCounter = 0;
    public KitchenCounter(int capacity) {
        this.capacity = capacity;
    }

    public void put() throws InterruptedException {
        monitor.lock();
        try {
            while(currentUnitCounter >= capacity) {
                notFull.await();
            }
            System.out.print("Put... [" + currentUnitCounter);
            currentUnitCounter++;
            System.out.println(" -> " + currentUnitCounter + "]");
            Thread.sleep(1000);
            notEmpty.signal();
        } finally {
            monitor.unlock();
        }
    }

    public void take() throws InterruptedException {
        monitor.lock();
        try {
            while(currentUnitCounter <= 0) {
                notEmpty.await();
            }
            System.out.print("Take... [" + currentUnitCounter);
            currentUnitCounter--;
            System.out.println(" -> " + currentUnitCounter + "]");
            Thread.sleep(1000);
            notFull.signal();
        } finally {
            monitor.unlock();
        }
    }

    public int getCurrentUnitCounter() {
        return currentUnitCounter;
    }
}
