package org.homework;

import java.util.concurrent.Semaphore;

public class Shared {
	public static int elfCount = 0;
	public static int reindeerCount = 0;
	
	public static Semaphore reindeerSem = new Semaphore(0);
	public static Semaphore elfSem = new Semaphore(0);
	public static Semaphore santaSem = new Semaphore(0);
	
	public static Semaphore counterMutex = new Semaphore(1);
	public static Semaphore elfMutex = new Semaphore(1);
}
