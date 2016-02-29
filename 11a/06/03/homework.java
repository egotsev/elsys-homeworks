import java.util.HashMap;

public class homeworkNo3 
{
	public static void main(String[] args) 
	{
		HashMap<String, String> properties = new HashMap<String, String>();
		String property;
		int i = 0;
		
		while (i < (args.length -1))
		{
			property = System.getProperty(args[i]);
					
			if (property != null)
			{
				properties.put(args[i], property);
			}
			
			i++;
		}
		
		if (args[args.length - 1] == "up")
		{
			for (String element : properties.keySet())
			{
				System.out.println(element + properties.get(element));
			};
		}
	}
}
