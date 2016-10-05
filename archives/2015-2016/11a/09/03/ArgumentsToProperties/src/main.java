import java.util.*;
import java.lang.System;

public class main {	
	private static List<String> getProperties(String[] arguments){
		List<String> properties = new ArrayList<String>();
		
		for(String arg : arguments){
			String property = System.getProperty(arg);
			
			if(property != null){
				properties.add(property);
			}
		}
		
		return properties;
	}
	
	public static void main(String[] args) {
		List<String> properties = getProperties(args);
		Collections.sort(properties);
		
		if(args.length > 0 && args[args.length - 1].compareTo("down") == 0){
			Collections.reverse(properties);
		}
		
		//Print the properties
		for(String property : properties){
			System.out.println(property);
		}
	}
}