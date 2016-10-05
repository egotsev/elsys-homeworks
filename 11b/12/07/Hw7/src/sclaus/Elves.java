package sclaus;

public class Elves extends scproblem {
	public void ElvesProcces() throws InterruptedException{
		Elflock.wait();
		lock.wait();
			elfCount+=1;
			System.out.println(elfCount+" elves waiting!");
			if(elfCount == 3) SantaSem.notify();
			else Elflock.notify();
		lock.notify();
		ElfSem.wait();
		System.out.println("Santa helping elves");
		lock.wait();
			elfCount-=1;
			System.out.println("Santa helped elf");
			if(elfCount == 0) Elflock.notify();
		lock.notify();
	}
}
