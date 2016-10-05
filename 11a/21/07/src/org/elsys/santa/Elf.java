package org.elsys.santa;

public class Elf implements Runnable {
	private int id;
	
	public Elf(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "Elf " + id;
	}

	private void getHelp() {
		System.out.println(this + " is getting help.");
	}
	
	@Override
	public void run() {
		try {
			System.out.println(this + " tries to enter.");
			Main.doorSem.acquire();
			System.out.println(this + " has entered.");
			
			synchronized(Main.lock) {
				Main.elfCount++;
				System.out.println("Elfs waiting: " + Main.elfCount);
				
				if(Main.elfCount == 3) {
					Main.santaSem.release();
				}
			}
			
			Main.elfSem.acquire();
			getHelp();
			
			synchronized(Main.lock) {
				System.out.println(this + " is leaving.");
				Main.elfCount--;
				Main.doorSem.release();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
