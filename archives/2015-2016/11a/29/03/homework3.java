import java.lang.System;
import java.lang.Collections;

public class JavaHomework {
	public static void main(String[] args) {
		
		List<String> myList = new ArrayList<String>();

		for (int count = 0; count < args.length; count++) {
			if (System.getProperty() != null) {
				myList.add(count);
			}
		}

		if (args[args.length - 1] == "down") {
			Collections.sort(myList, Collections.reverseOrder());
		} else {
			Collections.sort(myList);
		}

		for (int count = 0; count < args.length; count++) {
			System.out.println(count);
		}

	}
}