package org.elsys.santa;

import java.util.concurrent.BrokenBarrierException;

public class Santa extends ThreadController implements Runnable {
	protected boolean prepped;

	public Santa() {
		prepped = false;
	}

	private void prepSleigh() {
		System.out.println("Santa is prepping sleigh");
		prepped = true;
	}

	public boolean isPrepped() {
		return prepped;
	}

	private void helpElves() {
		System.out.println("Santa is helping elves");
	}

	@Override
	public void run() {
		while (!prepped) {
			try {
				santaSem.await();
				System.out.println("Santa has been awoken");
				synchronized (m) {
					if (reindeerCount == 9) {
						reindeerCount = 0;
						prepSleigh();
						reindeerSem.notifyAll();;
					} else {
						elfSem.notifyAll();;
						helpElves();
					}
				}
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Sleigh has been prepped. Merry Christmas to all and to all a good night!!!");
	}
}
