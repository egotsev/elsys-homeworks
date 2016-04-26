package org.homework;

public class Elf extends Thread {
	
	private int id;

	public Elf(int id) {
		this.id = id;
	}
	
	@Override
	public void run() {
		System.out.println("Elf #" + id + " has come.");
		try {
			Shared.elfMutex.acquire();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			Shared.counterMutex.acquire();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		Shared.elfCount++;
		if (Shared.elfCount == 3) {
			Shared.santaSem.release();
		} else {
			Shared.elfMutex.release();
		}
		Shared.counterMutex.release();
		try {
			Shared.elfSem.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		getHelp();
		
		Shared.elfCount--;
		if (Shared.elfCount == 0) {
			Shared.elfMutex.release();
		}
		Shared.counterMutex.release();
	}
	
	public void getHelp() {
		System.out.println("Elf #" + id + " is getting help");
	}
}
