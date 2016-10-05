import java.util.Collections;
import java.util.Arrays;
public class Homework1 {	  
		public static void main (String [] args) {
		int i = 0;
		int rever = 0;
		for(i = 0; i <= args.length-1; i++)
			if(args[i].equals("down"))
				rever = 1;
			if (rever != 1) {
				Arrays.sort(args); // sorting in ascending order
			} else 
				Arrays.sort(args, Collections.reverseOrder()); // sort in descending order
			for(i = 0; i <= args.length-1; i++) {
					String x;	
					x = System.getProperty(args[i]);
				if(x != null)
					System.out.println(x);
			}
		}
}