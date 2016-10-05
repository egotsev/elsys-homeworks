package org.elsys.santaproblem;

public class Elf extends Thing implements Runnable {

	public Elf(int id, Main constructor) {
		super(id, constructor);
	}
	
	@Override
	public void run() {
		try {
			getConstructor().elfLive(this);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getHelp() {
		System.out.println("Santa helps elves!");
	}

}
