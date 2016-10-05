package org.elsys.santa;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class NorthPole
{
	private int elfCounter = 0;
	private int raindeerCounter = 0;
	
	private CyclicBarrier santaSem = new CyclicBarrier(1);
	private CyclicBarrier reindeerSem = new CyclicBarrier(9);
	
	private Semaphore elfMutex = new Semaphore(3);
	private ReentrantLock mutex = new ReentrantLock();

	
	public void santaChrist(SantaChrist santa)
	{
		try
		{
			santaSem.await();
		} catch (InterruptedException | BrokenBarrierException e)
		{
			e.printStackTrace();
		}
		mutex.lock();
		if (raindeerCounter == 9)
		{
			santa.prepSleigh();
			reindeerSem.reset();
		}
		else if (elfCounter == 3)
		{
			santa.helpElves();
		}
		mutex.unlock();

	}
	
	public void raindeer(Raindeer deer)
	{
		mutex.lock();
		raindeerCounter += 1;
		if (raindeerCounter == 9)
		{
			try
			{
				santaSem.await();
			}
			catch (InterruptedException | BrokenBarrierException e)
			{
				e.printStackTrace();
			}
		}
		mutex.unlock();		
		try
		{
			reindeerSem.await();
		} 
		catch (InterruptedException | BrokenBarrierException e)
		{
			e.printStackTrace();
		}
		deer.getHitched();

	}
	
	public void elves(Elf elf)
	{
		try
		{
			elfMutex.acquire();
		} catch (InterruptedException e1)
		{
			e1.printStackTrace();
		}
		mutex.lock();
		elfCounter += 1;
		if (elfCounter == 3)
		{
			try
			{
				santaSem.await();
			}
			catch (InterruptedException | BrokenBarrierException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			elfMutex.release();
		}
		mutex.unlock();
		elf.getHelp();
		mutex.lock();
		elfCounter -= 1;
		if (elfCounter == 0)
		{
			elfMutex.release();
		}
		mutex.unlock();
	}
	
	public static void main(String[] args)
	{
		NorthPole north = new NorthPole();
		int i = 0;
		for(i = 0; i < 9; i++)
		{
			Raindeer d = new Raindeer(i, north);
			new Thread(d).start();
		}
		
		SantaChrist santa = new SantaChrist(north);
		new Thread(santa).start();
		
		while (true)
		{
			Elf elf = new Elf(i, north);
			new Thread(elf).start();
			
			try
			{
				Thread.sleep(2000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}
