package org.elsys.hm3;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class displayProperty {

	public static void main(String[] args) {
		List<String> forDisplay = new ArrayList<String>();
		for (String s: args){
			String curProp = System.getProperty(s);
			if (curProp != null){
				forDisplay.add(curProp);
			}
		}
		String sortOrder = args[args.length-1];
		if (sortOrder == "down"){
			Collections.sort(forDisplay);
		} else {
			Collections.sort(forDisplay, Collections.reverseOrder());
		}
		System.out.println(forDisplay);
	}
}