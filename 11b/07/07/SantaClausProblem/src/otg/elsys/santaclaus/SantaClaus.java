package otg.elsys.santaclaus;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class SantaClaus implements Runnable{

	private static final int MAX_REINDEERS = 9;
	private static final int REQUIRED_NUM_OF_ELVES = 3;
	
	private int elvesCounter;
	private int reindeersCounter;
	
	private Semaphore santaSem;
	private Semaphore reindeerSem;
	private Semaphore elfSem;
	
	private ReentrantLock countersLock;
	private Semaphore door;
	
	public SantaClaus() {
		elvesCounter = 0;
		reindeersCounter = 0;
		santaSem = new Semaphore(0);
		reindeerSem = new Semaphore(0);
		elfSem = new Semaphore(0);
		countersLock = new ReentrantLock();
		door = new Semaphore(REQUIRED_NUM_OF_ELVES);
	}
	
	private void prepareSleigh() {
		System.out.println("The sleigh is ready to go . . .");
	}
	
	void onArrivedReindeer(Reindeer reindeer) {
		countersLock.lock();
			reindeersCounter++;
			//System.out.println(reindeersCounter);
			if(reindeersCounter == MAX_REINDEERS) {
				//System.out.println("BABLABLALBLA");
				santaSem.release();
			}
		countersLock.unlock();
		try {
			reindeerSem.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		reindeer.getHitched();
	}
	
	void onArrivedElf(Elf elf) {
		try {
			door.acquire();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		countersLock.lock();
			elvesCounter++;
			if(elvesCounter == REQUIRED_NUM_OF_ELVES) {
				santaSem.release();
			}else{
				door.release();
			}
		countersLock.unlock();
		try {
			elfSem.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		elf.getHelp();
		countersLock.lock();
		elvesCounter--;
		if(elvesCounter == 0) {
			try {
				door.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		countersLock.unlock();
	}
	
	private void helpElves() {
		System.out.println("Santa Clause helped elves");
	}
	
	private void santaClaus() {
		while(true){
			try {
				santaSem.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			countersLock.lock();
				if(reindeersCounter == MAX_REINDEERS) {
					reindeersCounter = 0;
					prepareSleigh();
					reindeerSem.release(MAX_REINDEERS);
				}else {
					elfSem.release(REQUIRED_NUM_OF_ELVES);
					helpElves();
				}
			countersLock.unlock();
		}
	}

	@Override
	public void run() {
		System.out.println("Santa Claus is sleeping . . .");
		Thread thread = new Thread() {
			public void run() {
				santaClaus();
			};
		};
		thread.start();
		
		Random random = new Random();
		int rc = 0;
		int ec = 0;
		
		while(true) {
			if(random.nextInt(2) == 1) {
				if(rc == MAX_REINDEERS) {
					rc = 0;
				}
				Reindeer reindeer = new Reindeer(rc, this);
				new Thread(reindeer).start();
				rc++;
			}else {
				Elf elf = new Elf(ec, this);
				new Thread(elf).start();
				ec++;
			}
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void main(String[] args) {
		new Thread(new SantaClaus()).start();
	}
}
