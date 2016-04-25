package org.elsys.santa;

import java.util.concurrent.BrokenBarrierException;

class Reindeer extends ThreadController implements Runnable {

	private void getHitched() {
		System.out.println("Reindeer is getting hitched");
	}

	@Override
	public void run() {
		System.out.println("Reindeer has returned");
		try {
			synchronized(m){
				reindeerCount++;
				if (reindeerCount == 9) {
					santaSem.notify();
				}
			}
			reindeerSem.await();
			getHitched();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
	}
}
