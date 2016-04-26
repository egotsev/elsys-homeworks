package sclaus;

public class Santa extends scproblem {
	public void SantaProcess() throws InterruptedException{
		while(true){
			SantaSem.wait();
			lock.wait();
			if(reideerCount == 9){
				reideerCount = 0;
				System.out.println("Santa is prepearing the Sleigh");
				wait(2);
				ReideerSem.release(9);
			}else{
				ElfSem.release(3);
				System.out.println("Santa helping elves");
			}
			lock.notify();
		}
	}
}
