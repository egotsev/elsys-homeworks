package org.elsys.santaproblem;

public class Santa extends Thing implements Runnable {
	
	private boolean prepared = false;
	
	public Santa(int id, Main constructor) {
		super(id, constructor);
	}
	
	public boolean isPrepared() {
		return prepared;
	}
	
	@Override
	public void run() {
		while(!isPrepared()) {
			try {
				getConstructor().santaLive(this);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		}
	}
	
	public void prepareSleight() {
		prepared = true;
		System.out.println("Sleight prepared!");
	}
	
	public void helpElves() {
		System.out.println("Santa is helping the elves!");
	}

}
