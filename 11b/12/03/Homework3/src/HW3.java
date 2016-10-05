import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HW3 {

	public static void main(String[] args) {
		List<String> properties = new ArrayList<String>();
		for(String argument : args){
			String property = System.getProperty(argument);
			if(null != property)
				properties.add(property);
		}
		Collections.sort(properties);
		if("down".equals(args[args.length-1])){
			Collections.reverse(properties);
		}
		for (String string : properties) {
			System.out.println(string);
		}
	}
}
