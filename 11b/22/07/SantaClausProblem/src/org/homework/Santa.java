package org.homework;

public class Santa extends Thread {
	@Override
	public void run() {
		while(true) {
			try {
				Shared.santaSem.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			try {
				Shared.counterMutex.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (Shared.reindeerCount == 9) {
				Shared.reindeerCount = 0;
				prepareSleigh();
				Shared.reindeerSem.release(9);
			} else {
				Shared.elfSem.release(3);
				helpElves();
			}
			Shared.counterMutex.release();
			try {
				// This exists so the data is printed accurately.
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void prepareSleigh() {
		System.out.println("Santa is preparing his sleigh.");
	}
	
	public void helpElves() {
		System.out.println("Santa is helping elves.");
	}
	
	public static void main(String[] args) {		
		Santa santa = new Santa();
		santa.start();
		
		int numberOfReindeer = 9;
		int numberOfElves = 7;
		
		for (int i = 0; i < numberOfReindeer; i++) {
			new Reindeer(i).start();
		}
		
		for (int i = 0; i < numberOfElves; i++) {
			new Elf(i).start();
		}
	}
}
