package domashno1;

public class HomeWork1 {
	public static void main(String[] args)
	{
		int i = 0;
		String[] array = new String[args.length];
		for (i = 0;i < args.length;i++)
		{
			
				array[i] = System.getProperty(args[i]);
			
		}
		if(args[(args.length-1)].equals("down"))
		{
			for(i = array.length-1; i >0; i--)
			{
				if(array[i]!= null)

					System.out.println(array[i]);
			}
		} else {
			for(i = 0 ; i < array.length-1; i++)
			{
				if(array[i]!= null)

				System.out.println(array[i]);
			}
		}
		
	}

}

