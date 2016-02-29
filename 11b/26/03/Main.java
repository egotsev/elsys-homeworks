public class Main {

    public static void main(String[] args) {
    	if (args.length == 0) {
    	    System.err.println("No arguments provided");
    	    System.exit(1);
    	}
    	
	int end = args.length - 1;

        String sortMethod = args[end];

        if (sortMethod.equals("down")) {
            for (int i = end - 1; i >= 0; i--) {
                printProperty(args[i]);
            }
        } else if (sortMethod.equals("up")) {
            for (int i = 0; i < end; i++) {
                printProperty(args[i]);
            }
        } else {
            for (int i = 0; i <= end; i++) {
                printProperty(args[i]);
            }
        }
    }

    private static void printProperty(String arg) {
        String property = System.getProperty(arg);
        if (property != null) {
            System.out.println(property);
        }
    }
}
