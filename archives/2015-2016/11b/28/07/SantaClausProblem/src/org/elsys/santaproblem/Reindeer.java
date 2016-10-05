package org.elsys.santaproblem;

public class Reindeer extends Thing implements Runnable {

	public Reindeer(int id, Main constructor) {
		super(id, constructor);
	}
	
	@Override
	public void run() {
		try {
			getConstructor().reindeerLive(this);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getHitched() {
		System.out.println("Reindeer is getting hitched!");
	}

}
