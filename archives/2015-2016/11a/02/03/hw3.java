

import java.lang.System;


	public static void main(String args[])
	{
		String stuff[] = new String[args.length];
		for(int i = 0; i < args.length; i++)
		{
			stuff[i] = args[i];
		}
		
		 if(stuff[stuff.length - 1] == "down")
		{
			for(int i = stuff.length - 1; i >= 0; i++)
			{
				if(System.getproperty(stuff[i]) != null)
				{
					System.out.println(System.getproperty(stuff[i]));
				}
			}
			
		else if(stuff[stuff.length - 1] == "up")
		{
			for(int i = 0; i < stuff.length - 1; i++)
			{
				if(System.getproperty(stuff[i]) != null)
				{
					System.out.println(System.getproperty(stuff[i]));
				}
			}
		}
	}
