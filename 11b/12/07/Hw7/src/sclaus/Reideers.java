package sclaus;

public class Reideers extends scproblem{
	public void RaindeerProcces() throws InterruptedException{
		lock.wait();
		reideerCount+=1;
		System.out.println("Reideer returned from vacation");
		if(reideerCount == 9) 
			SantaSem.notify();
			System.out.println("9 reideer waking santa");
		lock.notify();
		ReideerSem.wait(9);
		System.out.println("Reideers Hiched");
	}
}
