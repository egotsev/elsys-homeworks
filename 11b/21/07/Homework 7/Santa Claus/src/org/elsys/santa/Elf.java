package org.elsys.santa;

import java.util.concurrent.BrokenBarrierException;

public class Elf extends ThreadController implements Runnable {

	private static void getHelp() {
		System.out.println("Elf is getting help");
	}

	@Override
	public void run() {
		try {
			elfMutex.await();
			System.out.println("Elf has entered the waiting room");
			synchronized (m) {
				elfCount++;
				if (elfCount == 3) {
					santaSem.notify();
				} else {
					elfMutex.notify();
				}
			}
			elfSem.await();
			getHelp();
			synchronized (m) {
				elfCount--;
				if (elfCount == 0) {
					elfMutex.notify();
				}
			}
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
	}
}
