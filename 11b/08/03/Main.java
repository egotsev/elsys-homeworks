import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {

		List<String> result = new ArrayList<String>();
		String element = "";
		
		for(int counter = 0; counter < args.length; counter++) {
			element = System.getProperty(args[counter]);
			
			if(element != null ){
				result.add(element);
			}
		}
		
		Collections.sort(result);
		
		if(args[args.length - 1].compareTo("down") == 0) {
			Collections.reverse(result);
		}
		
		for(String elements : result){
			System.out.println(elements);
		}
	}
}