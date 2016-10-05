package org.elsys.homework3;

import java.lang.System;
import java.util.ArrayList;
import java.util.Collections;

public class Lubomir_18_B {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		ArrayList<String> mlist = new ArrayList<String>();
			
		for (int i = 0; i < args.length; i++) {
			if (!args[i].equals("up") && !args[i].equals("down")) {
				if (System.getProperty(args[i]) != null) {
					mlist.add(System.getProperty(args[i]));
				}
			}
		}
		
		if (args[args.length - 1].equals("down")) {
			Collections.sort(mlist, Collections.reverseOrder());
		}else {
			Collections.sort(mlist);
		}
		
		for (String item : mlist) {
			System.out.println(item);
		}
	}
}
