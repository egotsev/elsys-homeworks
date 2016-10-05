import java.util.ArrayList;
import java.util.Collections;

public class Main {
	private static final String ASCENDING_COMMAND = "up";
	private static final String DESCENDING_COMMAND = "down";
	
	public static void main(String[] args) {
		String sortOrder = args[args.length - 1];
		if (!sortOrder.equals(DESCENDING_COMMAND) && !sortOrder.equals(ASCENDING_COMMAND)) {
			sortOrder = null;
		}
		
		ArrayList<String> result = new ArrayList<String>();
		for(int i = 0; i < args.length - (sortOrder == null ? 0 : 1); i++) {
			String currentProperty = System.getProperty(args[i]);
			if (currentProperty != null) {
				result.add(currentProperty);
			}
		}
		
		if ((sortOrder != null) && sortOrder.equals(DESCENDING_COMMAND)) {
			Collections.sort(result, Collections.reverseOrder());
		} else {
			Collections.sort(result);
		}

		System.out.println(String.join("\n", result));
	}
}
