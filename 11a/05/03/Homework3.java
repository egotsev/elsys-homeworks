import java.util.Collections;
import java.util.LinkedList;

public class Homework1 {

	public static void main(String[] args) {

		LinkedList<String> properties = new LinkedList<String>();

		for (String x : args) {
			if (System.getProperty(x) != null)
				properties.push((System.getProperty(x)));
		}

		if (!properties.isEmpty()) {
			if (args[args.length - 1].equals("down"))
				Collections.sort(properties, Collections.reverseOrder());
			else
				Collections.sort(properties);
		}
		for (String x : properties) {
			System.out.println(x);
		}
	}
}
