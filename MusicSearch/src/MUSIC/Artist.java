package MUSIC;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;


/**
 * Holds all the information for a given artist's
 * 
 * @author J Schumacher
 * @version 1.6
 */
public class Artist 
{
	private String artistName;                                       //the artist's name
	public ArrayList<Album> artistsAlbums = new ArrayList<Album>();  //Collection of all this artist's albums
	Scanner keyboard = new Scanner(System.in);
	
	/**
	 * Creates a new artist and sets the artist's name
	 * @param artistName
	 */
	public Artist(String artistName)
	{
		this.artistName = artistName;
	}
		
	/**
	 * Returns the artist's name 
	 * @return artistName - artist's name
	 */
	public String getArtistName() 
	{
		return artistName;
	}
	
	/**
	 * Sets the artist's name 
	 * @param artistName - artist's new name
	 */
	public void setArtistName(String artistName) 
	{
		this.artistName = artistName;
	}
	
	/**
	 * Returns the reference this artist's ArrayList of albums
	 * @return artistsAlbums - the collection of the artist's albums
	 */
	public ArrayList<Album> getArtistsAlbums()
	{
		return artistsAlbums;
	}
	
	/**
	 * Adds an album to this artist's ArrayList of albums
	 * @param albumTemp - an album reference to an album to add to this artist's collection of album's 
	 */
	public void addToArtistsAlbums(Album albumTemp)
	{
		artistsAlbums.add(albumTemp);
	}

	/**
	 * Returns the collection of alll the artist's albums
	 * @return albums - the collection of all the Artist's albums
	 */
	public String getAlbumList()
	{
		String albums="";
		for(int index=0;index<artistsAlbums.size();index++)
		{
			
			Album albumsTemp = artistsAlbums.get(index);
			albums = albums+ albumsTemp.getAlbumTitle()+ "-" ;
		}
		return albums;
	}
	
	/**
	 * Deletes the albumTemp from the artist's collection of albums
	 * @param albumTemp - a reference to the album that is to be deleted from the collection of albums
	 */
	public void deleteFromArtistsAlbums(Album albumTemp)
	{
		artistsAlbums.remove(albumTemp);
	}
	
	/**
	 * Tries to find an album with the that matches all the words given for the title in the artist's collection of albums
	 * @param albumTitle - the album title of the album that is be located
	 * @param okReturn - whether or not it is ok to return null if no album can be found
	 * @return returnList - ArrayList of type Object containing the chosen album and the stop variable 
	 */
	public ArrayList<Object> findArtistsAlbumByTitle(String albumTitle, int okReturn)
	{
		ArrayList<Object> returnList = new ArrayList<Object>(); //this is what the method will return
		Album albumTemp = null;
		int stop = 0;    //this variable will be used later
		ArrayList<Album> allAlbumMatches = new ArrayList<Album>();   //ArrayList of all the album matches that are found
		StringTokenizer albumTitleString = new StringTokenizer(albumTitle); 
		String elementsOfAlbumTitle[] = new String[albumTitleString.countTokens()];  
		int count = 0;
		
		/** makes an array that contains all the words the user entered as the album title **/
		while(albumTitleString.hasMoreTokens())
		{
			elementsOfAlbumTitle[count] = albumTitleString.nextToken();
			count++;
		}
		
		//steps through the collection of all albums trying to find an album that matches what the user typed for album title
		for(int i = 0; i < artistsAlbums.size(); i++)
		{
			StringTokenizer thisAlbumsTitleString = new StringTokenizer(artistsAlbums.get(i).getAlbumTitle()); 
			int resultCount = 0;  //counts how many matches the album has to what the user typed
			
			while(thisAlbumsTitleString.hasMoreTokens()) 
			{
				String temp = thisAlbumsTitleString.nextToken();
				for(int j = 0; j < elementsOfAlbumTitle.length; j++)
				{
					if(elementsOfAlbumTitle[j].equalsIgnoreCase(temp)) //if elementsOfAlbumTitle[j] equals next token of temp increment resultCount 
					{
						resultCount++;
					}
				}
			}
			if(resultCount == elementsOfAlbumTitle.length) //if true then the this album matches every word of albumTitle
			{
				allAlbumMatches.add(artistsAlbums.get(i)); //add this album to allAlbumMatches
			}
		}
		if(allAlbumMatches.size() > 1) //There was more than one artist match
		{
			boolean repeat = true;
			while(repeat)
			{
				try 
				{
					System.out.print("\nThese are all the possible album that match what you entered\n");
					int num = 0;
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
						albumTitle = keyboard.next();
						albumTitle = albumTitle.replace('-', ' '); //replaces all underscores in the string with spaces
						findArtistsAlbumByTitle(albumTitle, okReturn);
					}
					else if(answer == (num+2))
					{
						stop = 1; //tells the method that called it to stop
					}
					else
					{
						albumTemp = allAlbumMatches.get(keyboard.nextInt() - 1);
					}
					repeat = false;
				}
				catch (IndexOutOfBoundsException e) 
				{
					System.out.print("\nThe number you entered was not one on the options you were given please try again\n");
				}
			}		
		}
		else if(allAlbumMatches.size() == 1) //there was only one album match 
		{ 
			System.out.print("\nThe closest album match to what you typed is: \"" + allAlbumMatches.get(0).getAlbumTitle() + "\"\n");
			albumTemp = allAlbumMatches.get(0);
		}
		else if(allAlbumMatches.size() < 1) //there was zero album matches
		{
			if(okReturn == 0)  //the method that called it doesn't want to get null back
			{
				System.out.print("\nNo possible album matches were found, would you like to try again? (yes or no)\n");
				String result = keyboard.next(); 	
				if(result.equalsIgnoreCase("yes") || result.equalsIgnoreCase("y"))
				{
					System.out.print("\nWhat is the artist's name?  ");
					String AlbumTitle = keyboard.next();
					AlbumTitle = AlbumTitle.replace('-', ' '); //replaces all underscores in the string with spaces
					findArtistsAlbumByTitle(AlbumTitle, okReturn);
				}
				else
				{
					stop = 1;  //tells the method that called it to stop
				}
			}
			else if(okReturn == 1)  //the method that called it doesn't mind receiving null back
			{
				albumTemp = null;
			}
		}
		returnList.add(albumTemp);  //add the chosen album to returnList
		returnList.add(stop);  //add the value of stop to returnList
		return returnList;  //a reference to the album with the given title or null if it cannot be found
	}
	
	/**
	 * Takes an artist object and checks to see if it is equal to this one 
	 * @param artistTemp - a reference to another artist object 
	 * @return - whether or not the given artist is equal to this one
	 */
	public boolean equals(Artist artistTemp)
	{
		return this.artistName.equalsIgnoreCase(artistTemp.getArtistName());
	}
}