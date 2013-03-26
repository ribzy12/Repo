package MUSIC;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;


/**
 *  Holds all the methods for the store 
 * 
 * @author J Schumacher, J Gil
 * @version 2.4
 */
public class Store {
	String username;  //the clerk's UserName
	String password;  //the clerk's password
	Scanner keyboard = new Scanner(System.in);
	Clerk employee = new Clerk(keyboard);  //makes a new Clerk object
	
	/**
	 * Tries to log in as a clerk by checking the username and password entered by the user 
	 * with the master username and password 
	 * @param username -the clerk's UserName
	 * @param password - clerk's password
	 */
	private void login(String username, String password)
	{
		int count = 0;  //the number of times the user has tried to log in
		
		//loops until the user has tried and failed to log in 5 times
		while(count < 5)
		{
			if (username.equalsIgnoreCase("_cancel")) //returns back to welcome method
			{}
			else if(username.equalsIgnoreCase(Rules.MASTER_USERNAME) && password.equalsIgnoreCase(Rules.MASTER_PASSWORD))
			{
				employee.performClerkTasks();  
				count = 5;  //sets count to 5 so the loop stops
			}
			else if(count == 4)//if count = 4 than the user as tried and failed 5 times to login 
			                   //and cannot try again
			{
				System.out.print("\nI am sorry you have tried to many times to login, try again later");
				count = 5;  //sets count to 5 so the loop stopscount = 5;  //sets count to 5 so the loop stops
			}
			else if(!username.equalsIgnoreCase(Rules.MASTER_USERNAME))  //if the username entered not equals the master 
				                                              //username the user is prompted to reenter the 
				                                              //username and password and the loop starts again
			{
				System.out.print("\n\nThe username you entered is incorrect, try again " +
						" (or enter _cancel to cancel)\n");
				System.out.print("What your username?  ");
				username = keyboard.next(); //sets the value of username to the string the user enters 
				if (username.equalsIgnoreCase("_cancel"))  //if the user enters _cancel the programs 
					                                       //returns to welcome method and stops the loop
				{
					count = 5;  //sets the count to 5 so the loop stops
				}
				else
				{
					System.out.print("What your password?  ");
					password = keyboard.next();
				}
				count++;  //increments count by one
			}
			else if(!password.equalsIgnoreCase(Rules.MASTER_PASSWORD))  //if the password entered not equals the master 
                                                              //password the user is prompted to reenter the 
                                                              //username and password and the loop starts again
			{
				System.out.print("\n\nThe password you entered is incorrect, try again (or enter _cancel to " +
						"cancel)\n");
				System.out.print("What your username?  ");
				username = keyboard.next();
				if (username.equalsIgnoreCase("_cancel"))  //if the user enters _cancel the programs 
                                                           //returns to welcome method and stops the loop	
				{
					count = 5;  //sets the count to 5 so the loop stops
				}
				else
					{
					System.out.print("What your password?  ");
					password = keyboard.next();
				}
				count++;  //increments count by one
			}
		}
	}
	
	/**
	 * the central method in store that all other methods are run from
	 */
	public void welcome()
	{
		System.out.print("********************************************************** WELCOME TO MUSIC PLEX " +
				"**********************************************************");
		System.out.print("\n\n                                               This is an easy to use program to find CDs in the store.  " +
				           "\n                 You may search by a CD by entering the artist's name, the album title, or the title of a song from the album ");
		
		boolean outerRepeat = true; //makes a boolean object that will be used to see if the while loop continues
		while(outerRepeat == true)
		{
			try	
			{
				//prompts the user with three options
				System.out.print("\n\nWhat do you want to do? (Type the number of your choice)" 
						+ "\n1) search by artist name "
						+ "\n2) search by album title "
						+ "\n3) search by song title"
						+ "\n4) login if you are a clerk"
						+ "\n\nYour decision is: ");
				int decision = keyboard.nextInt();
				switch(decision)
				{
					case 1:  //asks the user to enter an artist name then starts the searchByArtistName method
					{
						System.out.print("\n*** WHEN ENTERING INFORMATION REPLACE ALL SPACES WITH DASHES (-), THIS IS VERY IMPORTANT ***\n" +
								"Make sure you spell the artist's name correctly (the case of the letters doesn't matter)\n");
						System.out.print("\nType the artist you wish to search for:  ");
						String artistName = keyboard.next();
						artistName = artistName.replace('-', ' '); //replaces all dashes in the string with spaces
						searchByArtistName(artistName);
						outerRepeat = false; //sets repeat to false so the loop stops
						break;
					}
					case 2:  //asks the user to enter an album title then starts the searchByAlbumTitle method 
					{
						System.out.print("\n*** WHEN ENTERING INFORMATION REPLACE ALL SPACES WITH DASHES (-), THIS IS VERY IMPORTANT ***\n" +
								"Make sure you spell the album's title correctly (the case of the letters doesn't matter)\n");
						System.out.print("\nType the album you wish to search for:  ");
						String albumTitle = keyboard.next();
						albumTitle = albumTitle.replace('-', ' '); //replaces all dashes in the string with spaces
						searchByAlbumTitle(albumTitle); 
						outerRepeat = false; //sets repeat to false so the loop stops
						break;
					}
					case 3:  //asks the user to enter a song title to search for 
					{
						System.out.print("\n*** WHEN ENTERING INFORMATION REPLACE ALL SPACES WITH DASHES (-), THIS IS VERY IMPORTANT ***\n" +
								"Make sure you spell the song's title correctly (the case of the letters doesn't matter)\n");
						System.out.print("\nType the song title you wish to search for:  ");
						String songTitle = keyboard.next();
						songTitle = songTitle.replace('-', ' '); //replaces all dashes in the string with spaces
						searchBySongTitle(songTitle); 
						outerRepeat = false; //sets repeat to false so the loop stops
						break;
					}
					case 4:  //asks the user for a username and password then call the login method
					{
						String username;
						String password = null;
						System.out.print("\n       What is your username?  ");
						username = keyboard.next();
						System.out.print("       What is your password?  ");
						password = keyboard.next();
						login(username, password);		
						outerRepeat = false; //sets repeat to false so the loop stops
						break;
					}
					default:  //if number entered isn't one of the defined cases the following message is outputted
					{
						System.out.print("\nThe system did not recognize the number you entered, " +
								"please try again\n");
						break;
					}
				}	
				//prints out a nice thank you message for choosing to shop at this stores
				System.out.print("\n\n********************************* Thank your for shopping at Music Plex, " +
						"I hope you enjoyed your visit********************************");
				System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n");
			}
			catch(InputMismatchException ime)
			{
				System.out.print("\n\nWhat you entered wasn't an integer, please try again\n\n\n\n\n\n\n\n\n\n\n\n\n");
				outerRepeat = false;
				String[] args = new String[2];//makes string array of size 2 
				Display.main(args);//calls the main method in Display with a String array of more than 0 so open method isn't called
			}
			catch(NoSuchElementException nsee)
			{
				System.out.print("\n\nThere has been a fatal error in the program.  \nDid you perhaps press control-z?  You can't press control-z in this program!  \nThe program will now shut down.");
				System.exit(0);
			}
		}
	}
	
	/**
	 * Searches for an artist with the given name and then prints out all that artist's albums
	 * @param artistName - the name of the artist the user is looking for
	 */
	private void searchByArtistName(String artistName)
	{
		boolean outerRepeat = true;
		while(outerRepeat == true)
		{
				
			ArrayList<Object> returnList = employee.findArtistByName(artistName, 0);
			Artist artistTemp = (Artist)returnList.get(0); //makes a new artist and instantiates it 
			  											   //by calling the findArtistByName() in the 
	 		                                               //Clerk class and castes it as an album	 				
			if((Integer)returnList.get(1) == 1) //if the value at returnList.get(1) is 1 stop the outer loop
			{
				outerRepeat = false;
			}
			else
			{	
				//walks through the artist's collection of albums and prints out each album title, one per line
				boolean innerRepeat = true; //makes a boolean object that is used to see if the while loop continues
				while(innerRepeat)
				{
					try 
					{
						System.out.print("\nThe available albums by this artist are the the following \n");
						for(int i = 0; i < artistTemp.artistsAlbums.size(); i++)
						{
						int num = i+1;
						System.out.print("  " + num + ") \"" + artistTemp.artistsAlbums.get(i).getAlbumTitle() + "\"\n");							}
						System.out.print("\nWhich album would you like to see? Please type the number of the album you desire: ");
						Album albumTemp = artistTemp.artistsAlbums.get(keyboard.nextInt() - 1);
						
						boolean doneRepeat = true; 
						while (doneRepeat == true)//loops until the user says they are done
						{
							System.out.print("\n                       The artist is:  " + artistTemp.getArtistName() + "\n");								albumTemp.printAllTheAlbumsInfo(); //all of the chosen album's information is printed
							System.out.print("\nWhen you are done type the word done or the letter 'd' here: ");
	
							String answer = keyboard.next();
							if(answer.equalsIgnoreCase("d") || answer.equalsIgnoreCase("done"))
							{
								doneRepeat = false;  // sets doneRepeat to false so the done loop doesn't continue
								innerRepeat = false; // sets innerRepeat to false so the inner loop doesn't continue
								outerRepeat = false; //sets outerRepeat to false so the outer loop stops
							}
						}//end done while loop
					}
					catch (IndexOutOfBoundsException ioobe) 
					{
						System.out.print("\nThe number you entered was not one on the option you were given please try again\n");
					}
					catch (NullPointerException npe)
					{
						innerRepeat = false; // sets innerRepeat to false so the inner loop doesn't continue
						outerRepeat = false; //sets outerRepeat to false so the outer loop stops
					}
				}//end inner while loop
			}//end outer else statement
		}//end outer while loop
	}//end method

	/**
	 * Finds the album in the with the entered title and displays all of that album's information
	 * @param albumTitle - the album Title the user is searching for
	 */
	private void searchByAlbumTitle(String albumTitle)
	{
		boolean outerRepeat = true;
		while(outerRepeat == true)
		{		  
			{
				ArrayList<Object> returnList = employee.findArtistsAlbumByTitle(albumTitle, 0);
				Album albumTemp = (Album)returnList.get(0); //makes a new album and instantiates it 
				                                                                //by calling the findArtistsAlbumByTitle() in the 
																				//Clerk class
				if((Integer)returnList.get(1) == 1)
				{
					outerRepeat = false;
				}
				else                                                          
				{
					try
					{
						boolean innerRepeat = true; //makes a boolean object that is used to see if the while loop continues
						while(innerRepeat == true)
						{
							String artistName = employee.findArtistByAlbumTitle(albumTemp.getAlbumTitle());
							System.out.print("\n                       The artist is:  " + artistName + "\n");
							
							albumTemp.printAllTheAlbumsInfo(); //all of the chosen album's information is printed
							
							System.out.print("\nWhen you are done type the word done or the letter 'd' here: ");
							String answer = keyboard.next();
							if(answer.equalsIgnoreCase("d") || answer.equalsIgnoreCase("done"))
							{
									outerRepeat = false; // sets outerRepeat to false so the outer loop doesn't continue
									innerRepeat = false; // sets innerRepeat to false so the inner loop doesn't continue
							}//end innermost if statement
						} //end inner while loop
					}//end try block
					catch (NullPointerException npe)
					{
						outerRepeat = false; //sets outerRepeat to false so the outer loop stops
					}//end catch block
				}//end inner else statement
			}//end outermost else statement
		}//end outer while loop
	}//end method
	
	/**
	 * Looks all the albums that contain the song title entered and then allows the user to select which one to 
	 * they want to view all the information for 
	 * @param songTitle - the song title the user is searching for
	 */
	private void searchBySongTitle(String songTitle)
	{		
		
		//sets allAlbumsWithThisSong to with all the albums that contain the desired song
		ArrayList<Object> returnList = employee.findAlbumsThatHaveSpecifiedSong(songTitle); 
		
		if((Integer)returnList.get(1) == 0)
		{
			try
			{
				Album albumChoosen = (Album)returnList.get(0);
				searchByAlbumTitle(albumChoosen.getAlbumTitle());
			}
			catch (NullPointerException npe)
			{}
		}//end if statement
	}//end method
}//end class