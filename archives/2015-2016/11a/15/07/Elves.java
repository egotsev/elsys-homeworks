package org.elsys.kalin;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Elves extends Thread {

	protected final CyclicBarrier threeElves;
	protected final Semaphore queueElves;
	protected final CyclicBarrier elvesAreInspired;
	protected static Random generator = new Random();
	protected final Semaphore santasAttention;
	
	int id;

    Elves(int id) {
    	this.id = id; 
    }
	
	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(generator.nextInt(2000));
				
				try {
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
				} catch (BrokenBarrierException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}