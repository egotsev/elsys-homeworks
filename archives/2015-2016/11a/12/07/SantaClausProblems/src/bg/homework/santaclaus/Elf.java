package bg.homework.santaclaus;

public class Elf implements Runnable{

	private int id;
	private NorthPole northPole;
	
	public Elf(int id, NorthPole northPole){
		this.id = id;
		this.northPole = northPole;
	}

	
	@Override
	public void run() {
		System.out.println("Elf " + id + " needs help");		
		try {
			northPole.elfNeedHelp(id);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
