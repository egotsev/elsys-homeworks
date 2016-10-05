package org.elsys.santa;

public class MainClass {

	public static void main(String[] args) {
		SantaHut hut = new SantaHut();
		for (int i = 0; i < 9; i++) {
			Reindeers p = new Reindeers(i,hut);
			new Thread(p).start();
		}
		Santa santa = new Santa(hut);
		new Thread(santa).start();
		int numberOfElf = 0;
		while(true)
		{
			Elf n = new Elf(numberOfElf,hut);
			new Thread(n).start();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			numberOfElf++;
		}
	}

}
