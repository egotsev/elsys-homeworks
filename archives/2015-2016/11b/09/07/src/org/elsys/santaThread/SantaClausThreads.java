package org.elsys.santaThread;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class SantaClausThreads {
	private int elfCount = 0;
	private int reindeerCount = 0;

	private Semaphore santaSemaphore = new Semaphore(0);
	private Semaphore elfSemaphore = new Semaphore(0);
	private Semaphore reindeerSemaphore = new Semaphore(0);

	private ReentrantLock lock = new ReentrantLock();
	private ReentrantLock elfLock = new ReentrantLock();

	private AtomicBoolean running = new AtomicBoolean(false);
	private AtomicBoolean running_elf = new AtomicBoolean(false);

	private int count = 0;

	public void Reindeer() {
		if (running.equals(false)) {
			lock.lock();
			running.set(true);
		}
		count = 0;
		reindeerCount++;
		if (reindeerCount == 9) {
			santaSemaphore.release();
		}
		if (running.equals(true)) {
			lock.unlock();
			running.set(false);
		}

		try {
			reindeerSemaphore.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		getHitched();

	}

	public void Santa() {
		try {
			santaSemaphore.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (running.equals(false)) {
			lock.lock();
			running.set(true);
		}
		System.out.println("Santa has been awaken.");
		if (reindeerCount == 9) {
			reindeerCount = 0;
			prepSleigh();
			reindeerSemaphore.release(9);
		} else {
			elfSemaphore.release(3);
			helpElves();
		}
		if (running.equals(true)) {
			lock.unlock();
			running.set(false);
		}
	}

	public void Elves() {
		if (running_elf.equals(false)) {
			running_elf.set(true);
			elfLock.lock();
		}
		if (running.equals(false)) {
			running.set(true);
			lock.lock();
		}
		elfCount++;
		if (elfCount == 3) {
			santaSemaphore.release();
		} else {
			if (running_elf.equals(true)) {
				running_elf.set(false);
				elfLock.unlock();
			}
		}
		if (running.equals(true)) {
			lock.unlock();
			running.set(false);
		}

		try {
			elfSemaphore.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		getHelp();
		if (running.equals(false)) {
			lock.lock();
			running.set(true);
		}
		elfCount--;
		if (elfCount == 0) {
			if (running_elf.equals(true)) {
				running_elf.set(false);
				elfLock.unlock();
			}
		}

		if (running.equals(true)) {
			lock.unlock();
			running.set(false);
		}
	}

	private void getHelp() {
		System.out.println("Elves get helped.");

	}

	private void helpElves() {
		System.out.println("Santa Claus is helping to elves.");
	}

	private void prepSleigh() {
		System.out.println("All reindeers are back. Now let's prepare the sleigh.");
	}

	private void getHitched() {
		count++;
		System.out.println("Reindeer " + count + " get hitched.");
	}

	public static void main(String[] args) {
		SantaClausThreads constructor = new SantaClausThreads();
		int elf = 0;
		Santa s = new Santa(0, constructor);
		new Thread(s).start();
		for (int reindeer = 1; reindeer <= 9; reindeer++) {
			Reindeer r = new Reindeer(reindeer, constructor);
			new Thread(r).start();
		}
		while (true) {
			Random random = new Random();
			Elf el = new Elf(++elf, constructor);
			new Thread(el).start();
			try {
				Thread.sleep(random.nextInt(1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
