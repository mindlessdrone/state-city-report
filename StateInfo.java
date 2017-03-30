
/**********************************************************
 * Class Name     : StateInfo
 * Author         : Shao yu Cheng, Anthony Massacci
 * Date           : Mar 31 , 2017
 * Course/Section : CSC 264 - 001
 * Program Description: The class will store the state's information.
 *
 * Methods: + stateInfo(String stateName) 	                : none
 *			+ setName(String stateName)                : void
 *			+ getName()                     	        : String
 *			+ getNumCities()                                : int
 *			+ getPop()			                        : int
 *		    + addCities(String name,int pop)		        : void
 *		    + removeCities(int index)                       : void
 *		    + modifyCity(String name, int pop, int index)   : boolean
 *		    + sortCities(int choice)                        : void
 *		    + toString()                                    : String
 *
 **********************************************************/
public class StateInfo 
{
   //local constants
   final static int CITIES_MAX = 30;

   //local variables
   String stateName;
   int statePop = 0;
   private int numCities = 0;
   private int stateCount = 0;
   private cityInfo city[];


   public StateInfo(String name) {
      stateName = name;
      city = new cityInfo[CITIES_MAX];
   }

   /**********************************************************
    * Method Name    : setName
    * Author         : Shao yu Cheng
    * Date           : Mar 31 , 2017
    * Course/Section : CSC 264 - 001
    * Program Description: To reset the state's name.
    *
    * BEGIN setCityName
    *    local state name = new state name
    * END setCityName
    **********************************************************/
   public void setName(String stateName)
   {
      this.stateName = stateName;  //local state name = new state name
   } //END setCityName

   /**********************************************************
    * Method Name    : getName
    * Author         : Shao yu Cheng
    * Date           : Mar 31 , 2017
    * Course/Section : CSC 264 - 001
    * Program Description: To return the state's name.
    *
    * BEGIN getName
    *    return the state's name
    * END getName
    **********************************************************/
   public String getName()
   {
      return stateName;  //return the state's name
   } //END getName

   /**********************************************************
    * Method Name    : getNumCities
    * Author         : Shao yu Cheng
    * Date           : Mar 31 , 2017
    * Course/Section : CSC 264 - 001
    * Program Description: To return how many cities in the state.
    *
    * BEGIN getNumCities
    *    return how many cities in the state
    * END getNumCities
    **********************************************************/
   public int getNumCities()
   {
      return numCities; //return how many cities in the state
   } //END getNumCities

   /**********************************************************
    * Method Name    : getPop
    * Author         : Shao yu Cheng
    * Date           : Mar 31 , 2017
    * Course/Section : CSC 264 - 001
    * Program Description: To return the state's total population.
    *
    * BEGIN getPop
    *    FOR()
    *       add city's population to total state population
    *    EDN FOR
    *    return state's total population
    * END getPop
    **********************************************************/
   public int getPop()
   {
      //FOR()
      for (int i = 0; i < numCities; i++)
      {
         //add city's population to total state population
         statePop = city [i].getPop() + statePop;
      } //EDN FOR

      return statePop;  //return state's total population
   } //END getPop

   /**********************************************************
    * Method Name    : addCity
    * Author         : Anthony Massicci
    * Date           : March 31, 2017
    * Course/Section : CSC264
    * Program Description: Add city to array, returns true if
    * city was successfully added or modified, false if not.
    * 
    * BEGIN addCity(name, population)
    *    index = 0
    *    cityAdded = false
    *    WHILE (name is not city[i].name and index is less than numCities)
    *       Increment index
    *    END WHILE
    *    IF (city was not found)
    *       IF (city is not full)
    *          Create a new city object at index
    *          IF (more than one city in cities)
    *              Call sortAddedCity(index)
    *          END IF
    *          Update state population
    *          Increment numCities
    *          cityAdded = true
    *       END IF
    *    ELSE
    *       cityAdded = modifyCity(index, name, population)
    *    END IF
    *    Return cityAdded
    * END addCity
    **********************************************************/

   public boolean addCity(String name, int population)
   {
      // local constants

      //local variables
      int index = 0;             // current index of city
      boolean cityAdded = false; // flag to return whether city has been added

      /********************   Start addCity method  *****************/

      // perform linear search to find if city is already in array
      while (index < numCities && !name.equals(city[index].getName()))
      {
         // increment index
         index++;

      } // end while

      // if city was not found lets try to add it
      if (city[index] == null)
      {
         // only add the city if array is not full
         if (numCities < CITIES_MAX)
         {
            // create a new city object
            city[index] = new cityInfo(name, population);

            // increment numCities
            numCities++;

            // check to see if array needs sorting
            if (numCities > 1)
            {
               // sort the city into the array
               sortCity(index);
            }

            // update state population
            statePop += population;

            // set added city flag to true
            cityAdded = true;

         } // end if
      }
      else // a city was found
      {
         // modify city at index
         modifyCity(index, name, population);

      } // end if

      // return city added flag
      return cityAdded;

   } //end addCity method

   /**********************************************************
    * Method Name    : modifyCity
    * Author         : Anthony Massicci
    * Date           : March 31, 2017
    * Course/Section : CSC264
    * Program Description: Modifies city at specified index.
    * Returns true if a city was sucessfully modified,
    * otherwise false.
    *
    * BEGIN modifyCity(index, name, pop)
    *    cityModified = false
    *    IF (index is valid)
    *       Update state population
    *       Create new city object at index with name and pop
    *       Call sortCity(index)
    *       cityModified = true
    *    END IF
    * END modifyCity
    **********************************************************/
   public boolean modifyCity(int index, String cityName, int pop)
   {
      //local constants

      //local variables
      boolean cityModified = false; // flag to tell if a city was successfully modified

      /********************   Start modifyCity method  *****************/

      // if index is valid modify selected city
      if (index < numCities)
      {
         // update state population
         statePop -= city[index].getPop();
         statePop += pop;

         // create city object with new information
         city[index] = new cityInfo(cityName, pop);

         // restore order of array
         sortCity(index);

         cityModified = true;
      } // end if 

      // return cityModified
      return cityModified;

   } //end modifyCity method

   /**********************************************************
    * Method Name    : sortCity
    * Author         : Anthony Massicci
    * Date           : March 31, 2017
    * Course/Section : CSC264
    * Program Description: Online insertion sort, places city
    * at specified position into the correct position in array.
    *
    * BEGIN sortCity(pos)
    *    key = city at pos
    *    index = pos
    *    WHILE (index is not at left most position and
    *           and key is less than city on left)
    *       Move city on left to current index
    *       Decrement index
    *    END WHILE
    *    WHILE (index is not at right most position and
    *           and key is greater than city on right)
    *       Move city on right to current index
    *       Increment index
    *    END WHILE
    *    city[index] = key
    * END sortCity
    **********************************************************/
   private void sortCity(int pos)
   {
      //local constants

      //local variables
      cityInfo key = city[pos];        // city to be sorted
      int index = pos;                 // current position

      /********************   Start sortCity method  *****************/

      // while key is not sorted or we have not reached the end of array
      while (index > 0 && key.getPop() < city[index-1].getPop())
      {
         // move city to left at current index
         city[index] = city[index-1];

         // Decrement index
         index--;

      } // end while

      while (index < numCities - 1 && key.getPop() > city[index+1].getPop())
      {
         // move city to right to current index
         city[index] = city[index+1];

         // increment index
         index++;
      }

      // place key at current index
      city[index] = key;

   } //end sortCity method

   /**********************************************************
    * Method Name    : removeCities
    * Author         : Shao yu Cheng
    * Date           : Mar 31 , 2017
    * Course/Section : CSC 264 - 001
    * Program Description: To remove the city from array.
    *
    * BEGIN removeCities
    *    decrease state population by city population
    *    remove city from array
    *    FOR()
    *       move cities forward
    *    END FOR
    *    reduce 1 from number of cities
    * END removeCities
    **********************************************************/
   public void removeCities(int index)
   {
      // decrease state population by city population
      statePop -= city[index].getPop();

      //remove city from array
      city [index] = null;

      //FOR()
      for (int i = index; i < numCities; i++)
      {
         //move cities forward
         city [i] = city [i+1];
      } //END FOR
   } // end removeCities

   /**********************************************************
    * Method Name    : toString
    * Author         : Anthony Massicci
    * Date           : March 31, 2017
    * Course/Section : CSC264
    * Program Description: Returns string representation of
    * class. Returns state name along with each city name
    * and population. 
    *
    * BEGIN toString
    *    Append state name to str
    *    FOR (each city)
    *       Append city name and population to str
    *    END FOR
    *    Return str
    * END toString
    **********************************************************/
   public String toString() 
   {
      // local constants
      // local variables
      String str;    // built string to be returned
      /****************Start toString method****************/ 

      // append state name to str
      str = getName() + "\n";

      // for each city 
      for(int i = 0; i < numCities; i++) 
      {
         // append city name and population to str
         str += city[i] + "\n";

      } // end for

      // return str
      return str;
   } // end toString
} // end StateInfo
