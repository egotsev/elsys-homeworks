package org.elsys.christmas;

public class Elf implements Runnable {
	
	private int id;

	public Elf(int id) {
		// TODO Auto-generated constructor stub
		this.id = id;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			System.out.println("Elf " + id + " tries to enter");
			SantaWorkshop.door.acquire();
			System.out.println("Elf " + id + " entered");
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		SantaWorkshop.lock.lock();
		try {
			SantaWorkshop.elfCount++;
			if(SantaWorkshop.elfCount == 3) {
				SantaWorkshop.santaSem.release();
			}
		} finally {
			SantaWorkshop.lock.unlock();
		}
		try {
			SantaWorkshop.elfSem.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		getHelp();
		SantaWorkshop.lock.lock();
		try {
			SantaWorkshop.elfCount--;
			System.out.println("Elf " + id + " is leaving!");
			SantaWorkshop.door.release();
		} finally {
			SantaWorkshop.lock.unlock();
		}
	}
	
	public void getHelp() {
		System.out.println("Elf " + id + " is getting help from Santa!");
	}

}
