package org.elsys.santa;

public class Santa implements Runnable {
	private boolean hasPreppedSleigh;
	
	public Santa() {
		hasPreppedSleigh = false;
	}
	
	public boolean sleighHasBeenPrepped() {
		return hasPreppedSleigh;
	}
	
	private void prepSleigh() {
		hasPreppedSleigh = true;
		System.out.println("Santa is prepping the sleigh.");
	}

	private void helpElves() {
		System.out.println("Santa is helping the elves.");
	}

	@Override
	public void run() {
		while(!sleighHasBeenPrepped()){
			try {
				System.out.println("Santa is sleeping.");
				Main.santaSem.acquire();
				System.out.println("Santa woke up.");
				
				synchronized(Main.lock){
					if (Main.reindeerCount == 9) {
						Main.reindeerCount = 0;
						prepSleigh();
						Main.reindeerSem.release(9);
					} else {
						Main.elfSem.release(3);
						helpElves();
					}
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
