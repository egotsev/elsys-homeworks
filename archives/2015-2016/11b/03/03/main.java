package hw_system_props;

public class Main {

	public static void main(String[] args) {
		int argc = args.length-1;
		String preset = args[argc];

		if (preset.equals("down")) {
			for (int i = argc ; i >= 0 ; i--) {
				String prop = System.getProperty(args[i]);
				if (prop != null) {
					System.out.println(prop);
				}
			}
		} else {
			for (int i = 0 ; i <= argc ; i++) {
				String prop = System.getProperty(args[i]);
				if (prop != null) {
					System.out.println(prop);
				}
			}
		}
	}
}

