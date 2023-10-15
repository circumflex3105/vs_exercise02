public class Student implements Runnable {
    private final KitchenCounter kitchenCounter;
    private final String name;
    public Student(KitchenCounter kitchenCounter, String name) {
        this.kitchenCounter = kitchenCounter;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            while(true) {
                kitchenCounter.take();
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
