package sclaus;

import java.util.concurrent.Semaphore;

public class scproblem {
	int elfCount = 0;
	int reideerCount = 0;
	
	Semaphore SantaSem;
	Semaphore ElfSem;
	Semaphore ReideerSem;
	
	static Object lock = new Object();
	static Object Elflock = new Object();
	
	public static void main(String[] args) {
		
	}
}
