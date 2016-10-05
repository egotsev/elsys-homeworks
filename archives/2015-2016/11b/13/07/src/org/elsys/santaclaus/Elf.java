package org.elsys.santaclaus;

public class Elf extends FactoryEntity implements Runnable {

	public Elf(int elfId, ToyFactory factory) {
		super(elfId, factory);
	}
	
	@Override
	public void run() {
		try {
			getFactory().handleElf(this);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getEntityName() {
		return "Elf";
	}

	public void getHelp() {
		System.out.println(this + " is getting help...");
	}
}