package MUSIC;
import java.util.Scanner;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Holds all the information for one album
 * 
 * @author J Schumacher, J Gil
 * @version 2.8
 */
public class Album 
{
	private String locationInStore = "";  //the albums location in the store
	private String recordLabel = "";      //the record label that produced the album 
	private double price = 0;             //the price of the album
	private int numInStock = 0;           //the number of copies of this album the store has in stock
	private int numTracks = 1;            // the number of tracks the album has
	private String songTitles[];          //an array containing all the titles of the songs on the album
	private String albumTitle = "";       //the album's title
	private boolean inCirculation = true; //whether or not the album is still in circulation
	private int yearOfRelease = 0;
	
	/**
	 * Creates a new album and fills in all of thats albums variables
	 * @param locationInStore - the albums location in the store
	 * @param recordLabel - the record label that produced the album 
	 * @param price - the price of the album
	 * @param numInStock - the number of copies of this album the store has in stock 
	 * @param numTracks - the number of tracks the album has
	 * @param albumTitle - the album's title
	 * @param inCirculation - whether or not the album is still in circulation
	 * @param songTitles[] - array of the songTitles 
	 */
	public Album(String albumTitle, double price, int numTracks, String recordLabel,  String locationInStore, 
			int numInStock, String[] songTitles, boolean inCirculation, int yearOfRelease) 
	{
		this.locationInStore = locationInStore;
		this.recordLabel = recordLabel;
		this.price = price;
		this.numInStock = numInStock;
		this.numTracks = numTracks;	
		this.albumTitle = albumTitle;
		this.inCirculation = inCirculation;
		this.yearOfRelease = yearOfRelease;
		if (songTitles == null) 
		{
			this.songTitles = new String[numTracks];
		}
		else 
		{
			this.songTitles = songTitles;
		}
	}
	
	/**
	   * Returns the albums location in the store 
	   * @return locationInStore - the albums location in the store   
	   */
	public String getLocationInStore()
	{
		return locationInStore;
	}
	
	/**
	   * Sets the albums location in the store
	   * @param locationInStore - the albums location in the store   
	   */
	public void setLocationInStore(String locationInStore)
	{
		this.locationInStore = locationInStore;
	}
	
	/**
	   * Returns the record label that produced the album
	   * @return recordLabel - the record label that produced the album   
	   */
	public String getRecordLabel()
	{
		return recordLabel;
	}
	
	/**
	   * Sets the name of the record label that produced the album
	   * @param recordLabel - the record label that produced the album   
	   */
	public void setRecordLabel(String recordLabel)
	{
		this.recordLabel = recordLabel;
	}
	
	/**
	   * Returns the albums price 
	   * @return price - the price of the album   
	   */
	public double getPrice()
	{
		return price;
	}
	
	/**
	   * Sets the albums price 
	   * @param price - the price of the album   
	   */
	public void setPrice(double price)
	{
		this.price = price;
	}
	
	/**
	   * Returns the number of copies of this album the store has in stock
	   * @return numInStock - the number of copies of this album the store has in stock   
	   */
	public int getNumInStock()
	{
		return numInStock;
	}
	
	/**
	   * Sets the number of copies of this album the store has in stock
	   * @param numInStock - the number of copies of this album the store has in stock   
	   */
	public void setNumInStock(int numInStock)
	{
		this.numInStock = numInStock;
	}
	
	/**
	   * Returns the number of tracks the album has
	   * @return numTracks - the number of tracks the album has   
	*/
	public int getNumTracks()
	{
		return numTracks;
	}
	
	/**
	   * Prints all the song titles of all the tracks on the album   
	   */
	public void printSongTitles()
	{
		//steps through the array of song titles and prints out each song title one to a line
		for(int i = 0; i < songTitles.length; i++)
		{ 
			if(i < 9)
			{
				System.out.print("               Track " + (i+1) + ":   \""  + songTitles[i] + "\"\n");
			}
			else if(i >= 9)
			{
				System.out.print("               Track " + (i+1) + ":  \""  + songTitles[i] + "\"\n");
			}
		}
	}
	
	/**
	 * Returns the array of song titles 
	 * @return songTitles - the songTitles array containing all the song titles 
	 */
	public String[] returnSongTitlesArray()
	{
		return songTitles;
	}
	
	/**
	 * Makes a string that has all of the song titles in it
	 * @return a string that has all the songs in it
	 */
	public String returnSongTitles()
	{
		String allSongs = "";
		for(int i = 0; i < songTitles.length; i++)
		{
			allSongs = allSongs + songTitles[i] + "~";
		}
		return allSongs; //string containing all the songs
	}
	
	/**
	 * Changes given track's title to the given title
	 * @param trackNum - The track number of the song that is to be changed
	 * @param newTitle - the new track title for the given track
	 */
	public void setOneSongTitle(int trackNum, String newTitle)
	{			
		if(trackNum < 0 || trackNum >= songTitles.length)
		{
			System.out.print("\nThe track you wish to change is not on the album, try again\n");
		}
		else 
		{
			songTitles[trackNum] = newTitle;
		}
	}
	
	/**
	 * Sets the song titles for all the songs by asking to the user to input the names
	 * @param keyboard - a Scanner object 
	 */
	public void setAllSongTitles(Scanner keyboard)
	{
		int j;
		for(int i = 0; i < songTitles.length; i++)
		{
			j = i+1;
			if(i < 9)
			{
				System.out.print("What is the song title of track  " + j + "? " );
			}
			else if(i >= 9)
			{
				System.out.print("What is the song title of track " + j + "? " );
			}
			
			String newSongTitle = keyboard.next();
			newSongTitle =  newSongTitle.replace('-', ' '); //replaces all dashes in the string with spaces
			songTitles[i] = newSongTitle;
			System.out.print("");
		}
	}
	
	/**
	 * Returns the album title
	 * @return albumTitle - the album's title
	 */
	public String getAlbumTitle()
	{
		return albumTitle;
	}
	
	/**
	 * Sets the album title to the given title
	 * @param albumTitle
	 */
	public void setAlbumTitle(String albumTitle)
	{
		this.albumTitle = albumTitle;
	}
	
	/**
	 * Returns the album's year of release
	 * @return yearOfRelease - the year this album was released
	 */
	public int getReleaseYear()
	{
		return yearOfRelease;
	}
	
	/**
	 * Sets the album's year of release
	 * @param yearOfRelease - the year this album was released
	 */
	public void setReleaseYear(int yearOfRelease)
	{
		this.yearOfRelease = yearOfRelease;
	}
	
	/**
	 * Returns whether or not the album is in circulation
	 * @return inCirculation - whether or not the album is still in circulation
	 */
	public boolean checkIfCirculation()
	{
		return inCirculation;
	}
	
	/**
	 * Sets the circukation value of the album
	 * @param inCirculation - whether or not the album is still in circulation
	 */
	public void setCirculation(boolean inCirculation)
	{
		this.inCirculation = inCirculation;
	}
	
	/**
	 * Prints all the album's information out at once
	 */
	public void printAllTheAlbumsInfo()
	{
		NumberFormat moneyFormatter = NumberFormat.getCurrencyInstance(Locale.US);  
		System.out.print("                  The album title is:  \"" + albumTitle + "\"\n"); //prints out the album's title
		System.out.print("                 The record label is:  " + recordLabel + "\n"); //the album's record label  
		System.out.print("          This album was released in:  " + yearOfRelease + "\n"); 
		
		//checks to see if the albums is still in circulation
		if(inCirculation == true)  
		{
			//prints out the price with the correct decimal places
			System.out.print("                        The price is:  " + moneyFormatter.format(price)  + "\n"); 

			//checks to see if the album is in stock at store
			if(numInStock > 10)
			{
				//if in stock prints out the album's location in the store
				System.out.print("   Section of store this album is in:  " + locationInStore + "\n\n");   
			}
			else if (numInStock <= 10 && numInStock !=0 )
			{	//if in stock prints out the album's location in the store	
				System.out.print("   Section of store this album is in:  " + locationInStore + "\n");   	
				
				//if numStock is between 0 and 10 prints a messages to the user saying they should buy the album soon
				System.out.print("\n   *** There is only " + numInStock + " copy of this album left at Music Plex, if you are interested in " +
						"buying it you should buy it before we sell out!!!!! ***\n\n");  
			}
			else if(numInStock == 0)
			{
				//if not in stock prints instructions to the customer on how they can have the album ordered
				System.out.print("  \nThis album isn't currently in stock at MusicPlex," +
						"You may go to the customer service desk \nto ask when it will be in stock again " +
						"or how the store can order this album for you\n\n");
			}
		}
		else if(inCirculation == false)//if not in circulation prints a message telling the customer that
		{
			System.out.print("\n     *******This album is no longer in circulation*******\n\n");
		}
		
		System.out.print("   This album's tracks are the following:\n");
		printSongTitles();	//prints all the song titles to the screen
	}
	
	/**
	 * Takes an album object and checks to see if it is equal to this one 
	 * @param albumTemp - a reference to another album object
	 * @return boolean - whether or not the given album is equal to this one
	 */
	public boolean equals(Album albumTemp)
	{
		//call the equals method on all variables
		return (this.albumTitle.equalsIgnoreCase(albumTemp.getAlbumTitle()) 
			&& this.recordLabel.equalsIgnoreCase(albumTemp.getRecordLabel()) && this.price == albumTemp.getPrice()
			&& this.numInStock == albumTemp.getNumInStock() && this.numTracks == albumTemp.getNumTracks()
			&& this.inCirculation == albumTemp.checkIfCirculation());
	}	
}