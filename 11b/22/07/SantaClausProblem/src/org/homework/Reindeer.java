package org.homework;

public class Reindeer extends Thread {
	private int id;
	
	public Reindeer(int id) {
		this.id = id;
	}
	
	@Override
	public void run() {
		System.out.println("Reindeer #" + id + " has come.");
		try {
			Shared.counterMutex.acquire();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		Shared.reindeerCount++;
		if (Shared.reindeerCount == 9) {
			Shared.santaSem.release();
		}

		Shared.counterMutex.release();
		
		try {
			Shared.reindeerSem.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		getHitched();
	}
	
	public void getHitched() {
		System.out.println("Reindeer #" + id + " is getting hitched.");
	}
}
