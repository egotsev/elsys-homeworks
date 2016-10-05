package org.elsys.hw_3;

import java.util.Arrays;
import java.util.Collections;

public class B_21_Martin_Galabov {
	public static void main(String[] args) {
		int i;
		String [] command_line = new String[args.length-1];
		for(i = 0; i<args.length-1; ++i){
			command_line[i] = "<"+System.getProperty(args[i])+">";
		}
		if(args[args.length-1] == "up"){
			Arrays.sort(command_line);
		}
		if(args[args.length-1] == "down"){
			Arrays.sort(command_line,Collections.reverseOrder());
		}
		for(String token: command_line){
			System.out.println(token);
		}
	}
}
