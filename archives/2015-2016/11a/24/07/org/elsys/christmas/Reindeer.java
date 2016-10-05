package org.elsys.christmas;

public class Reindeer implements Runnable {
	
	private int id;

	public Reindeer(int id) {
		// TODO Auto-generated constructor stub
		this.id = id;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		SantaWorkshop.lock.lock();
		try {
			SantaWorkshop.reindeerCount++;
			System.out.println("Reindeer " + id + " has returned!");
			if(SantaWorkshop.reindeerCount == 9) {
				SantaWorkshop.santaSem.release();
			}
		} finally {
			SantaWorkshop.lock.unlock();
		}
		try {
			SantaWorkshop.reindeerSem.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getHitched();
	}

	public void getHitched() {
		System.out.println("Reindeer " + id + " is getting hitched");
	}
}
