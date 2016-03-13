import java.util.NavigableMap;
import java.util.TreeMap;

public class homeworkNo3 
{
	public static void main(String[] args) 
	{
		TreeMap<String, String> properties = new TreeMap<String, String>();
		String property;
		int i = 0;
		
		while (i <= (args.length -1))
		{
			property = System.getProperty(args[i]);
					
			if (property != null)
			{
				properties.put(args[i], property);
			}
			
			i++;
		}
		
		if (args[args.length - 1].equals("up") || !args[args.length - 1].equals("down"))
		{
			NavigableMap<String, String> properties2 = properties.descendingMap();
			
			for (String element : properties2.keySet())
			{
				System.out.println(properties2.get(element));
			}
		} 
		
		if (args[args.length - 1].equals("down"))
		{
			for (String element : properties.keySet())
			{
				System.out.println(properties.get(element));
			}
		}
	}
}
