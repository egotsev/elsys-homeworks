package hw;

import java.io.IOException;
import java.util.Stack;

public class OOP_03 {
	public static void main(String[]args){
		try {
			Stack<Stack<Character>> st=new Stack<Stack<Character>>();
			int c=0;
			while(c!=-1){
				c=System.in.read();
				if(c!=-1){
					if(c==' '){
					st.push(new Stack<Character>());
					}else{
						if(c=='\n'){
							c=-1;
						}else{
							st.elementAt(st.size()).push(Character.toChars(c)[0]);
						}
					}
				}
			}
			Stack<String> str = new Stack<String>();
			for(int i=0;i<st.size();i++){
				String s= new String();
				for(int j=0;j<st.elementAt(i).size();j++){
					s.concat(st.elementAt(i).elementAt(j).toString());
				}
				str.push(s);
			}
			for(int i=0;i<str.size();i++){
				String el=System.getProperty(str.elementAt(i));
				if(el!=null){
					System.out.println(el);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
