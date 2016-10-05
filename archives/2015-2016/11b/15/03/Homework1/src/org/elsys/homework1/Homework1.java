package org.elsys.homework1;

import java.lang.System;
import java.util.Arrays;
import java.util.Collections;

public class Homework1 {
	
	
	public static void main(String[] args) {
		int count = 0;
		for(String sy: args)
		{
			String prep = System.getProperty(sy);
			if(prep != null)
			{
				count++;
			}
		}
		String[] tokens = new String[count];
		int index = 0;
		for(String sy: args)
		{
			String prep = System.getProperty(sy);
			if(prep != null)
			{
				tokens[index] = prep;
				index++;
			}
		}
		
		if(args[args.length - 1].contentEquals("down"))
		{
			
			Arrays.sort(tokens, Collections.reverseOrder());
			for(String sy: tokens)
			{
				System.out.println(sy);
			}
			
		}
		else
		{
			Arrays.sort(tokens);
			for(String sy: tokens)
			{
				System.out.println(sy);
			}
		}
	}

}
