package org.elsys.santaproblem;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

	private static Random randomNumber = new Random();
	
	private Semaphore santaSemaphore = new Semaphore(0);
	private Semaphore reindeerSemaphore = new Semaphore(0);
	private Semaphore elfSemaphore = new Semaphore(0);
	
	private ReentrantLock lock = new ReentrantLock();
	private ReentrantLock anotherLock = new ReentrantLock();
	
	private AtomicBoolean is_running = new AtomicBoolean(false);
	private AtomicBoolean is_elf_running = new AtomicBoolean(false);
	
	private int reindeerCount = 0;
	private int elfCount = 0;
	
	public static void main(String[] args) {
		Main constructor = new Main();
		Santa santa = new Santa(0, constructor);
		Thread santaThread = new Thread(santa);
		santaThread.start();
		
		int numberOfElves = 0;
		int numberOfReindeer = 0;
		
		while(!santa.isPrepared()) {
			if (randomNumber.nextInt(3) == 0) {
				Elf elf = new Elf(++numberOfElves, constructor);
				new Thread(elf).start();
			} else {
				Reindeer reindeer = new Reindeer(++numberOfReindeer, constructor);
				new Thread(reindeer).start();
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void santaLive(Santa santa) throws InterruptedException {
		santaSemaphore.acquire();
		if (is_running.equals(false)) {
			lock.lock();
			is_running.set(true);
		}
		System.out.println("Santa woke up!");
		if (reindeerCount == 9) {
			santa.prepareSleight();
			reindeerSemaphore.release();
		} else {
			elfSemaphore.release();
			santa.helpElves();
		}
		
		if (is_running.equals(true)) {
			lock.unlock();
			is_running.set(false);
		}
	}
	
	public void reindeerLive(Reindeer reindeer) throws InterruptedException {
		if (is_running.equals(false)) {
			lock.lock();
			is_running.set(true);
		}
		
		reindeerCount += 1;
		if (reindeerCount == 9) {
			santaSemaphore.release();
		}
		
		if (is_running.equals(true)) {
			lock.unlock();
			is_running.set(false);
		}
		
		reindeerSemaphore.acquire();
		
		reindeer.getHitched();
	}
	
	public void elfLive(Elf elf) throws InterruptedException {
		if (is_elf_running.equals(false)) {
			is_elf_running.set(true);
			anotherLock.lock();
		}
		
		if (is_running.equals(false)) {
			is_running.set(true);
			lock.lock();
		}
		
		elfCount += 1;
		if (elfCount >= 3) {
			santaSemaphore.release();
		} else {
			if (is_elf_running.equals(true)) {
				is_elf_running.set(false);
				anotherLock.unlock();
			}
		}
		
		if (is_running.equals(true)) {
			is_running.set(false);
			lock.unlock();
		}
		
		elfSemaphore.acquire();
		
		elf.getHelp();
		
		if (is_running.equals(false)) {
			is_running.set(true);
			lock.lock();
		}
		
		elfCount -= 1;
		if (elfCount == 0) {
			if (is_elf_running.equals(true)) {
				is_elf_running.set(false);
				anotherLock.unlock();
			}
		}
		
		if (is_running.equals(true)) {
			is_running.set(false);
			lock.unlock();
		}
	}

}
