package org.elsys.santaclaus;

import java.util.concurrent.Semaphore;

public class SantaClausSolution {
	private int elfCount;
	private int raindeerCount;
	private Semaphore santa;
	private Semaphore raindeerSemaphore;
	private Semaphore counterSemaphore;
	private Semaphore elvesSemaphore;
	private Semaphore elvesMutex;
	private Semaphore reindeerMutex;
	private boolean santaMustSleep;
	private boolean elfShouldWait;
	private boolean sleighIsReady;
	
	public SantaClausSolution() {
		this.santa = new Semaphore(1);
		this.elfCount = 0;
		this.raindeerCount = 0;
		this.raindeerSemaphore = new Semaphore(9);
		this.counterSemaphore = new Semaphore(1);
		this.elvesSemaphore = new Semaphore(3);
		this.elvesMutex = new Semaphore(1);
		this.reindeerMutex = new Semaphore(1);
		this.santaMustSleep = true;
		this.elfShouldWait = true;
		this.sleighIsReady = false;
	}
	
	public static void main(String[] args) {
		SantaClausSolution solution = new SantaClausSolution();
		Raindeer[] reindeers = new Raindeer[9];
		for (int i = 0; i < reindeers.length; i++) {
			reindeers[i] = new Raindeer(i);
			final int ir = i;
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						solution.raindeerWorker(reindeers[ir]);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
		
		Elf[] elves = new Elf[50];
		for (int i = 0; i < elves.length; i++) {
			elves[i] = new Elf(i);
			final int ie = i;
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						solution.elfWorker(elves[ie]);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					solution.santaWorker();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	public void prepSleigh() {
		System.out.println("Santa prepped the sleigh!");
		this.sleighIsReady = true;
	}
	
	public void helpElves() {
		System.out.println("Santa helped the elves!");
		this.elfShouldWait = false;
	}
	
	public void santaWorker() throws InterruptedException {
		synchronized (this.santa) {
			while(true) {
				while (this.santaMustSleep) {
					this.santa.wait();
				}

				this.counterSemaphore.acquire();
				if (this.raindeerCount == 9) {
					this.prepSleigh();
					synchronized (this.reindeerMutex) {
						this.reindeerMutex.notifyAll();
					}
				} else {
					this.helpElves();
					synchronized (this.elvesMutex) {
						this.elvesMutex.notifyAll();
					}
				}
				
				this.santaMustSleep = true;
				this.counterSemaphore.release();
			}
		}
	}
	
	public void elfWorker(Elf elf) throws InterruptedException {
		while(true) {
			this.elvesSemaphore.acquire();
			this.counterSemaphore.acquire();
			this.elfCount++;
			System.out.println(this.elfCount);
			if (this.elfCount == 3) {
				synchronized (this.santa) {
					this.santaMustSleep = false;
					this.santa.notifyAll();
				}
			}
			
			this.counterSemaphore.release();
			synchronized (this.elvesMutex) {
				while (this.elfShouldWait) {
					this.elvesMutex.wait();
				}
				
				elf.getHelp();
				this.counterSemaphore.acquire();
				this.elfCount--;
				if (this.elfCount == 0) {
					this.elfShouldWait = true;
					this.elvesSemaphore.release(3);
				}
				
				this.counterSemaphore.release();
			}
		}
	}
	
	public void raindeerWorker(Raindeer reindeer) throws InterruptedException {
		while(true) {
			this.raindeerSemaphore.acquire();
			this.counterSemaphore.acquire();
			this.raindeerCount++;
			
			if (this.raindeerCount == 9) {
				synchronized (this.santa) {
					this.santaMustSleep = false;
					this.santa.notify();
				}
			}
			
			this.counterSemaphore.release();
			
			synchronized(this.reindeerMutex) {
				while (!this.sleighIsReady) {
					this.reindeerMutex.wait();
				}
				
				reindeer.getHitched();
				this.counterSemaphore.acquire();
				this.raindeerCount--;
				if (this.raindeerCount == 0) {
					this.raindeerSemaphore.release(9);
					this.sleighIsReady = false;
				}
				
				this.counterSemaphore.release();
			}
		}
	}
}
