package org.elsys.santa;

public class Elf implements Runnable{
	private int id;
	
	public Elf(int id){
		this.id = id;
	}
	
	@Override
	public void run(){
		
	}
	
	@Override
	public String toString(){
		return "Elf " + id;
	}
	
	public void getHelp(){
		System.out.println(this + " is being helped");
	}
}
