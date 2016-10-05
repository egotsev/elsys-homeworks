
public class Reindeer implements Runnable{
	int id;
	public Reindeer(int id){
		this.id = id;
	}
	public String getId(){
		return "Reindeer" + id;
	}
	@Override
	public void run() {
		try {
			synchronized(MainClass.lock) {
				System.out.println(this + " has returned.");
				MainClass.reindeer++;

				if (MainClass.reindeer == 9) {
					MainClass.santaSem.release();
				}
			}
			
			MainClass.reindeerSem.acquire();
			System.out.println(this + " is getting hitched.");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
