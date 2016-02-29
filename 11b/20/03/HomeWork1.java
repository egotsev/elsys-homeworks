package homework1;

public class HomeWork1 {

	public static void main(String[] args) {
		String method = args[args.length - 1];
		
		if(method.equals("down")) {
			for(int i = args.length - 1; i >= 0; i--) {
				String str = System.getProperty(args[i]);
				if (str != null) {
					System.out.println(str);
				}
			}
		} else if (method.equals("up") || args.length == 5) {
			for(int i = 0; i < args.length; i++) {
				String str = System.getProperty(args[i]);
				if (str != null) {
					System.out.println(str);
				}
			}
		}
		else {
			System.out.println("Invalid method for sorting given...");
			return;
		}
	}
}
