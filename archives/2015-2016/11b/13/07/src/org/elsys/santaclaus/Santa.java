package org.elsys.santaclaus;

public class Santa extends FactoryEntity implements Runnable {

	public Santa(int santaId, ToyFactory factory) {
		super(santaId, factory);
	}
	
	@Override
	public void run() {
		try {
			getFactory().handleSanta(this);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getEntityName() {
		return "Santa";
	}

	public void prepSleigh() {
		System.out.println(this + " is preparing the sleigh...");
	}

	public void helpElves() {
		System.out.println(this + " is helping the elves...");
	}
}