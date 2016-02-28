package org.elsys.first_hm;

import java.util.Arrays;
import java.util.Scanner;

public class SystemPropertiesHomework {

	public static void main(String[] args) {
		System.out.println("Hello World");
		String arr[];
		Scanner sc = new Scanner(System.in);
		while(sc.hasNextLine()){
			int count = 0;
			String line = sc.nextLine();
			line = line.trim();
			String[] tokens = line.split("\\s+");
			for(String token: tokens){
				String property = System.getProperty(token);
				if(property != null){
					count++;
				}
			}
			arr = new String[count];
			int i = 0;
			for(String token: tokens){
				String property = System.getProperty(token);
				if(property != null){
					arr[i] = property;
					i++;
				}
			}						
			if(tokens[tokens.length-1].equals("down")){
				Arrays.sort(arr);
				for(int j = arr.length - 1; j >= 0; j--){
					System.out.println(arr[j]);
				}
			} else {
				Arrays.sort(arr);
				for(int j = 0; j < arr.length; j++){
					System.out.println(arr[j]);
				}
			}
		}
		
		sc.close();

	}

}
