package org.elsys.santa;

public class Santa implements Runnable{
	
	private final SantaHut hut;
	
	public Santa(SantaHut hut) {
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
			sleeping();
			hut.santasPlace(this);
		}
		
	}
	
	public void prepSleight()
	{
		System.out.println("Preparing Sleight... Christmas time!!");
	}

	public void helpElves() 
	{
		System.out.println("Helping the little guys!");	
	}

	public void sleeping()
	{
		
		System.out.println("Sleeping. Zzz...");	
	}
}
