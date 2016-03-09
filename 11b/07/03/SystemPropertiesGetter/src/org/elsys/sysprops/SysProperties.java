package org.elsys.sysprops;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class SysProperties {
	private static final String UP = "up";
	private static final String DOWN = "down";

	private static String getProperty(String property) {
		String result = "";
		try {
			result = System.getProperty(property);
		} catch (SecurityException se) {
			System.err.println("SecurityException: " + se.getMessage());
		} catch (NullPointerException ne) {
			System.err.println("NullPointerException: " + ne.getMessage());
		} catch (IllegalArgumentException iae) {
			System.err.println("IllegalArguementException: " + iae.getMessage());
		}
		return (result != null && !result.isEmpty()) ? result : null;
	}

	private static void sortAndPrint(String op, String[] arr) {
		if (DOWN.equals(op)) {
			Arrays.sort(arr, Collections.reverseOrder());
		} else {
			Arrays.sort(arr);
		}
		for (int i = 0; i < arr.length; ++i) {
			System.out.println(arr[i]);
		}
	}

	private static String[] listToStringArray(ArrayList<String> arrlist) {
		while (arrlist.contains(null)) {
			arrlist.remove(null);
		}
		String[] str = new String[arrlist.size()];
		str = arrlist.toArray(str);
		return str;
	}

	public static void main(String[] args) {
		int checkSize = args.length;
		if (checkSize > 0) {
			String option = UP;
			ArrayList<String> strList = new ArrayList<>();
			if (UP.equals(args[checkSize - 1]) || DOWN.equals(args[checkSize - 1])) {
				checkSize--;
				option = args[checkSize];
			}
			for (int i = 0; i < checkSize; ++i) {
				strList.add(getProperty(args[i]));
			}
			sortAndPrint(option, listToStringArray(strList));
		}
	}

}
