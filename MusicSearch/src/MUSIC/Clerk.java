package MUSIC;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.text.NumberFormat;
import java.util.Locale;



/**
 * Performs all the Tasks an Employee of the store would need to do
 * @author J Schumacher, J Gil, P Torres 
 * @version 3.2
 */
		
public class Clerk 
{
	Scanner keyboard;         //a Scanner object
	private static ArrayList<Artist> allArtists = new ArrayList<Artist>();  //Collection of all the artist the store carries
	private static ArrayList<Album> allAlbums = new ArrayList<Album>();     //Collection of all the albums the store carries
	
	/**
	 * Makes a new Clerk object
	 * @param keyboard - a Scanner object
	 */
	public Clerk(Scanner keyboard) 
	{
		this.keyboard = keyboard;
	}
	
	/**
	 * Makes a new album object with the parameters given and adds it to the artist's ArrayList of albums, 
	 * if an artist with the given name doesn't exist yet it creates that artist as well 
	 * @param artistTemp - the reference to that artist if it exists
	 * @param artistName - the artist's name
	 * @param albumTemp - a reference to the album if it exists yet
	 * @param albumTitle - the album's title
	 */
	private void add(Artist artistTemp, String artistName, Album albumTemp, String albumTitle)
	{
		String locationInStore;  //the albums location in the store
		String recordLabel;      //the record label that produced the album
		double price;            //the price of the album
		int numInStock;          //the number of copies of this album the store has in stock
		int numTracks;           // the number of tracks the album has
		int yearOfRelease;
		boolean inCirculation;   //whether or not the album is still in circulation
		String[] songTitles = null;
		//if no artist exists yet a new artist is made with the given artist name and added to the collection of artists
		if(artistTemp == null)
		{
			artistTemp = new Artist(artistName);
			allArtists.add(artistTemp);
		}
		
		//asks the user to fill in all the album's information
		System.out.print("What is the album's price? $");
		price = keyboard.nextDouble();
		System.out.print("How many tracks does the album have? ");
		numTracks = keyboard.nextInt();
		System.out.print("What record label produced the album? ");
		recordLabel = keyboard.next();
		recordLabel = recordLabel.replace('-', ' '); //replaces all dashes in the string with spaces
		System.out.print("In what section of the store is the album located? ");
		locationInStore = keyboard.next();
		locationInStore = locationInStore.replace('-', ' '); //replaces all dashes in the string with spaces
		System.out.print("How many of this album does the store have in stock? ");
		numInStock = keyboard.nextInt();
		System.out.print("What year was this album released? ");
		yearOfRelease = keyboard.nextInt();
		
		inCirculation = true; //sets the circulation value to true because the album must be still in circulation if it is just being 
		                      //added now 
		albumTemp = new Album(albumTitle, price, numTracks, recordLabel, locationInStore, 
				numInStock, songTitles, inCirculation, yearOfRelease);
		System.out.print("");
		System.out.print("\n");
		albumTemp.setAllSongTitles(keyboard); //sets all the albums song titles
		System.out.print("\n");
		artistTemp.addToArtistsAlbums(albumTemp); //adds this album to the artist collection of albums
		allAlbums.add(albumTemp); //adds this album to the collection of all albums
		saveArtist(); //backs up all the information into the text file
	}

	/**
	 * Searches for a given artist in the ArrayList allArtists 
	 * 
	 * @param artistName - the artist that the user is looking for
	 * @param okReturn - whether or not it is ok to return null if no album can be found
	 * @return returnList - ArrayList of type Object containing the chosen artist and the stop variable
	 */
	public ArrayList<Object> findArtistByName(String artistName, int okReturn)
	{
		ArrayList<Object> returnList = new ArrayList<Object>();  //this is what the method will return
		Artist artistTemp = null;
		int stop = 0;  //this variable will be used later
		ArrayList<Artist> allArtistMatches = new ArrayList<Artist>();
		StringTokenizer artistNameString = new StringTokenizer(artistName); 
		String elementsOfArtistName[] = new String[artistNameString.countTokens()];
		int count = 0;
		
		/** makes an array that contains all the words the user entered as the artist's name **/
		while(artistNameString.hasMoreTokens())
		{
			elementsOfArtistName[count] = artistNameString.nextToken();
			count++;
		}
		
		//steps through the collection of artist trying to find the artist with the given name
		for(int i = 0; i < allArtists.size(); i++)
		{
			StringTokenizer artistNameStringTemp = new StringTokenizer(allArtists.get(i).getArtistName());
			int resultCount = 0;  //counts how many matches the artist has to what the user typed
			while(artistNameStringTemp.hasMoreTokens()) 
			//if (allArtists.get(i).getArtistName().equalsIgnoreCase(artistName))
			{
				String temp = artistNameStringTemp.nextToken();
				for(int j = 0; j < elementsOfArtistName.length; j++)
				{
					if(elementsOfArtistName[j].equalsIgnoreCase(temp))  //if elementsOfArtistName[j] equals next token of temp increment resultCount
					{
						resultCount++;
					}
				}
			}
			if(resultCount == elementsOfArtistName.length)  //if true then the this artist matches every word of artistName
			{
				allArtistMatches.add(allArtists.get(i));  //add this album to allArtistMatches
			}
		}
		if(allArtistMatches.size() > 1)  //There was one than one artist match
		{
			boolean repeat = true;
			while(repeat)
			{
				try 
				{
					int num = 0;
					System.out.print("\nThese are all the possible artists that match what you entered\n");
					for(int k = 0; k < allArtistMatches.size(); k++)
					{
						num = k+1;
						System.out.print("  " + (num) + ") " + allArtistMatches.get(k).getArtistName() + "\n");
					}
					System.out.print("  " + (num+1) + ") Type " + (num+1) + " to try again\n");
					System.out.print("  " + (num+2) + ") Type " + (num+2) + " to quit\n");
					System.out.print("\nPlease type the number of the album you desire: ");
					int answer = keyboard.nextInt();
					if(answer == (num+1))
					{
						System.out.print("\nWhat is the artist's name?  ");
						artistName = keyboard.next();
						artistName = artistName.replace('-', ' '); //replaces all underscores in the string with spaces
						findArtistByName(artistName, okReturn);
					}
					else if(answer == (num+2))
					{
						stop = 1;  //set stop to 1 to signal the method that called it not to continue
					}
					else
					{
						artistTemp = allArtistMatches.get(answer - 1);  //adds this album to allArtistMatches
					}
					repeat = false;
				} 
				catch (IndexOutOfBoundsException e) 
				{
					System.out.print("\nThe number you entered was not one on the option you were given please try again\n");			
				}
			}
		}
		else if(allArtistMatches.size() == 1)  //there was only one artist match 
		{
			System.out.print("\nThe closest artist match to what you typed is: " + allArtistMatches.get(0).getArtistName() + "\n");
			artistTemp = allArtistMatches.get(0);
		}
		else if(allArtistMatches.size() == 0)  //there was zero artist matches
		{		
			if(okReturn == 0)  //the method that called it doesn't want to get null back
			{
				System.out.print("\nNo possible artist matches were found, would you like to try again? (yes or no): ");
				String result = keyboard.next(); 	
				if(result.equalsIgnoreCase("yes") || result.equalsIgnoreCase("y"))
				{
					System.out.print("\nWhat is the artist's name?  ");
					artistName = keyboard.next();
					artistName = artistName.replace('-', ' '); //replaces all underscores in the string with spaces
					findArtistByName(artistName, okReturn);
				}
				else
				{
					stop = 1;  //tells the method that called it to stop
				}
			}
			else if(okReturn == 1)  //the method that called it doesn't mind receiving null back
			{
				artistTemp = null;
			}
		}
		returnList.add(artistTemp);	 //add the chosen artist to returnList
		returnList.add(stop);  //add the value of stop to returnList
		return returnList;  //a reference to the album with the given title or null if it cannot be found
	}
	
	/**
	 * Searches for a given album in the ArrayList allAlbums
	 * @param albumTitle - the title of the album to be found
	 * @param print - decides whether or not to print anything if only one album id found 
	 * @return returnList - ArrayList of type Object containing the chosen album and the stop variable
	 */
	public ArrayList<Object> findArtistsAlbumByTitle(String albumTitle, int print)
	{
		ArrayList<Object> returnList = new ArrayList<Object>(); //this is what the method will return
		Album albumTemp = null;
		int stop = 0;    //this variable will be used later
		ArrayList<Album> allAlbumMatches = new ArrayList<Album>();  //ArrayList of all the album matches that are found
		StringTokenizer albumTitleString = new StringTokenizer(albumTitle); 
		String elementsOfAlbumTitle[] = new String[albumTitleString.countTokens()];
		int count = 0;
		
		/** makes an array that contains all the words the user entered as the album title **/
		while(albumTitleString.hasMoreTokens())
		{
			elementsOfAlbumTitle[count] = albumTitleString.nextToken();
			count++;
		}
		
		//steps through the collection of all albums trying to find the album with the given title
		for(int i = 0; i < allAlbums.size(); i++)
		{
			StringTokenizer albumTitleStringTemp = new StringTokenizer(allAlbums.get(i).getAlbumTitle());
			int resultCount = 0;
			while(albumTitleStringTemp.hasMoreTokens()) 
			{
				String temp = albumTitleStringTemp.nextToken();
				for(int j = 0; j < elementsOfAlbumTitle.length; j++)
				{
					if(elementsOfAlbumTitle[j].equalsIgnoreCase(temp))
					{
						resultCount++;
					}
				}
			}
			if(resultCount == elementsOfAlbumTitle.length) //if true then the this album matches every word of albumTitle
			{
				allAlbumMatches.add(allAlbums.get(i)); //add this album to allAlbumMatches
			}
		}
		
		if(allAlbumMatches.size() > 1)  //There was more than one artist match
		{
			boolean repeat = true;
			while(repeat)
			{
				try 
				{
					int num = 0;
					System.out.print("\nThese are all the possible artists that match what you entered\n");
					for(int k = 0; k < allAlbumMatches.size(); k++)
					{
						num = k+1;
						System.out.print("  " + (num) + ") " + allAlbumMatches.get(k).getAlbumTitle() + "\n");
					}
					System.out.print("  " + (num+1) + ") Type " + (num+1) + " to try again\n");
					System.out.print("  " + (num+2) + ") Type " + (num+2) + " to quit\n");
					System.out.print("\nPlease type the number of the album you desire: ");
					int answer = keyboard.nextInt();
					if(answer == (num+1))
					{
						System.out.print("\nWhat is the album's title?  ");
						String AlbumTitle = keyboard.next();
						AlbumTitle = AlbumTitle.replace('-', ' '); //replaces all underscores in the string with spaces
						findArtistsAlbumByTitle(AlbumTitle, 0);
					}
					else if(answer == (num+2))
					{
						stop = 1;  //tells the method that called it to stop
					}
					else
					{
						albumTemp = allAlbumMatches.get(keyboard.nextInt() - 1);
					}
					repeat = false;
				} 
				catch (IndexOutOfBoundsException e) 
				{
					System.out.print("\nThe number you entered was not one on the option you were given please try again\n");
				}
			}
		}
		else if(allAlbumMatches.size() == 1)  //there was only one album match
		{
			if(print == 0) 
			{
				System.out.print("\nThe closest album match to what you typed is: \"" + allAlbumMatches.get(0).getAlbumTitle() + "\" by " 
					 + findArtistByAlbumTitle(allAlbumMatches.get(0).getAlbumTitle()) + "\n");
			}
			albumTemp = allAlbumMatches.get(0);
		}
		else  //there was zero album matches
		{
			System.out.print("\nNo possible album matches were found, would you like to try again? (yes or no): ");
			String result = keyboard.next(); 	
			if(result.equalsIgnoreCase("yes") || result.equalsIgnoreCase("y"))
			{
				System.out.print("\nWhat is the album's title?  ");
				String AlbumTitle = keyboard.next();
				AlbumTitle = AlbumTitle.replace('-', ' '); //replaces all underscores in the string with spaces
				findArtistsAlbumByTitle(AlbumTitle, 0);
			}
			else
			{
				stop = 1;  //tells the method that called it to stop
			}
		}
		returnList.add(albumTemp);  //add the chosen album to returnList
		returnList.add(stop);  //add the value of stop to returnList
		return returnList;  //a reference to the album with the given title or null if it cannot be found
	}
	
	/**
	 * Takes an album title and searches for the artist that made that artist
	 * @param albumTitle - the title of the album that is given
	 * @return artistName - the artist name that made the given album
	 */
	public String findArtistByAlbumTitle(String albumTitle)
	{
		//steps through the allArtists collection
		for(int i = 0; i < allArtists.size(); i++)
		{
			//steps through the the artist at index i's artistsAlbums collection
			for( int j = 0; j < allArtists.get(i).artistsAlbums.size(); j++)
			{
				Album albumTemp = allArtists.get(i).artistsAlbums.get(j); //makes an album object and sets its value to the album at index i 
				                                                          //of allArtist collection's album at index j of it's artistsAlbums 
				                                                          //collection  
				String foundAlbumTitle = albumTemp.getAlbumTitle();  //makes a string and sets the value of it to the album title of albumTemp 
				if(foundAlbumTitle.equalsIgnoreCase(albumTitle)) //if the found Album Title equals the albumTitle returns the artistName 
					                                             //at index i 
				{
					return allArtists.get(i).getArtistName(); //returns the artistName at index i 
				}
			}
		}
		return null; //if no match was found null is returned
	}
	
	/**
	 * Searches through every album in the system to find the desired song
	 * @param songTitle - the title of the song that the user wants to find
	 * @return returnList - ArrayList of type Object containing the chosen album and the stop variable 
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Object> findAlbumsThatHaveSpecifiedSong(String songTitle)
	{
		ArrayList<Object> returnList = new ArrayList<Object>(); //this is what the method will return
		Album albumTemp = null;
		int stop = 0;
		ArrayList<Object[]> allAlbumsWithThisSong = new ArrayList<Object[]>();  //an ArrayList of arrays of objects, each having two elements, 
		                                                                        //the album found and an ArrayList of all the songs found that 
		                                                                        //matched on that album
		StringTokenizer songTitleString = new StringTokenizer(songTitle);
		String elementsOfSongTitle[] = new String[songTitleString.countTokens()];
		int count = 0;  //counts how many matches the album has to what the user typed
		
		/** makes an array that contains all the words the user entered as the song's title **/
		while(songTitleString.hasMoreTokens())
		{
			elementsOfSongTitle[count] = songTitleString.nextToken();
			count++;
		}
		
		//steps through the collection of all albums trying to find an album that matches what the user typed for album title
		for(int i = 0; i < allArtists.size(); i++)
		{
			ArrayList<Album> thisArtistsAlbums = allArtists.get(i).getArtistsAlbums();//sets thisArtistsAlbums to all albums by the artist at 
			boolean checkAlreadyAdded = false;                                                                          //index i of allArtists collection
			for(int j = 0; j < thisArtistsAlbums.size(); j++)//walks through the collection of all the albums by the artist 
				                                             //at index i of allArtists collection
			{
				boolean foundAlbum = false; 
				Object[] matchingSongsOnAlbum = new Object[2]; //creates a two element array of Objects, the first element is the found album 
				                                               //and an ArrayList of all the songs found that matched on that album
				ArrayList<String> songsThatMatch = new ArrayList<String>(); //ArrayList of all the songs found that matched on that album
				String[] songTitlesArray = thisArtistsAlbums.get(j).returnSongTitlesArray();//sets songTitlesArray to contain all songs on the 
				                                                                          //album at index at j of thisArtistsAlbums collection
				for(int k = 0; k < songTitlesArray.length; k++)//walks through the array of all the songs
				{
					StringTokenizer songTitleStringTemp = new StringTokenizer(songTitlesArray[k]);  
					int resultCount = 0;
					while(songTitleStringTemp.hasMoreTokens())   //keeps going until there are no more tokens in songTitleStringTemp
					{
						String temp = songTitleStringTemp.nextToken();
						for(int m = 0; m < elementsOfSongTitle.length; m++)
						{
							if(elementsOfSongTitle[m].equalsIgnoreCase(temp))  //if elementsOfSongTitle[j] equals next token of temp 
								                                               //increment resultCount
							{
								/**String matchingSong = "\"" + songTitlesArray[k] + "\" from of the album \"" + 
								                       thisArtistsAlbums.get(j).getAlbumTitle() + "\"" + " by " + 
								                       allArtists.get(i).getArtistName() + "\n";
								**/
								resultCount++;
							}
						}
					}
					if(resultCount == elementsOfSongTitle.length)  //if true then the this album matches every word of albumTitle
					{
						songsThatMatch.add(songTitlesArray[k]); //adds this song title to the songsThatMatch ArrayList  
						foundAlbum = true; //signals an album was found to match 
						//allAlbumsWithThisSong.add(matchingSongsOnAlbum);//adds this string to the allAlbumsWithThisSong collection
					}
				}//end for loop with int k
				if(foundAlbum==true && checkAlreadyAdded==false) //checks to see if this album matched and also checks if it was already added
				{
					matchingSongsOnAlbum[0] = thisArtistsAlbums.get(j); //adds this album title to matchingSongsOnAlbum
					matchingSongsOnAlbum[1] = songsThatMatch; //adds the ArrayList of all matching songs from the album to matchingSongsOnAlbum
					allAlbumsWithThisSong.add(matchingSongsOnAlbum); //adds the array matchingSongsOnAlbum to allAlbumsWithThisSong
					checkAlreadyAdded = true; //makes sure this album is added to allAlbumsWithThisSong more than once
				}
			}//end for loop with int j
		}//end for loop with int i		
		if(allAlbumsWithThisSong.size() > 1)  //There was more than one artist match
		{
			boolean repeat = true;
			while(repeat)
			{
				try 
				{
					int num = 0;
					System.out.print("\nThese are all the possible albums that have songs with titles that words you typed\n");
					for(int n = 0; n < allAlbumsWithThisSong.size(); n++)
					{ 
						Album foundAlbum = (Album)allAlbumsWithThisSong.get(n)[0]; //sets foundAlbum to the first element in the array found 
						                                                           //at allAlbumsWithThisSong.get(n) and castes it as an Album
						String albumTitleTemp = foundAlbum.getAlbumTitle(); //sets albumTitleTemp to the song title of the foundAlbum 
						num = n+1;
						System.out.print("  " + (num) + ") \"" + foundAlbum.getAlbumTitle() + "\" by " + findArtistByAlbumTitle(albumTitleTemp) 
								+ " has the following matching tracks\n");
						Object objectFound = allAlbumsWithThisSong.get(n)[1];//sets objectFound to the ArrayList found in the second index of 
						                                                     //the array found at allAlbumsWithThisSong.get(n)
						
						ArrayList<String> matchingSongs = (ArrayList<String>)objectFound;  //castes matchingSongs as an ArrayList of Strings
						
						String foundSongTitle = "";
						for(int o = 0; o < matchingSongs.size(); o++)//walks through matchingSongs and prints out all the matching song titles
						{
							foundSongTitle = (String)matchingSongs.get(o);
							System.out.print("        \"" + foundSongTitle + "\"\n");
						}
					}
					System.out.print("  " + (num+1) + ") Type " + (num+1) + " to try again\n");
					System.out.print("  " + (num+2) + ") Type " + (num+2) + " to quit\n");
					System.out.print("\nPlease type the number of the album you desire: ");
					int answer = keyboard.nextInt();
					if(answer == (num+1))
					{
						System.out.print("\nWhat is the song's title?  ");
						songTitle = keyboard.next();
						songTitle = songTitle.replace('-', ' '); //replaces all underscores in the string with spaces
						findAlbumsThatHaveSpecifiedSong(songTitle);
					}
					else if(answer == (num+2))
					{
						stop = 1; //tells the method that called it to stop
					}
					else
					{
						albumTemp = (Album)allAlbumsWithThisSong.get(answer - 1)[0];
					}
					repeat = false;
				}
				catch (IndexOutOfBoundsException e) 
				{
					System.out.print("\nThe number you entered was not one on the option you were given please try again\n");
				}
			}
		}
		else if(allAlbumsWithThisSong.size() == 1)  //there was only one album match
		{
			//System.out.print("\nThe closest album match to what you typed is: \"" + allAlbumsWithThisSong.get(0).getAlbumTitle() + "\"\n");
			albumTemp = (Album)allAlbumsWithThisSong.get(0)[0];
		}
		else if(allAlbumsWithThisSong.size() < 1)  //there was zero album matches
		{
			System.out.print("\nNo albums were found to have songs with titles that words you typed, " 
					+ "would you to try again? (yes or no)\n");
			String result = keyboard.next(); 	
			if(result.equalsIgnoreCase("yes") || result.equalsIgnoreCase("y"))
			{
				System.out.print("\nWhat is the song's title?  ");
				String AlbumTitle = keyboard.next();
				AlbumTitle = AlbumTitle.replace('-', ' '); //replaces all underscores in the string with spaces
				findAlbumsThatHaveSpecifiedSong(AlbumTitle);
			}
			else
			{
				stop = 1;  //tells the method that called it to stop
			}
		}
		returnList.add(albumTemp);  //add the chosen album to returnList
		returnList.add(stop);  //add the value of stop to returnList
		return returnList;  //a reference to the album with the given title or null if it cannot be found
	}
	
	/**
	 * Removes a given album from the ArrayList of all albums
	 * @param artistTemp - the artist name of the album to be deleted 
	 * @param albumTemp - the album title of the album to be deleted
	 */
	private void remove(Artist artistTemp, Album albumTemp)
	{
		System.out.print("\nAre your sure you want to delete this album?  (yes or no)");
		String result = keyboard.next(); 	
		if(result.equalsIgnoreCase("yes") || result.equalsIgnoreCase("y"))
		{
			System.out.print("The album \"" + albumTemp.getAlbumTitle() + "\" by " + artistTemp.getArtistName() + " was deleted");	
			artistTemp.deleteFromArtistsAlbums(albumTemp); //deletes the album from the artists collection of albums 
			allAlbums.remove(albumTemp); //deletes the album for the collection of all albums

			//checks to see if there are any albums left for that artist, if not that artist it deleted from the collection of all artists
			if(artistTemp.artistsAlbums.size() == 0)
			{
				System.out.print("\n" + artistTemp.getArtistName() + " has no albums left in the system and the artist will now be deleted");
				allArtists.remove(artistTemp);
			}
			saveArtist(); //backs up all the information into the text file
		}
		else //does nothing if the user says they aren't sure
		{}
	}
		
	/**
	 * Allows the user to edit all an album's information
	 * @param artistName - album's artist name 
	 * @param albumTitle - the album title
	 * @throws InputMismatchException
	 * @throws NoSuchElementException
	 */
	private void edit(Artist artistTemp, Album albumTemp)
	{
		String albumTitle = albumTemp.getAlbumTitle();
		
		boolean repeat = true;
		while(repeat == true)
		{		
			ArrayList<Object> returnList = findArtistsAlbumByTitle(albumTitle, 1);
			Album allAlbumsTemp = (Album)returnList.get(0);  //find that album in the collection of all albums 
			
			System.out.print("\nWhat do you want to do? (Type the number of your choice)");
			System.out.print("\n     1)  Change the album's title");
			System.out.print("\n     2)  Change the album's record label");
			System.out.print("\n     3)  Change the album's loctation in store");
			System.out.print("\n     4)  Change the number copies of this album the store has in stock");
			System.out.print("\n     5)  Change the album's price");
			System.out.print("\n     6)  Change the album's circulation value");
			System.out.print("\n     7)  Change the year this album was released");
			System.out.print("\n     8)  Change the album's track titles");
			System.out.print("\n     0)  Return to clerk options menu \n");
			System.out.print("\nYour decision is: ");
			
			int decision = keyboard.nextInt();
			switch(decision)
			{
				case 1: //changes the album title
				{
					System.out.print("\nThe current album title is: \"" + albumTemp.getAlbumTitle() + "\"");
					System.out.print("\nWhat do you want to change the album's title to? ");
					String newAlbumTitle = keyboard.next(); 
					newAlbumTitle = newAlbumTitle.replace('-', ' '); //replaces all spaces in the string with underscores
					allAlbumsTemp.setAlbumTitle(newAlbumTitle); //changes the album title for the album in all albums collection
					albumTemp.setAlbumTitle(newAlbumTitle); //changes the album title for the album in artist's albums collection
					albumTitle = newAlbumTitle;
					saveArtist(); //backs up all the information into the text file 
					break;
				}			
				case 2: //changes the album's record label
				{
					System.out.print("\nThe album's current record label is: " + albumTemp.getRecordLabel());
					System.out.print("\nWhat do you want to change to album's record label to? ");
					String newRecordLabel =  keyboard.next();
					newRecordLabel = newRecordLabel.replace('-', ' '); //replaces all spaces in the string with underscores
					allAlbumsTemp.setRecordLabel(newRecordLabel); //changes the record label for the album in all albums collection
					albumTemp.setRecordLabel(newRecordLabel); //changes the record label for the album in artist's albums collection
					saveArtist(); //backs up all the information into the text file
					break;
				}
				case 3: //changes the album's store location
				{
					System.out.print("\nThe album's current store location is: " + albumTemp.getLocationInStore());
					System.out.print("\nIn what section of the store is the album located? ");
					String newAlbumLocation = keyboard.next(); 
					newAlbumLocation = newAlbumLocation.replace('-', ' '); //replaces all spaces in the string with underscores
					allAlbumsTemp.setLocationInStore(newAlbumLocation); //changes the store location in for the album for the album in all albums collection			
					albumTemp.setLocationInStore(newAlbumLocation); //changes the store location in for the album for the album in artist's albums collection
					saveArtist(); //backs up all the information into the text file
					break;
				}
				case 4: //Changes the album's stock value 
				{
					System.out.print("\nThe current stock value for this album is: " + albumTemp.getNumInStock());
					System.out.print("\nHow many of this album does the store have in stock? ");
					int newStockNum = keyboard.nextInt();
					allAlbumsTemp.setNumInStock(newStockNum); //changes the stock value in for the album for the album in all albums collection
					albumTemp.setNumInStock(newStockNum); //changes the stock value in for the album for the album in artist's albums collection
					saveArtist(); //backs up all the information into the text file
					break;
				}
				case 5: //changes the album's price
				{
					NumberFormat moneyFormatter = NumberFormat.getCurrencyInstance(Locale.US); 
					System.out.print("\nThe album's current price is: " + moneyFormatter.format(albumTemp.getPrice())); //formats the price as US currency
					System.out.print("\nHow much does this album cost? $" );
					double newAlbumCost = keyboard.nextDouble();
					allAlbumsTemp.setPrice(newAlbumCost); //changes the price in for the album for the album in all albums collection
					albumTemp.setPrice(newAlbumCost); //changes the price in for the album for the album in artist's albums collection
					saveArtist(); //backs up all the information into the text file
					break;
				}
				case 6: //changes the circulation value
				{
					String out;
					out = "\nAccording to this system this album is: ";
					boolean result = albumTemp.checkIfCirculation();
					if(albumTemp.checkIfCirculation() == true) //if the value of inCirculation is true than the album is still in circulation 
					{
						out = out + "still in circulation";
					}
					else //if the value of inCirculation is false than the album is not still in circulation
					{
						out = out + "not still in circulation";
					}
					System.out.print(out);	
					System.out.print("\nIs this album still in circulation? (y/n) "); 
					String answer = keyboard.next();
					result = true;
					if(answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("yes")) 
					{
						result = true;
					}
					else if(answer.equalsIgnoreCase("n") || answer.equalsIgnoreCase("no"))
					{
						result = false;
					}
					allAlbumsTemp.setCirculation(result); //changes the circulation value in for the album for the album in all albums collection
					albumTemp.setCirculation(result); //changes the circulation value in for the album for the album in artist's albums collection
					saveArtist(); //backs up all the information into the text file
					break;
				}
				case 7: //changes the album's release year
				{
					System.out.print("\nThe album's current year of release is: " + albumTemp.getReleaseYear()); 
					System.out.print("\nWhat year was this album released? " );
					int yearOfRelease = keyboard.nextInt();
					System.out.print("\n");
					allAlbumsTemp.setReleaseYear(yearOfRelease); //changes the release year for the album for the album in all albums collection
					albumTemp.setReleaseYear(yearOfRelease); //changes the release year for the album for the album in artist's albums collection
					saveArtist(); //backs up all the information into the text file
					break;
				}
				case 8: //changes the song titles
				{
					changeSongTitles(artistTemp, albumTemp, allAlbumsTemp); //calls the changeSongTitles() method to change song titles
					saveArtist(); //backs up all the information into the text file
					break;
				}
				case 0: //returns to the performClerkTasks() method
				{
					System.out.print("\n");
					repeat = false;
					break;
				}
				default: //if the number the user entered isn't one of the defined cases the user is told this
				{
					System.out.print("\nThe system did not recognize the number you entered, please try again\n");
					break;
				}
			} //ends the switch statement 			
		}
	}
	
	/**
	 * Changes the track name of the track desired by the user
	 * @param artistTemp - an artist reference to the artist whose album is being edited
	 * @param albumTemp - an album reference to the album to be edited
	 * @param allAlbumsTemp - an album reference 
	 * @throws InputMismatchException
	 * @throws NoSuchElementException
	 */
	private void changeSongTitles(Artist artistTemp, Album albumTemp, Album allAlbumsTemp)
	{
		boolean repeat = true;
		while(repeat == true)
		{
			System.out.print("\n    1)  Show track titles");			
			System.out.print("\n    2)  Change a track title");
			System.out.print("\n    0)  Return to edit options menu");
			System.out.print("\n\nYour decision is: ");
			
			int decision = keyboard.nextInt();
			switch(decision)
			{
				case 1: //prints all the song titles out one per line
				{
					System.out.print("\nThe album's current song titles are: \n");
					albumTemp.printSongTitles(); //calls the album's printSongTitles() method
					System.out.print("\n"); //skips a line
					break;
				}
				
				case 2: //changes one song title
				{
					    System.out.print("\n*** REMEMBER WHEN ENTERING INFORMATION REPLACE ALL SPACES WITH DASHES (-), " +
					    		"THIS IS VERY IMPORTANT ***\n");
						System.out.print("\nWhat track number would you like to change? ");
						int trackNum = keyboard.nextInt();
						System.out.print("What do you want to change that track title to? ");
						String newTitle = keyboard.next();
						newTitle = newTitle.replace('-', ' '); //replaces all spaces in the string with underscores
						albumTemp.setOneSongTitle(trackNum-1, newTitle); //changes the track title of one track in for the album for the album in all albums collection
						allAlbumsTemp.setOneSongTitle(trackNum-1, newTitle); //changes the track title of one track in for the album for the album in all albums collection
						break;
				}
			 
				case 0:	//returns to the edit() method
				{
					System.out.print("\n");
					repeat = false;
					break;
				}
				default: //if the number the user entered isn't one of the defined cases the user is told this
				{
					System.out.print("\nThe system did not recognize the number you entered, please try again\n");
					break;
				}
			}//ends the switch statement 
		}
	}
	
	/**
	 * The central method in the Clerk class that calls the other methods
	 * @throws InputMismatchException
	 * @throws NoSuchElementException
	 */
	public void performClerkTasks()
	{
		boolean repeat = true;
		while(repeat == true)
		{
			System.out.print("\nYou can do the following");
			System.out.print("\n    1)  Add an album");
			System.out.print("\n    2)  Edit an album");
			System.out.print("\n    3)  Remove an album");
			System.out.print("\n    4)  Shut down program");
			System.out.print("\n    0)  Return to welcome screen\n");
			System.out.print("\nYour decision is: ");
			
			int decision = keyboard.nextInt();
			
			switch(decision)
			{
				case 1: //adds an album 
				{		
					System.out.print("\n*** REMEMBER WHEN ENTERING INFORMATION REPLACE ALL SPACES WITH DASHES (-), " +
							"THIS IS VERY IMPORTANT ***\n");
					System.out.print("\nWhat is the artist's name?  ");
					String artistName = keyboard.next();
					artistName = artistName.replace('-', ' '); //replaces all dashes in the string with spaces
					ArrayList<Object> returnList = findArtistByName(artistName, 2);
					Artist artistTemp = (Artist)returnList.get(0);
					if(artistTemp != null)             //if an artist existed with the given artist name artistTemp will not be null
					{
						System.out.print("\nWhat is the album's title?  ");
						String albumTitle = keyboard.next();
						albumTitle = albumTitle.replace('-', ' '); //replaces all dashes in the string with spaces
						ArrayList<Object> resultList = artistTemp.findArtistsAlbumByTitle(albumTitle, 1);
						Album albumTemp = (Album)resultList.get(0); //finds the album with given name in the artist's collection of albums
						if(albumTemp != null)  //if albumTemp isn't null than the album the user is trying to add already exists
						{
							System.out.print("\nThat artist already has an album with that title, would you like to edit that album? " 
									+ "(yes or no) ");
							String answer = keyboard.next();
							if(answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("yes"))
							{
								edit(artistTemp, albumTemp);
							}	
						}
						else 
						{
							add(artistTemp, artistName, albumTemp, albumTitle);
						}
					}
					else
					{
						System.out.print("\nWhat is the album's title?  ");
						String albumTitle = keyboard.next();
						albumTitle = albumTitle.replace('-', ' '); //replaces all dashes in the string with spaces
						add(artistTemp, artistName, null, albumTitle);
					}
					break;
				}
				case 2: //edits an album
				{
					System.out.print("\n*** REMEMBER WHEN ENTERING INFORMATION REPLACE ALL SPACES WITH DASHES (-), " +
							"THIS IS VERY IMPORTANT ***\n");
					System.out.print("\nWhat is the artist's name?  ");
					String artistName = keyboard.next();
					artistName = artistName.replace('-', ' '); //replaces all dashes in the string with spaces
					ArrayList<Object> returnList = findArtistByName(artistName, 0);
					Artist artistTemp = (Artist)returnList.get(0);
					if( (Integer)returnList.get(1) == 0) //if returnList.get(1) is 0 this case continues
					{
						System.out.print("\nWhat is the album's title?  ");
						String albumTitle = keyboard.next();
						albumTitle = albumTitle.replace('-', ' '); //replaces all dashes in the string with spaces
						ArrayList<Object> albumResultList = artistTemp.findArtistsAlbumByTitle(albumTitle, 0);
						Album albumTemp =  (Album)albumResultList.get(0);//finds the album with given name in the artist's collection of albums
						if( (Integer)albumResultList.get(1) == 0)  //if albumResultList.get(1) is 0 this case continues
						{
							edit(artistTemp, albumTemp);
						}
					}
					break;
				}
				case 3: //removes an album
				{
					System.out.print("\n*** REMEMBER WHEN ENTERING INFORMATION REPLACE ALL SPACES WITH DASHES (-), " +
							"THIS IS VERY IMPORTANT ***\n");
					System.out.print("\nWhat is the artist's name?  ");
					String artistName = keyboard.next();
					artistName = artistName.replace('-', ' '); //replaces all dashes in the string with spaces
					ArrayList<Object> returnList = findArtistByName(artistName, 0);
					Artist artistTemp = (Artist)returnList.get(0);
					if( (Integer)returnList.get(1) == 0)  //if returnList.get(1) is 0 this case continues
					{
						System.out.print("\nWhat is the album's title?  ");
						String albumTitle = keyboard.next();
						albumTitle = albumTitle.replace('-', ' '); //replaces all dashes in the string with spaces
						ArrayList<Object> albumResultList = artistTemp.findArtistsAlbumByTitle(albumTitle, 0);
						Album albumTemp = (Album)albumResultList.get(0); //finds the album with given name in the artist's collection of albums
						if( (Integer)albumResultList.get(1) == 0)  //if albumResultList.get(1) is 0 this case continues
						{
							remove(artistTemp, albumTemp);
						}
					}
					break;
				}
				case 4: //shuts the program down
				{
					System.out.print("Are you sure? (y/n)  ");
					String answer = keyboard.next();
					if(answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("yes")) //if the user types the letter y or the word yes the 
						                                                               //program shuts down
					{
						saveArtist(); //backs up all the information into the text file 
						System.exit(0);
					}
					else
					{}
					break;
				}
				case 0:	//returns to the welcome() method in the Store class 
				{
					repeat = false;
					System.out.print("\n");
					break;
				}
				default: //if the number the user entered isn't one of the defined cases the user is told this
				{
					System.out.print("\nThe system did not recognize the number you entered, please try again\n");
					break;
				}
			} //ends switch statement
		}
	}
	
	/**
	 * Opens the two text files and fills the two ArrayLists in this class accordingly
	 * @throws FileNotFoundException 
	 * @throws NumberFormatException 
	 * @return okToContinue - a boolean value telling the method that called this one that it is ok to continue 
	 */
	public static boolean open()
	{
		boolean okToContinue = true; //makes a boolean object to decide if its ok to continue
		Scanner inFile1 = null;
		Scanner inFile2 = null;
		String artistTemp;
		String albumTemp;
		//open the text files for artists and albums
		try
		{
			inFile1 = new Scanner(new FileInputStream("Artist.txt"));
			inFile2 = new Scanner(new FileInputStream("Album1.txt"));
		}
		catch(FileNotFoundException fne)
		{
			System.out.println("Error found please call your FooSoft Inc. representative, the program will now turn off\n\n\n\n");
            okToContinue = false; //sets okToContinue to false signifying that it isn't ok to continue the program
            fne.printStackTrace();
		}	
		//read album text file
		while(inFile2.hasNextLine())
		{
			albumTemp= inFile2.nextLine();		    
		    
			int titlecomma = albumTemp.indexOf(',');
			int pricecomma = albumTemp.indexOf('!');
			int numTrackscomma = albumTemp.indexOf('@');
			int recordlabelcomma = albumTemp.indexOf('#');
			int locationInStorecomma = albumTemp.indexOf('$');
			int numInStockcomma = albumTemp.indexOf('%');		
			int yearOfReleasecomma = albumTemp.indexOf('|');
			int circulationcomma = albumTemp.indexOf('^');
			int star = albumTemp.indexOf("*");
			
			String albumnametemp = albumTemp.substring(0, titlecomma-1);	
			String pricesubtext = albumTemp.substring(titlecomma+2 ,pricecomma-1);
			String numtracksSubtext = albumTemp.substring(pricecomma+2 ,numTrackscomma-1);
			String recordLabelSubtext = albumTemp.substring(numTrackscomma+2 ,recordlabelcomma-1);
			String locationInStoreSubtext = albumTemp.substring(recordlabelcomma+2 ,locationInStorecomma-1);
			String numInStockSubtext = albumTemp.substring(locationInStorecomma +2 ,numInStockcomma-1);
			String yearOfReleaseSubText = albumTemp.substring(numInStockcomma +2, yearOfReleasecomma-1); 
			String songsSubtext = albumTemp.substring(yearOfReleasecomma+2 ,circulationcomma-1);
			String circulationsubstring = albumTemp.substring(circulationcomma +2,star-0);
			
			StringTokenizer song = new StringTokenizer(songsSubtext, "~");
			int tokenCount = song.countTokens();
			String[] songs = new String[tokenCount];
			 
			for (int i = 0; i < tokenCount; i++) {
			    songs[i] = song.nextToken();
			}		
			boolean circulation = true;
			if(circulationsubstring.equalsIgnoreCase("true"))
			{
				circulation = true;
			}
			else if(circulationsubstring.equalsIgnoreCase("false"))
			{
				circulation = false;
			}
			
			double price = 0;
			int numtracks = 0;
			int instock = 0;
			int yearOfRelease = 0;
			try{
				price = Double.parseDouble(pricesubtext);
				numtracks = Integer.parseInt(numtracksSubtext);
				instock = Integer.parseInt(numInStockSubtext);
				yearOfRelease = Integer.parseInt(yearOfReleaseSubText);
			}
			catch(NumberFormatException nfe) {
	            System.out.println("Error found please call your FooSoft Inc. representative, the program will now turn off\n\n\n\n");
	            okToContinue = false; //sets okToContinue to false signifying that it isn't ok to continue the program
	            nfe.printStackTrace();
	        }
			Album addAlbum = new Album(albumnametemp,price,numtracks,recordLabelSubtext,locationInStoreSubtext, instock, songs , 
					                   circulation, yearOfRelease);
			allAlbums.add(addAlbum);
		}
		for(int i =0; i<allArtists.size();i++){
			System.out.println(allArtists.get(i).getAlbumList());
		}
		
		//read artist file
		while(inFile1.hasNextLine())
		{
			artistTemp =inFile1.nextLine();
			int artistcomma = artistTemp.indexOf(",");
			String artistAlbums= artistTemp.substring(artistcomma+2);
			
			StringTokenizer albums = new StringTokenizer(artistAlbums, "-");
			int tokenCount = albums.countTokens();
			String[] album1 = new String[tokenCount];
			for (int i = 0; i < tokenCount; i++) {
			    album1[i] = albums.nextToken();
			    }	
			
			StringTokenizer artist = new StringTokenizer(artistTemp, ",");		 
		    String newArtist = artist.nextToken();
		    Artist addArtist = new Artist(newArtist);//call constructor in Artist
			
				for(int j =0;j<allAlbums.size();j++){
					for(int k =0;k<tokenCount;k++)
					{
						if(allAlbums.get(j).getAlbumTitle().equalsIgnoreCase(album1[k]))
						{
							addArtist.addToArtistsAlbums(allAlbums.get(j));
						}
					}
				
			}
			allArtists.add(addArtist);
		}
		//close files
		inFile1.close();
		inFile2.close();
		return okToContinue; 
	}		
	
		/**
		 * save into text files Artist.txt and Album1.txt
		 * @throws FileNotFoundException
		 */
		public void saveArtist()
		{
			PrintWriter outFile = null;
			 try {
				 	outFile = new PrintWriter(new FileOutputStream("Artist.txt"));
				 	//write into file			
				 	for(int i =0; i< allArtists.size(); i++)
				 	{
				 		new PrintWriter(outFile).println(allArtists.get(i).getArtistName()+", "+ allArtists.get(i).getAlbumList() + " *");
				 	}
			 }
			 catch (FileNotFoundException fnfe) 
			 {
				System.out.println("Error found please call your FooSoft Inc. representative, the program will now turn off\n\n\n\n");
				fnfe.printStackTrace();
			 }
			finally
			{
				outFile.close(); 
			}
			PrintWriter outFile2= null;
			 try 
			 {
				 outFile2 = new PrintWriter(new FileOutputStream("Album1.txt"));
				{
					for(int j =0; j< allAlbums.size(); j++)
					{
						new PrintWriter(outFile2).println(allAlbums.get(j).getAlbumTitle()+ " , " + allAlbums.get(j).getPrice() + " ! " + 
												allAlbums.get(j).getNumTracks() + " @ " + allAlbums.get(j).getRecordLabel() + " # " +
												allAlbums.get(j).getLocationInStore() + " $ " + allAlbums.get(j).getNumInStock() + " % " +
												allAlbums.get(j).getReleaseYear() + " | " + allAlbums.get(j).returnSongTitles() + " ^ " + 
												allAlbums.get(j).checkIfCirculation()+ "*");
					}
				}
			 } 
			 catch (FileNotFoundException fnfe)
			 {
				System.out.println("Error found please call your FooSoft Inc. representative, the program will now turn off\n\n\n\n");
				fnfe.printStackTrace();
			 }
			 finally
			 {
				 outFile2.close();
			 }
			System.out.println("\nThe changes have been saved.");
		}
	}