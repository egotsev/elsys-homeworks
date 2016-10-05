package org.elsys.first_hm_s_t;

import java.util.Arrays;

public class PropertiesGetter {

	public static void main(String[] args) {
		int count = 0;
		String arr[];
		for (String token : args) {
			String property = System.getProperty(token);
			if (property != null) {
				count++;
			}
		}
		arr = new String[count];
		int i = 0;
		for (String token : args) {
			String property = System.getProperty(token);
			if (property != null) {
				arr[i] = property;
				i++;
			}
		}
		if ("down".equals(args[args.length - 1])) {
			Arrays.sort(arr);
			for (int j = arr.length - 1; j >= 0; j--) {
				System.out.println(arr[j]);
			}
		} else {
			Arrays.sort(arr);
			for (int j = 0; j < arr.length; j++) {
				System.out.println(arr[j]);
			}
		}

	}

}
