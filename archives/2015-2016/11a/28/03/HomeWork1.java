import java.lang.System;
import java.util.Collections;
import java.util.Vector;

public class HomeWork1
{
	public static void main(String[] args)
	{
		Vector<String> collect = new Vector<String>();
		for(int i=0; i<args.length; ++i)
		{
			String res = System.getProperty(args[i]);
			collect.add(res);
		}
		
		if(args[args.length - 1] == "down")
		{
			Collections.sort(collect, Collections.reverseOrder());
		}else{
			Collections.sort(collect);
		}
		
		for(int i = 0; i < collect.size(); ++i)
		{
			System.out.println(collect.elementAt(i));
		}
	}
}
