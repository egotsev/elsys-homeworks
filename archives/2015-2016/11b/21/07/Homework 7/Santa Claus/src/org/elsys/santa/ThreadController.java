package org.elsys.santa;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;

public class ThreadController {

	protected static Random random = new Random();

	protected int elfCount = 0;
	protected int reindeerCount = 0;

	protected CyclicBarrier santaSem = new CyclicBarrier(2);
	protected CyclicBarrier elfSem = new CyclicBarrier(4);
	protected CyclicBarrier reindeerSem = new CyclicBarrier(10);

	protected Object m = new Object();
	protected CyclicBarrier elfMutex = new CyclicBarrier(2);

	public static void main(String[] args) {
		Santa s = new Santa();
		new Thread(s).start();
		while (!s.isPrepped()) {
			if(random.nextInt(2) == 0){
				Elf e = new Elf();
				new Thread(e).start();
			} else {
				Reindeer r = new Reindeer();
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
