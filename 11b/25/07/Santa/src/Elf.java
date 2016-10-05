
public class Elf implements Runnable{
	int id;
	
	public Elf(int id){
		this.id = id;
	}
	
	public String getId(){
		return "Elf" + id;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			System.out.println(this + " tries to enter.");
			MainClass.doorSem.acquire();
			System.out.println(this + " has entered.");
			synchronized(MainClass.lock){
				MainClass.elf++;
				System.out.println(MainClass.elf + "elfs waitng");
				if(MainClass.elf == 3){
					MainClass.santaSem.release();
				}
			}
			
			MainClass.elfSem.acquire();
			System.out.println(this + " is getting help.");

			synchronized(MainClass.lock) {
						System.out.println(this + " is leaving.");
						MainClass.elf--;
						MainClass.doorSem.release();
			}
		
		
		}
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
