package bg.homework.santaclaus;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class NorthPole implements Runnable {

	private int elvesWaiting = 0;
	private int readyReindeers = 0;
	private ReentrantLock counterLock = new ReentrantLock();
	private Semaphore doorSemaphore = new Semaphore(3);

	private Semaphore santaSemaphore = new Semaphore(0);
	private Semaphore reindeersSemaphore = new Semaphore(0);
	private Semaphore elveSemaphore = new Semaphore(0);

	private boolean isSleighReady = false;

	public void reindeerArriving(int id) throws InterruptedException {
		counterLock.lock();
		readyReindeers++;
		if (readyReindeers == 9) {
			santaSemaphore.release();
		}
		counterLock.unlock();
		reindeersSemaphore.acquire();
		getHitched(id);
	}

	private void getHitched(int id) {
		System.out.println("Reindeer "+id+" is hitched");
	}
	private void prepSleigh() {
		System.out.println("The sleigh is ready");
	}

	public void santaClaus() throws InterruptedException {
		while (true) {
			santaSemaphore.acquire();
			counterLock.lock();
			if (readyReindeers == 9) {
				readyReindeers = 0;
				reindeersSemaphore.release(9);
				isSleighReady = true;
			} else {
				elveSemaphore.release(3);
				helpElves();
			}
			counterLock.unlock();
		}
	}

	private void getHelp(int i) {
		System.out.println("Elf " + i + " was given help");
	}

	private void helpElves() {
		System.out.println("Santa is helping elves");
	}

	public void elfNeedHelp(int id) throws InterruptedException {

		doorSemaphore.acquire();
		counterLock.lock();
		elvesWaiting++;
		if (elvesWaiting == 3) {
			santaSemaphore.release();
		}
		counterLock.unlock();
		elveSemaphore.acquire();
		getHelp(id);
		counterLock.lock();
		elvesWaiting--;
		doorSemaphore.release();
		counterLock.unlock();
	}


	public static void main(String[] argc) {
		NorthPole northPole = new NorthPole();
		new Thread(northPole).start();

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		int elfCounter = 1;
		int reindeerCounter = 1;
		int counter = 0;
		SantaClaus santaClaus = new SantaClaus(this);
		new Thread(santaClaus).start();
		while (true) {
			
			if(isSleighReady){
				prepSleigh();
				return;
			}

			if (counter % 3 == 0) {
				Reindeer reindeer = new Reindeer(reindeerCounter, this);
				new Thread(reindeer).start();
				reindeerCounter++;
			} else if (counter % 2 == 0) {
				Elf elf = new Elf(elfCounter, this);
				new Thread(elf).start();
				elfCounter++;
			}

			counter++;
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
