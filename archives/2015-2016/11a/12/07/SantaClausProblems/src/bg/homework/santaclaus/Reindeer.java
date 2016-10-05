package bg.homework.santaclaus;

public class Reindeer implements Runnable{

	private int id;
	private NorthPole northPole;
	
	public Reindeer(int id, NorthPole northPole){
		this.id = id;
		this.northPole = northPole;
	}
	
	@Override
	public void run() {
		System.out.println("Reindeer " + id + " in the house");
	
		try {
			northPole.reindeerArriving(id);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public String getFullName(){
		return "Reindeer";
	}

}
