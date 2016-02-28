package org.elsys.sysprops;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class SysProperties {
	private static String GetProperty(String property){
		String result = "";
		try{
			result = System.getProperty(property);
		}catch(SecurityException se){
			System.err.println("SecurityException: " + se.getMessage());
		}catch(NullPointerException ne){
			System.err.println("NullPointerException: " + ne.getMessage());
		}catch(IllegalArgumentException iae){
			System.err.println("IllegalArguementException: " + iae.getMessage());
		}
		return (result!=null && !result.isEmpty()) ? result:null;
	}

	private static void sortAndPrint(String op, String[] arr) {
		if(op.equals("down")) {
			Arrays.sort(arr, Collections.reverseOrder());
		}else{
			Arrays.sort(arr);
		}
		for(int i = 0; i < arr.length; ++i){
			System.out.println(arr[i]);
		}
	}
	
	private static String[] ListToArray(ArrayList<String> arrlist) {
		while(arrlist.contains(null)) {
			arrlist.remove(null);
		}
		String[] str = new String[arrlist.size()];
		str = arrlist.toArray(str);
		return str;
	}
	
	public static void main(String[] args) {
		int checkSize = args.length;
		if(checkSize > 0) {
			String option = "up";
			ArrayList<String> strList = new ArrayList<String>();
			if(args[checkSize-1].equals("up") || args[checkSize-1].equals("down")){
				checkSize--;
				option = args[checkSize];
			}
			for(int i = 0; i < checkSize; ++i){
				strList.add(GetProperty(args[i]));
			}
			sortAndPrint(option, ListToArray(strList));
		}
	}

}
