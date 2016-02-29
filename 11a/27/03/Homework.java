package org.elsys.homework1;

public class Homework {
	public static void main(String[] args) {
		if(args[args.length-1].equals("down")) {
			for(int i = args.length-1; i >=0; i--) {
				String properties = System.getProperty(args[i]);
				if(properties != null) {
					System.out.println(properties);
				}
				
			}
		}
		else {
			for(int i = 0; i < args.length; i++) {
				String properties = System.getProperty(args[i]);
				if(properties != null) {
					System.out.println(properties);
				}
			}
		} 
	}
}
