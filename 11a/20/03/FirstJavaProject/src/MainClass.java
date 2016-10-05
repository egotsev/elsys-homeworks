import java.util.Scanner;
public class MainClass 
{
	public static void main(String[] args)
	{
		String[] output = new String[args.length];
		int length = 0;
		boolean direction = true;
		for(int i = 0; i < args.length; i++)
		{
			output[i] = (System.getProperty(args[i]));
		}
		if(args[args.length - 1].equals("up"))
		{
			direction = true;
			length = args.length - 1;
		}
		else if(args[args.length - 1].equals("down"))
		{
			direction = false;

			length = args.length - 1;
		}
		else 
		{
			direction = true;
			length = args.length;
		}
		if(direction)
		{
			for(int i = 0; i < length; i++)
			{
				if(output[i] != null)
				System.out.println(output[i]);
			}
		}
		else 
		{
			for(int i = length - 1; i >= 0; i--)
			{
				if(output[i] != null)
				System.out.println(output[i]);
			}
		}
	}
}
