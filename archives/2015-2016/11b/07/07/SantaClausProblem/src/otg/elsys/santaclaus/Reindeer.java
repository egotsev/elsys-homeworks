package otg.elsys.santaclaus;

public class Reindeer implements Runnable{
	
	private int reindeerId;
	private SantaClaus santa;
	
	public Reindeer(int reindeerId, SantaClaus santa) {
		this.reindeerId = reindeerId;
		this.santa = santa;
	}

	@Override
	public void run() {
		System.out.println("Reindeer " + reindeerId + " arrived . . .");
		santa.onArrivedReindeer(this);
	}
	
	public void getHitched() {
		System.out.println("Reinder " + reindeerId + " is hitched and ready . . .");
	}
	
	public String getName() {
		return "Reindeer " + reindeerId;
	}

}
