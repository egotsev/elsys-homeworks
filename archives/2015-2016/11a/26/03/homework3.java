package h3;

import java.util.Collections;
import java.util.Vector;

public class homework3 {
	public static void main(String[] argc){
		Vector<String> name = new Vector<String>();
		for (int i = 0; i < argc.length; i++) {
			if (System.getProperty(argc[i]) != null){
				name.add(System.getProperty(argc[i]));
			}
		}
		if (!name.isEmpty()) {
			if(argc[argc.length - 1].equals("down"))
				Collections.sort(name, Collections.reverseOrder());
			else 
				Collections.sort(name);
				
		}
		for(String s : name)
			System.out.println(s);
		
	}
}