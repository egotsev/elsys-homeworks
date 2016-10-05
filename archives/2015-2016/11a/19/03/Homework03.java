import java.util.*;

public class Homework03 {

	public static void main(String[] args) {
		int args_n = 0;
		for(int i = 0; i < args.length; i++){
			if(System.getProperty(args[i]) != null){
				args_n++;
			}
		}

		String[] properties = new String[args_n];
		int j = 0;
		for(int i = 0; i < args.length; i++){
			String property = System.getProperty(args[i]);
			if(property != null){
				properties[j] = property;
				j++;
			} 			
		}
		
		Arrays.sort(properties);

		if(args[args.length-1].equals("down")){
			for (int i = 0; i < properties.length / 2; i++) {
  				String temp = properties[i];
  				properties[i] = properties[properties.length - 1 - i];
  				properties[properties.length - 1 - i] = temp;
			}
		}
		for(int i = 0; i < properties.length; i++){
			if(properties[i]!=null){
				System.out.println(properties[i]);
			}
		}

	}

}
