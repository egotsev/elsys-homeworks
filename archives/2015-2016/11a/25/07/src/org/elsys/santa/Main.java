package org.elsys.santa;

import java.util.concurrent.Semaphore;

public class Main {
	public static int elf = 0;
	public static int reindeer = 0;
	
	public static Object lock = new Object();
	
	public static Semaphore elfSem = new Semaphore(0);
	public static Semaphore reindeerSem = new Semaphore(0);
	public static Semaphore santaSem = new Semaphore(0);
	
	public static void main(String[] args) {
		while(true){
			
		}
	}

}
