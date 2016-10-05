package org.elsys.santaclaus;

import java.util.concurrent.Semaphore;

public class ToyFactory {
	
	private int elfCount = 0;
	private int reindeerCount = 0;

	private Semaphore santaSemaphore = new Semaphore(0);
	private Semaphore reindeerSemaphore = new Semaphore(0);
	private Semaphore elfSemaphore = new Semaphore(0);
	
	// Using binary semaphores here since they're equivalent to a mutex
	private Semaphore countersLock = new Semaphore(1);
	private Semaphore elfLock = new Semaphore(1);

	public void handleReindeer(Reindeer reindeer) throws InterruptedException {
		countersLock.acquire();
		
		reindeerCount++;
		if (reindeerCount == 9) {
			santaSemaphore.release();
		}
		
		countersLock.release();
		reindeerSemaphore.acquire();
		reindeer.getHitched();
	}
	
	public void handleSanta(Santa santa) throws InterruptedException {
		while (true) {
			santaSemaphore.acquire();
			
			countersLock.acquire();
			
			// When woken up, give priority to the reindeers
			if (reindeerCount == 9) {
				reindeerCount = 0;
				santa.prepSleigh();
				reindeerSemaphore.release(9);
			} else {
				elfSemaphore.release(3);
				santa.helpElves();
			}
			
			countersLock.release();
		}
	}
	
	public void handleElf(Elf elf) throws InterruptedException {
		elfLock.acquire();
		countersLock.acquire();
		
		// Wake up Santa to help if a group of three elves is formed, otherwise let more elves in
		elfCount++;
		if (elfCount == 3) {
			santaSemaphore.release();
		} else {
			elfLock.release();
		}
		
		countersLock.release();
		
		elfSemaphore.acquire();
		elf.getHelp();
		countersLock.acquire();
		
		elfCount--;
		// Let in the other elves waiting once the three are helped
		if (elfCount == 0) {
			elfLock.release();
		}
		
		countersLock.release();
	}
	
	public static void main(String[] args) {
		ToyFactory toyFactory = new ToyFactory();
		int elfId = 1;
		int reindeerId = 1;
		
		Santa santa = new Santa(1, toyFactory);
		new Thread(santa).start();
		
		int turnId = 1;
		while (true) {
			if (turnId % 3 == 0) {
				Elf elf = new Elf(elfId, toyFactory);
				new Thread(elf).start();
				elfId++;
			} else {
				Reindeer reindeer = new Reindeer(reindeerId, toyFactory);
				new Thread(reindeer).start();
				reindeerId++;
			}
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			turnId++;
		}
	}
}