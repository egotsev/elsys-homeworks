package org.elsys.santa;


public class SantaChrist implements Runnable
{
	private NorthPole north;
	
	public SantaChrist(NorthPole north)
	{
		this.north = north;
	}
	
	@Override
	public void run()
	{
		try
		{
			Thread.sleep(5000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		System.out.println("Santa Christ is sleeping...");
		north.santaChrist(this);
	}
	
	public void prepSleigh()
	{
		System.out.println("Santa Christ is preparing the sleigh for Exmas!");
	}
	
	public void helpElves()
	{
		System.out.println("Santa christ is helping the elves...");
	}

}
