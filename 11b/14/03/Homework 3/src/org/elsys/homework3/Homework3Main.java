package org.elsys.homework3;

import java.lang.System;

public class Homework3Main {
	public static void main(String args[])
	{
		String prop[] = new String[args.length];
		for(int i = 0; i < args.length; i++)
		{
			prop[i] = args[i];
		}
		
		if(prop[prop.length - 1] == "up")
		{
			for(int i = 0; i < prop.length - 1; i++)
			{
				if(System.getProperty(prop[i]) != null)
				{
					System.out.println(System.getProperty(prop[i]));
				}
				else
				{
					System.out.println("invalid");
				}
			}
		}
		else if(prop[prop.length - 1] == "down")
		{
			for(int i = prop.length - 1; i >= 0; i++)
			{
				if(System.getProperty(prop[i]) != null)
				{
					System.out.println(System.getProperty(prop[i]));
				}
				else
				{
					System.out.println("invalid");
				}
			}
			
		}
	}
}