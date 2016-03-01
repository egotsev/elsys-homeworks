package homeworks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeworkTree {
	public static void main(String[] argc){
		List<String> ls = new ArrayList<String>() ;
		for(int i = 0 ; i < argc.length; i++){
			String res = System.getProperty(argc[i]);
			if (res!= null)
				{
					ls.add(res);
				}

		}
		Collections.sort(ls);
		if (argc[argc.length -1].equals("down")){
			Collections.reverse(ls);
		}
		for(String elem : ls){
			System.out.println(elem);
		}
	}

}
