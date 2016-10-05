package org.elsys.santa;

public class Elf implements Runnable {

	private final int elfNumber;
	private final SantaHut hut;
	
	public Elf(int elfNumber, SantaHut hut) {
		this.elfNumber = elfNumber;
		this.hut = hut;
	}
	
	@Override
	public void run() {
		working();
		hut.elfsPlace(this);
	}
	
	public void getHelp() {
		System.out.println("Yey, got help!");
	}
	
	public void working()
	{
		System.out.println("Working elf " + elfNumber);
	}
}
