package otg.elsys.santaclaus;

public class Elf implements Runnable{
	
	private int elfId;
	private SantaClaus santa;
	
	public Elf(int elfId, SantaClaus santa) {
		this.elfId = elfId;
		this.santa = santa;
	}

	@Override
	public void run() {
		System.out.println("Elf " + elfId + " needs help . . .");
		santa.onArrivedElf(this);
	}
	
	public void getHelp() {
		System.out.println("Elf " + elfId + " got helped . . .");
	}

	public String getName() {
		return "Elf " + elfId;
	}
}
