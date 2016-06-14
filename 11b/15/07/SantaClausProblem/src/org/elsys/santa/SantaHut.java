package org.elsys.santa;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

import java.util.concurrent.locks.ReentrantLock;

public class SantaHut {
	private int elfCount = 0;
	private int reindeerCount = 0;
	
	private CyclicBarrier reindeerSem = new CyclicBarrier(10);
	private CyclicBarrier santaSem = new CyclicBarrier(2);
	private CyclicBarrier elfSem = new CyclicBarrier(4);
	
	private ReentrantLock mutex = new ReentrantLock();
	private Semaphore elfMutex = new Semaphore(3);
	
	
	public void reindeersPlace(Reindeers reindeer)
	{
		mutex.lock();
		System.out.println("Reindeer here");
		reindeerCount++;
		if(reindeerCount == 9)
		{
			System.out.println("Notifying Santa ...");
			try {
				santaSem.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		}
		mutex.unlock();
		try {
			reindeerSem.await();
		} catch (InterruptedException | BrokenBarrierException e) {
					e.printStackTrace();
		}
		System.out.println("Hitching Reindeer");
		reindeer.getHitched();
		
	}
	
	public void elfsPlace(Elf elf)
	{
		boolean isLocked = false;
		try {
			elfMutex.acquire();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		mutex.lock();
		System.out.println("Waiting at the door");
		elfCount++;
		if(elfCount == 3)
		{
			isLocked = true;
			mutex.unlock();
			try {
				santaSem.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}	
		}
		else
		{
			elfMutex.release();
		}
		
		if(isLocked)
		{
			isLocked = false;
		}
		else
		{
			mutex.unlock();
		}
		try {
			elfSem.await();
		} catch (InterruptedException | BrokenBarrierException e1) {
			e1.printStackTrace();
		}
		System.out.println("Getting help");
		elf.getHelp();
		mutex.lock();
		System.out.println("Releasing elf");
		elfCount --;
		if(elfCount == 0 )
		{
			elfMutex.release();
		}
		mutex.unlock();
	}
	
	public void santasPlace(Santa santa)
	{
			boolean locked = false;
			try {
				santaSem.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
			System.out.println("Santa woke up!");
			mutex.lock();
			if(reindeerCount == 9)
			{
				reindeerCount = 0;
				mutex.unlock();
				locked = true;
				santa.prepSleight();
				try {
					reindeerSem.await();
				} catch (InterruptedException | BrokenBarrierException e) {
					e.printStackTrace();
				}	
			}
			else
			{
					try {
						elfSem.await();
					} catch (InterruptedException | BrokenBarrierException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				santa.helpElves();
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Elf Sem = " + elfSem.getNumberWaiting() + " Reindeer Sem = " + reindeerSem.getNumberWaiting() + " Santa Sem = " + santaSem.getNumberWaiting());
			if(!locked)
			{
				mutex.unlock();
			}	
	}
}
