package homework;

public class Arguments {

	public static void main(String[] args)
	{
		int i = 0;
		String[] argProperties2 = new String[args.length];
		String last = null;
		for (String arg : args)
		{
			argProperties2[i] = System.getProperty(arg);
			i++;
			last = arg;
		}

		if (last.equals("down"))
		{
			for(i=argProperties2.length-1; i>0;i--)
			{
				if (argProperties2[i]!= null)
				System.out.println(argProperties2[i]);
			}
		}
		else
		{
			for(i=0; i<argProperties2.length-1;i++)
			{
				if (argProperties2[i]!= null)
				System.out.println(argProperties2[i]);
			}
		}
	}
}
