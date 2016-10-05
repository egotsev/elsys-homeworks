package org.elsys.kalin;

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
	
    Reindeer(int id) { this.id = id; }

    public void run() {
    	while(true) {
    		try {
    			Thread.sleep(900 + generator.nextInt(200));
    			
    			int reindeer = allReindeers.await();
    			
    			try {
					sleigh.await();
				} catch (BrokenBarrierException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                Thread.sleep(generator.nextInt(20));
                
                try {
					reindeer = sleigh.await();
				} catch (BrokenBarrierException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                if (reindeer == 0) {
                    santasAttention.release();
                    System.out.println("=== Toys are delivered ===");
                }
    		} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BrokenBarrierException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
    	}
    }
}