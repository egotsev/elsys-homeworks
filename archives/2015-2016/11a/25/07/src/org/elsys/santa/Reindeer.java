package org.elsys.santa;

public class Reindeer implements Runnable {
	private int id;
	
	public Reindeer(int id){
		this.id = id;
	}
	
	@Override
	public void run(){
				
	}
	
	@Override
	public String toString(){
		return "Reindeer " + id;
	}
	
	public void getHitched(){
		
	}
	
	
}
