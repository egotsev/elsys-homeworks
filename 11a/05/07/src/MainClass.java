import java.util.ArrayList;

public class MainClass {

	public static void main(String[] args) {
		new SantaClaus().start();
		ArrayList<Thread> threads = new ArrayList<Thread>();
        for (int i = 0; i < 20; ++i)
            threads.add(new Thread(new Elf(i + 1)));
        for (int i = 0; i < 9; ++i)
            threads.add(new Thread(new Reindeer(i + 1)));
        for (Thread t : threads)
            t.start();

	}

}
