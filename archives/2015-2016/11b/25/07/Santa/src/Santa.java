
public class Santa implements Runnable {
	public boolean hasPreppedSleigh;
	
	public Santa() {
		hasPreppedSleigh = false;
	}

	@Override
	public void run() {
		while(!hasPreppedSleigh){
			try {
				System.out.println("Santa is sleeping.");
				MainClass.santaSem.acquire();
				System.out.println("Santa woke up.");
				
				synchronized(MainClass.lock){
					if (MainClass.reindeer == 9) {
						MainClass.reindeer = 0;
						hasPreppedSleigh = true;
						System.out.println("Santa is prepping the sleigh.");

						MainClass.reindeerSem.release(9);
					} else {
						MainClass.elfSem.release(3);
						System.out.println("Santa is helping the elves.");
					}
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}

}
