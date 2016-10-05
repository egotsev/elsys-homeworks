package org.elsys.homeworks;

import java.util.List;

public class Main {
	public static void main(String[] args) {
		SystemPropertiesFetcher fetcher = new SystemPropertiesFetcher();
		List<String> properties = fetcher.fetch(args, args[args.length - 1]);
		Main.displayProperties(properties);
	}
	
	private static void displayProperties(List<String> properties) {
		for (String prop : properties) {
			System.out.println(prop);
		}
	}
}