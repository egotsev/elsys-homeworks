package org.elsys.santaThread;

import java.util.Random;

public class Reindeer extends Pole implements Runnable {

	public Reindeer(int atomId, SantaClausThreads constructor) {
		super(atomId, constructor);
	}

	@Override
	public void run() {
		while (true) {
			System.out.println(this + " has returned from vacancy");
			try {
				getConstructor().Reindeer();
			} catch (Exception e) {
				e.printStackTrace();
			}
			Random random = new Random();
			try {
				Thread.sleep(random.nextInt(1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public String getFullName() {
		return "Reindeer";
	}

}
