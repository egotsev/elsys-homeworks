package homework3;

import java.util.Collections;
import java.util.LinkedList;

public class homework3 
{
	public static void main(String[] args) 
	{
		LinkedList<String> propertyList = new LinkedList<String>();

		for (String element : args) 
		{
			if (System.getProperty(element) != null)
			{
				propertyList.push((System.getProperty(element)));
			}
		}

		if (!propertyList.isEmpty()) 
		{
			if (args[args.length - 1].equals("down"))
			{
				Collections.sort(propertyList, Collections.reverseOrder());
			}
			else
			{
				Collections.sort(propertyList);
			}
		}
		
		for (String element : propertyList) 
		{
			System.out.println(element);
		}
	}
}