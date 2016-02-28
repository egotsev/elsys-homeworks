package org.elsys.sys.prop;

public class ShowProperties {

	public static boolean isEmpty(String[] arr) {
		return arr.length == 0;
	}
	
	
	public static void main(String[] args) {
		if(!isEmpty(args)) {
			int size = args.length;
			boolean descending = args[size-1].equals("down");
			for(int counter = 0; counter < size; counter++) {
				if(descending) {
					if(System.getProperty(args[size-counter-1]) != null) {
						System.out.println(System.getProperty(args[size-counter-1]));
					}
				}
				else {
					if(System.getProperty(args[counter]) != null) {
						System.out.println(System.getProperty(args[counter]));
					}
				}
			}
		}
		else {
			System.out.println("Please input some arguments!");
		}
	}

}
