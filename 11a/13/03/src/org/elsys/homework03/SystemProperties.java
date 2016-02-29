package org.elsys.homework03;

import java.util.ArrayList;

public class SystemProperties {
	public static void main(String args[]) {
		int size = args.length;
		ArrayList<String> property = new ArrayList<String>(size);
		for(String x : args) {
			if (System.getProperty(x) != null) {
				property.add(System.getProperty(x));
			}
		}
		System.out.print(property);
	}
}
