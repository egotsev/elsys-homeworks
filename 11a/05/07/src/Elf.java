import java.util.Random;

public class Elf extends Thread {

	private Random random = new Random();
	private int id;
	
	public Elf(int id) {
		this.id = id;
	}
	
	public void run() {
		System.out.println("Elf " + id + " Created");

		try {
			while (true) {
				Thread.sleep(random.nextInt(2000));
				SantaClaus.elfWaiting.acquire();
				System.out.println("Elf " + id + " waiting");
				SantaClaus.lock.lock();
				SantaClaus.elfCount++;
				if (SantaClaus.elfCount == 3) {
					synchronized (SantaClaus.santa) {
						SantaClaus.santa.notify();
					}
				} else {
					SantaClaus.elfWaiting.release();
				}
				SantaClaus.lock.unlock();
				synchronized (SantaClaus.elfReady) {
					SantaClaus.elfReady.wait();
				}
				getHelp();
				SantaClaus.lock.lock();
				SantaClaus.elfCount--;
				if (SantaClaus.elfCount == 0) {
					SantaClaus.elfWaiting.release();
					System.out.println("All elves left Santa's place");
				}
				SantaClaus.lock.unlock();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void getHelp() throws InterruptedException {
		System.out.println("Elf " + id + " is getting helped");
		Thread.sleep(random.nextInt(1000));
		System.out.println("Elf " + id + " left Santa's place");
	}
}
