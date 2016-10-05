package org.elsys.santa;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
	public static int elfCount = 0;
	public static int reindeerCount = 0;

	public static Semaphore santaSem = new Semaphore(0);
	public static Semaphore reindeerSem = new Semaphore(0);
	public static Semaphore elfSem = new Semaphore(0);
	public static Semaphore doorSem = new Semaphore(3);
	public static Object lock = new Object();

	private static Random random = new Random();

	public static void main(String[] args) {
		int elfsMade = 1;
		int reindeersReturned = 1;
		
		Santa s = new Santa();
		new Thread(s).start();
		
		while (!s.sleighHasBeenPrepped()) {
			if (random.nextInt(2) == 0) {
				Elf e = new Elf(elfsMade);
				elfsMade++;
				new Thread(e).start();
			} else {
				Reindeer r = new Reindeer(reindeersReturned);
				reindeersReturned++;
				new Thread(r).start();
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
