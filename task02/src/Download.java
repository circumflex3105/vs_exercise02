import javax.swing.JProgressBar;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// aktive Klasse
public class Download implements Runnable
{
	
	private final JProgressBar balken;
    // weitere Attribute zur Synchronisation hier definieren
	private Lock monitor;
	private Condition startButtonClicked;
    
	public Download(JProgressBar balken, Lock monitor, Condition startButtonClicked /* ggf. weitere Objekte */) {
		this.balken = balken;
		this.monitor = monitor;
		this.startButtonClicked = startButtonClicked;
        // ...
    }


    /*  hier die Methode definieren, die jeweils den Balken weiterschiebt
     *  Mit balken.getMaximum() bekommt man den Wert fuer 100 % gefuellt
     *  Mit balken.setValue(...) kann man den Balken einstellen (wieviel gefuellt) dargestellt wird
     *  Setzen Sie den value jeweils und legen Sie die Methode dann für eine zufaellige Zeit schlafen
     */
	public void updateBalken(int progress) {
		balken.setValue(progress);
	}

	@Override
	public void run() {
		try {
			monitor.lock();
			startButtonClicked.await();
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		} finally {
			monitor.unlock();

			Random random = new Random();

			for (int i = 0; i <= 100; i++) {
				updateBalken(i);
				try {
					int sleepTime = random.nextInt(200);
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt(); // Restore the interrupted status
				}
			}
		}
	}
}
