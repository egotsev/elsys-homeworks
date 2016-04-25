import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class SantaClaus extends Thread {

	protected static int elfCount = 0;
	protected static int reindeerCount = 0;

	protected static Object santa = new Object();
	protected static Object reindeerHut = new Object();
	protected static Object elfReady = new Object();
	protected static Semaphore elfWaiting = new Semaphore(1);
	protected static ReentrantLock lock = new ReentrantLock();
	protected static CyclicBarrier sleigh = new CyclicBarrier(9);

	private Random random = new Random();

	@Override
	public void run() {

		try {
			while (true) {
				System.out.println("Santa is sleeping");
				synchronized (santa) {
					santa.wait();
				}
				lock.lock();
				if (reindeerCount == 9) {
					reindeerCount = 0;
					prepSleigh();
					synchronized(reindeerHut) {
						reindeerHut.notifyAll();
					}
				} else {
					synchronized (elfReady) {
						elfReady.notifyAll();
					}
					helpElves();
				}
				lock.unlock();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void prepSleigh() throws InterruptedException {
		System.out.println("Santa is preparing the sleigh");
		Thread.sleep(random.nextInt(2000));
		System.out.println("Santa prepared the sleigh");
	}

	public void helpElves() throws InterruptedException {
		System.out.println("Santa is helping a group of elves");
		Thread.sleep(random.nextInt(1000));
		System.out.println("Santa is done helping the group of elves");
	}

}
