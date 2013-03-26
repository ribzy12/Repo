
/**
 * 
 * @author Jeff Schumacher
 */
public class TeamBuilder
{
	/**
	 * Given some example groups of paths prints out the results of specialLocations method
	 * @param args
	 */
	public static void main(String[] args)
	{
		System.out.println("example 0)");
		int[] example = specialLocations(new String[]{"010","000","110"});
		System.out.println("  results are (" + example[0] + "," + example[1] + ")");
		
		System.out.println("example 1)");
		example = specialLocations(new String[]{"0010","1000","1100","1000"});
		System.out.println("  results are (" + example[0] + "," + example[1] + ")");
		
		example = specialLocations(new String[]{"01000","00100","00010","00001","10000"});
		System.out.println("example 2)");
		System.out.println("  results are (" + example[0] + "," + example[1] + ")");
		
		System.out.println("example 3)");
		example = specialLocations(new String[]{"0110000","1000100","0000001","0010000","0110000","1000000","0001000"});
		System.out.println("  results are (" + example[0] + "," + example[1] + ")");
	}
	
	/**
	 * Walks through the given paths to calculate the number of locations that can reach all others and the 
	 * number of locations that are reachable by all other locations
	 * 
	 * @param paths array of paths
	 * @return int[] where [0] is the number of locations that can reach all others and [1] is the 
	 * 		   number of locations that are reachable by all other locations
	 */
	public static int[] specialLocations(String[] paths)
	{
		int canReachAllPaths = 0; //number of locations that can reach all others
		int isReachableByAllPaths = 0; //number of locations that are reachable by all other locations
		
		try
		{
			if(paths.length < 2 || paths.length > 50) //Checks to make sure there are between 2 and 50 paths
			{
				throw new PathException("There are not between 2 and 50 elements");
			}
			else
			{	
				char[][] pathElements = new char[paths.length][];
				char[] reachedPath = new char[paths.length];
				for(int m = 0; m < pathElements.length; m++) //Steps through all paths
				{
					/* If at the end reachedPath[p] still = 'Y' that location p is reachable by all others, this will be used later*/
					reachedPath[m] = 'Y';
					
					char[] location = paths[m].toCharArray();
					
					if(location[m] != '0') //ensures the i-th location contains a zero in the i-th position
					{
						throw new PathException("location " + m + " doesn't have a '0' at the " + m + "th position");
					}
					else if(location.length == pathElements.length)//Checks to make sure the number of locations 
						                                           //equals the number of paths
					{
						pathElements[m] = location;
					}
					else
					{
						throw new PathException("location " + m + " doesn't contain the correct number of elements");
					}
				}
				
				int[][] pathsReached = new int[paths.length][paths.length];
	
				for(int i = 0 ; i < paths.length; i++) //Steps through each path and calcultes which paths it reaches
				{
					pathsReached[i] = stepThroughAllPaths(i,i,pathsReached[i],pathElements,paths.length);
				}
				
				for(int n = 0; n < pathsReached.length; n++)
				{
					int[] inner = pathsReached[n];
					int locationsReached = 0; //The number of locations this one reached
					for(int z = 0; z < inner.length; z++)
					{
						/* Calculates the number of locations reached and sets the flags in reachedPath */
						if(n == z || (n != z && inner[z] > 0)) //We expect if n = z that inner[z] will be 0 so we say this location was reached
															   //If  n != z and inner[z] > 0 than this location can reach location
						{
							locationsReached++;
						}
						else
						{
							reachedPath[z] = 'N';
						}
					}
					if(locationsReached == inner.length)//This location can reach all others
					{
						canReachAllPaths++;
					}
				}
				
				for(int y = 0; y < reachedPath.length; y++)//Calculates which paths are reachable by all others
				{
					if(reachedPath[y] == 'Y')//The location at y is reachable by all 
					{
						isReachableByAllPaths++;
					}
				}
			}
		}
		catch(PathException pe)//A PathException is caught because a constraint failed somewhere
		{
			pe.printStackTrace();
			System.exit(1);
		}
		catch (Exception e) //Any other exception was caught
		{
			e.printStackTrace();
			System.exit(1);
		}
		
		return new int[]{canReachAllPaths,isReachableByAllPaths};
	}
	
	/**
	 * Takes in a what location to start with and starting at that path calculates all location this location can reach
	 * 
	 * @param startPath path number we started at
	 * @param position position we started at
	 * @param pathsReached which paths this path reached
	 * @param allPathElements the elements of all paths
	 * @param totalPaths the total number of paths
	 * @return pathsReached after it is manipulated
	 * @throws PathException PathExpection if a constraint failed
	 */
	private static int[] stepThroughAllPaths(
			int startPath,
			int position,
			int[] pathsReached, 
			char[][] allPathElements,
			int totalPaths
		) throws PathException 
	{
		char[] currentPath = allPathElements[position];
		for(int i = 0; i < totalPaths; i++)
		{
			if(startPath != i)// Ensures we don't return to the path we started at
			{
				if(currentPath[i] == '1')//if currentPath[i] is '1' than we can move onto the location at currentPath[i]
				{
					if(pathsReached[i] != 1) // Makes sure we only make each location as being reached once
					{
						pathsReached[i]++;
						
						/* We will now step through the location at position i */
						pathsReached = stepThroughAllPaths(startPath,i,pathsReached,allPathElements,totalPaths);
					}
				}
				else if(currentPath[i] != '1' && currentPath[i] != '0')//Checks for any non '0' or '1' elements
				{
					throw new PathException("location " + i + " contains a non '0' or '1' element");
				}
			}
		}
		return pathsReached;
	}
}

/**
 * Custom exception that will be thrown if any of the required constraints fail
 * @author Jeff Schumacher
 */
class PathException extends Exception
{
    public PathException(String msg) {
        super(msg);
    }
}