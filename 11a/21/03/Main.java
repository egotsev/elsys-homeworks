import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Main {
	private static void printArray(List<String> arr){
		for(String el : arr){
			System.out.println(el);
		}
	}
	
	private static List<String> getProperties(String[] args){
		List<String> properties = new ArrayList<String>();
		
		for(String arg : args){
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
		
		printArray(properties);
	}
}