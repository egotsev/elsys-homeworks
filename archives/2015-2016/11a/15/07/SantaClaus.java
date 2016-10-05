package org.elsys.kalin;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class SantaClaus {

	int elves = 0;
	int reindeers = 0;
	
	private static Random generator = new Random();
	
	protected static Semaphore Elves = new Semaphore(1);
	protected static CyclicBarrier sleigh = new CyclicBarrier(9);
	
	while(true) {
		System.out.println("Santa is sleeping");
		if(reindeers == 9) {
			System.out.println("Santa will help go");
		}
		else {
			System.out.println("The elves are ready");
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public void start() {
		// TODO Auto-generated method stub
		
	}
	}
}