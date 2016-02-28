package org.elsys.third;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
	private static final String UP = "up";
	private static final String DOWN = "down";
	
	private static void print(List<String> properties) {
		int i;
		for(i = 0; i < properties.size(); i++) {
			System.out.println(properties.get(i));
		}
	}
	
	private static void sort(List<String> properties, String sortOption) {
		Collections.sort(properties);
		
		if(sortOption.equals(DOWN)) {
			Collections.reverse(properties);
		}
		
		print(properties);
	}
	
	private static String getProperty(String propertyName) {
		String result = null;
		
		try {
			result = System.getProperty(propertyName);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		return result;
	}

	private static void interpret(InputStream in) {
		
		String sortOption = UP;
		Scanner scanner = new Scanner(in);
		List<String> allProperties = new ArrayList<String>();
		
		while(scanner.hasNextLine()) {
			String inputLine = scanner.nextLine();
			String[] tokens = inputLine.split("\\s+");
			for(String token: tokens) {
				token = token.trim();
				
				if(token.isEmpty()) {
					continue;
				}
				
				String property = getProperty(token);
				if(property != null) {
					allProperties.add(property);
				}
			}
			if(tokens[tokens.length - 1].equals(DOWN)) {
				sortOption = DOWN;
			}
			sort(allProperties, sortOption);
		}
		
		scanner.close();
	}
	
	public static void main(String[] args) {
		
		interpret(System.in);
	}

}
