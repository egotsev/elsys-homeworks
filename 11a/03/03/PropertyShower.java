package rokn;
import java.util.Vector;

public class PropertyShower 
{
	public static void main(String[] args)
	{
		Vector<String> vector = new Vector<String>();
		
		for(int i = 0; i < args.length; ++i)
		{
			String prop = System.getProperty(args[i]);
			
			if (prop != null)
			{
				vector.add(prop);
			}
		}
		
		String last = args[args.length - 1];
		boolean ascending = last.compareTo("down") != 0;
		
		for(int i = 0; i < vector.size(); ++i)
		{
			String out = (ascending) ?
					vector.elementAt(i) :
					vector.elementAt((vector.size() - i) - 1); 
			System.out.println(out);
		}
		
	}
}
