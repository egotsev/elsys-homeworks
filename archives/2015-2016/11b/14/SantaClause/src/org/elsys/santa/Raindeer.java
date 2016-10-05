package org.elsys.santa;

public class Raindeer implements Runnable
{
	private int id;
	private NorthPole north;
	
	public Raindeer(int deerID, NorthPole north)
	{
		this.id = deerID;
		this.north = north;
	}

	@Override
	public void run()
	{
		while(true)
		{
			try
			{
				Thread.sleep(5000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			System.out.println("Raindeer " + (id + 1) + " is back from holiday!");
			north.raindeer(this);
		}
	}
	
	public void getHitched()
	{
		System.out.println("Raindeer " + (id + 1) + " hitched...");
	}

}
