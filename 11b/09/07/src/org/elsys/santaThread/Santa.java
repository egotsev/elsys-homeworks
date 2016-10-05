package org.elsys.santaThread;

public class Santa extends Pole implements Runnable {

	public Santa(int pId, SantaClausThreads constructor) {
		super(pId, constructor);
	}

	@Override
	public void run() {
		while (true) {
			System.out.println(this + " is sleeping");
			try {
				getConstructor().Santa();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public String getFullName() {
		return "Santa";
	}

}
