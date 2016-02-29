package org.elsys.java;

public class DispayProperties 
{

	public static void main(String[] args)
	{
		int size = args.length;
		String[] arr = new String[size];
		int i = 0;
		
		for (i = 0; i < size; i++)
		{
			if(System.getProperty(args[i]) != null)
			{
				arr[i] = System.getProperty(args[i]);
			}
		}

		if(args[size-1].equals("down"))
		{
			for(i = (size - 1); i >= 0; i--)
			{
				if(arr[i] != null)
				{
					System.out.println(arr[i]);
				}
			}
		}
		else 
		{
			for(i = 0 ; i < (size); i++) 
			{
				if(arr[i]!=null)
				{
					System.out.println(arr[i]);
				}
			}
		}
	}
}