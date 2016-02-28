package org.elsys.sys.prop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ShowProperties {

	public static boolean isEmpty(String[] arr) {
		return arr.length == 0;
	}
	
	public static class MyComparator implements Comparator<String> {
		public int compare(String a1, String a2) {
			return a2.compareTo(a1);
		}
	}
	
	public static void main(String[] args) {
		if(!isEmpty(args)) {
			int size = args.length;
			List<String> propArray = new ArrayList<String>(size);
			boolean descending = args[size-1].equals("down");
			for(int counter = 0; counter < size; counter++) {
				if(System.getProperty(args[counter]) != null) {
					propArray.add(System.getProperty(args[counter]));
				}
			}
			if(descending) {
				Collections.sort(propArray, new MyComparator());
			}
			else {
				Collections.sort(propArray);
			}
			System.out.println(propArray);
		}
		else {
			System.out.println("Please input some arguments!");
		}
	}

}
