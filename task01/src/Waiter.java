public class Waiter implements Runnable {
    private final KitchenCounter kitchenCounter;
    private final String name;
    public Waiter(KitchenCounter kitchenCounter, String name) {
        this.kitchenCounter = kitchenCounter;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            while(true) {
                kitchenCounter.put();
            }
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
