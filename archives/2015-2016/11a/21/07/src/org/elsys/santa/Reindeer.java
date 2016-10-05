package org.elsys.santa;

public class Reindeer implements Runnable {
	private int id;
	
	public Reindeer(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "Reindeer " + id;
	}
	
	private void getHitched() {
		System.out.println(this + " is getting hitched.");
	}
	
	@Override
	public void run() {
		try {
			synchronized(Main.lock) {
				System.out.println(this + " has returned.");
				Main.reindeerCount++;

				if (Main.reindeerCount == 9) {
					Main.santaSem.release();
				}
			}
			
			Main.reindeerSem.acquire();
			getHitched();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
