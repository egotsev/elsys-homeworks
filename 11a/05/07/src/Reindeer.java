import java.util.Random;
import java.util.concurrent.BrokenBarrierException;

public class Reindeer extends Thread {

	private Random random = new Random();
	private int id;

	public Reindeer(int id) {
		this.id = id;
	}

	public void run() {
		System.out.println("Reindeer " + id + " Created");

		try {
			while (true) {
				Thread.sleep(random.nextInt(2000));
				SantaClaus.lock.lock();
				System.out.println("Reindeer " + id + " returned");
				SantaClaus.reindeerCount++;
				if (SantaClaus.reindeerCount == 9) {
					synchronized(SantaClaus.santa) {
						SantaClaus.santa.notify();
					}
				}
				SantaClaus.lock.unlock();
				synchronized(SantaClaus.reindeerHut) {
					SantaClaus.reindeerHut.wait();
				}
				getHitched();
				if(SantaClaus.sleigh.getNumberWaiting() == 8) {
					System.out.println("All reindeers hitched");
				}
				SantaClaus.sleigh.await();
			}
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}

	}

	public void getHitched() throws InterruptedException {
		System.out.println("Reindeer " + id + " is hitching");
		Thread.sleep(random.nextInt(1000));
		System.out.println("Reindeer " + id + " hitched");
	}

}
