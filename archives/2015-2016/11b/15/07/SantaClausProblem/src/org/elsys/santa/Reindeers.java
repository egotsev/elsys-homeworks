package org.elsys.santa;

public class Reindeers implements Runnable {

	private final int reindeerNumber;
	private final SantaHut hut;
	
	public Reindeers(int reindeerNumber, SantaHut hut) {
		this.reindeerNumber = reindeerNumber;
		this.hut = hut;
	}
	
	@Override
	public void run() {
		while(true)
		{
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			comingBackFromHoliday();
			hut.reindeersPlace(this);
		}
	}

	private void comingBackFromHoliday() {
		System.out.println("Number : " + reindeerNumber + " came back from holiday!");
		
	}

	public void getHitched()
	{
		System.out.println("Got hitched!Number: " + reindeerNumber + " Yeah!");
	}
}
