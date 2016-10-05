package org.elsys.rangelov.santaclauseproblem;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Elf extends Thread throws InterruptedException{

	protected final CyclicBarrier threeElves;
	protected final Semaphore queueElves;
	protected final CyclicBarrier elvesAreInspired;
	protected static Random generator = new Random();
	protected final Semaphore santasAttention;
	
	int id;

    Elf(int id) {
    	this.id = id; 
    }
	
	@Override
	public void run(){
		while(true) {
			
			Thread.sleep(generator.nextInt(2000));
			int threeelves = threeElves.await();
			if(threeelves == 3) {
				santasAttention.acquire();
			}
			Thread.sleep(generator.nextInt(500));
            System.out.println("Elf " + id + " got inspiration");
            elvesAreInspired.await();
                    
            if (threeelves == 3)
            	santasAttention.release();
                    
            queueElves.release();
            Thread.sleep(generator.nextInt(2000));
		}
	}
}
