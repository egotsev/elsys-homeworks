import java.util.Collections;
import java.util.LinkedList;

public class Homework1 {

	public static void main(String[] args) {

		LinkedList<String> properties = new LinkedList<>();

		for (String x : args) {
			if (System.getProperty(x) != null)
				properties.push((System.getProperty(x)));
		}

		if (!properties.isEmpty()) {
			if ("down".equals(args[args.length - 1]))
				Collections.sort(properties, Collections.reverseOrder());
			else
				Collections.sort(properties);
		}
		for (String x : properties) {
			System.out.println(x);
		}
	}
}