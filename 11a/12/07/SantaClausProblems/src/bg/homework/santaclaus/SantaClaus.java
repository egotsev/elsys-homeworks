package bg.homework.santaclaus;

public class SantaClaus implements Runnable{
	private NorthPole northPole;
	
	public SantaClaus(NorthPole northPole) {
		this.northPole = northPole;
	}
	
	public void run(){
		System.out.println("Santa is alive and sleeping");
		try {
			northPole.santaClaus();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
