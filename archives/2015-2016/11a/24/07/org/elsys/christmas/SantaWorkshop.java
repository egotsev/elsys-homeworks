package org.elsys.christmas;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class SantaWorkshop {
	
	public static int elfCount = 0;
	public static int reindeerCount = 0;
	
	public static Semaphore santaSem = new Semaphore(0);
	public static Semaphore elfSem = new Semaphore(0);
	public static Semaphore reindeerSem = new Semaphore(0);
	public static Semaphore door = new Semaphore(3);
	public static ReentrantLock lock = new ReentrantLock(true);
	
	private static ExecutorService reindeerExecutor;
	private static ExecutorService elfExecutor;
	private static ExecutorService santaExecutor;
	
	private static Random random = new Random();

	public static void main(String[] args) {
		santaExecutor = Executors.newFixedThreadPool(1);
		reindeerExecutor = Executors.newFixedThreadPool(9);
		elfExecutor = Executors.newCachedThreadPool();
		Santa santa = new Santa();
		santaExecutor.execute(santa);
		int rCount = 1;
		int eCount = 1;
		while(!santa.isTheSleighPrepared()) {
			if(random.nextInt(2) == 0) {
				elfExecutor.execute(new Elf(eCount));
				eCount++;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				reindeerExecutor.execute(new Reindeer(rCount));
				rCount++;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		santaExecutor.shutdown();
		reindeerExecutor.shutdown();
		elfExecutor.shutdown();
	}

}
