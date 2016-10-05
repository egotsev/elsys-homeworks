package org.elsys.santa;

public class Elf implements Runnable
{
	private int id;
	private NorthPole north;
	
	public Elf(int id, NorthPole north)
	{
		this.id = id;
		this.north = north;
	}

	@Override
	public void run()
	{
		System.out.println("Elf " + (id + 1) + " is working");
		north.elves(this);
	}
	
	public void getHelp()
	{
		System.out.println("Elf " + (id + 1) + " getting help from SantaChrist...");
	}
}
