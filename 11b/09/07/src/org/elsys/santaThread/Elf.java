package org.elsys.santaThread;

public class Elf extends Pole implements Runnable {
	public Elf(int Id, SantaClausThreads constructor) {
		super(Id, constructor);
	}

	@Override
	public void run() {
		System.out.println(this + " needs help.");
		try {
			getConstructor().Elves();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getFullName() {
		return "Elf";
	}
}
