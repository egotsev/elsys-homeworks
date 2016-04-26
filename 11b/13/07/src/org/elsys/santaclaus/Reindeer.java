package org.elsys.santaclaus;

public class Reindeer extends FactoryEntity implements Runnable {

	public Reindeer(int reindeerId, ToyFactory factory) {
		super(reindeerId, factory);
	}
	
	@Override
	public void run() {
		try {
			getFactory().handleReindeer(this);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getEntityName() {
		return "Reindeer";
	}

	public void getHitched() {
		System.out.println(this + " is getting hitched to the sleigh...");
	}
}