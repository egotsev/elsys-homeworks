package org.elsys.christmas;

public class Santa implements Runnable {
	
	private boolean isTheSleighReady;

	public Santa() {
		// TODO Auto-generated constructor stub
		isTheSleighReady = false;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(!isTheSleighReady) {
			try {
				System.out.println("Santa's sleeping...");
				SantaWorkshop.santaSem.acquire();
				System.out.println("Santa has woken up!");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			SantaWorkshop.lock.lock();
			try {
				if(SantaWorkshop.reindeerCount == 9) {
					SantaWorkshop.reindeerCount = 0;
					prepSleigh();
					isTheSleighReady = true;
					SantaWorkshop.reindeerSem.release(9);
				}
				else {
					helpElves();
					SantaWorkshop.elfSem.release(3);
				}
			} finally {
				SantaWorkshop.lock.unlock();
			}
		}
	}
	
	public boolean isTheSleighPrepared() {
		return isTheSleighReady;
	}
	
	public void prepSleigh() {
		System.out.println("Preparing the sleigh!");
	}
	
	public void helpElves() {
		System.out.println("Helping out 3 elves!");
	}

}
