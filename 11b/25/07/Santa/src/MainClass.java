import java.util.Random;
import java.util.concurrent.Semaphore;

public class MainClass {
	public static int elf = 0;
	public static int reindeer = 0;
	
	public static Semaphore santaSem = new Semaphore(0);
	public static Semaphore elfSem = new Semaphore(0);
	public static Semaphore reindeerSem = new Semaphore(0);
	public static Semaphore doorSem = new Semaphore(3);
	public static Object lock = new Object();

	static Random rdm = new Random();


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int createdElfs = 0;
		Santa santa = new Santa();
		int reindeers = 0;
		new Thread(santa).start();
		
		while(!santa.hasPreppedSleigh){
			if (rdm.nextInt(2) == 0) {
				createdElfs++;
				Elf e = new Elf(createdElfs);
				new Thread(e).start();
			}
			else{
				reindeers++;
				Reindeer r = new Reindeer(reindeers);
				new Thread(r).start();

			}
			try {
					Thread.sleep(1000);
			} catch (InterruptedException e) {
					e.printStackTrace();
			}
		}

	}

}
