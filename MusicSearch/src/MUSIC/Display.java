package MUSIC;

/**
 * Contains the main method for the program
 * @author J Schumacher
 * @version 1.2
 */
public class Display {
	
	/**
	 * Main method to run program
	 * @param args - a string array
	 */
	public static void main(String[] args) 
	{	
		boolean okToContinue = true; //makes a boolean object to decide if its ok to continue
		if(args.length == 0)//if the length of the args array is 0 then call the open method, if not don't call the method
		{
			okToContinue = Clerk.open(); //calls the open method and sets the value of okToContinue
		}

		if(okToContinue == true)//if it is ok to continue the program continues
		{
			Store storeTemp = new Store(); //makes a new Store object to be used further down
			while(true)
			{
				storeTemp.welcome(); //calls the welcome method in Store to start the program
			}
		}
		else
		{
			System.exit(0); //if okToContinue doesn't equal true, the program ends
		}
	}//end method
}//end class