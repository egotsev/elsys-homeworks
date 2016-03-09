import java.util.Vector;
public class Property
{
	public static void main(String[] args)
	{
		Vector<String> vector = new Vector<>();
		for(int i = 0 ; i < args.length ; i++){
			String property = System.getProperty(args[i]);
			if (property != null){
				vector.add(property);
			}
		}
		String lastProperty = args[args.length - 1];
		boolean asc = lastProperty.compare("down") != 0;
		for(int i = 0; i < vector.size(); i++){
			if(asc){
				String out = vector.elementAt(i);
			}
			else{
					vector.elementAt((vector.size() - i) - 1);
			}
			System.out.println(out);
		}
	}
}
