
import java.util.Scanner;

public class Main {

	private static Scanner user_input;

	public static void main(String[] args) {
		user_input = new Scanner(System.in);

		String input = user_input.nextLine();
		user_input.close();
		String splitted_input[] = input.split(" ");
		
		if(splitted_input[splitted_input.length - 1] != "down"){
			for(int i = splitted_input.length - 1; i > 0; i--){
				if(System.getProperty(splitted_input[i]) != null){
					System.out.println(System.getProperty(splitted_input[i]));
				}
			}
		}else{
			for(String str : splitted_input){
				if(System.getProperty(str) != null){
					System.out.println(System.getProperty(str));
				}
			}
		}
	}

}

