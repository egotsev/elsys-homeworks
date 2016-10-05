package org.elsys.rangelov.santaclauseproblem;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Reindeer extends Thread  {
	int id;

	private static Random generator = new Random();
	private final CyclicBarrier allReindeers;
	private final CyclicBarrier sleigh;
	private final Semaphore santasAttention;
	
    Reindeer(int id) { 
    	this.id = id; 
    	}

    public void run() {
    	while(true) {
    			Thread.sleep(900 + generator.nextInt(200));
    			
    			int reindeer = allReindeers.await();
    			
    			h.await();
			
                Thread.sleep(generator.nextInt(20));
                
               
					reindeer = sleigh.await();
			
                if (reindeer == 0) {
                    santasAttention.release();
                    System.out.println("Toys are delivered");
                }
    	}
    }
}