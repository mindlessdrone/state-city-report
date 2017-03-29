/**
 * Created by Shelly on 2017/3/10.
 */
import java.util.Arrays;
import java.util.Comparator;

public class stateInfo {

    //local constants
    final static int CITIES_MAX = 30;
    final static int BY_NAME = 1;
    final static int BY_POP =2;

    //local variables
    String stateName;
    int statePop = 0;
    private int numCities = 0;
    private int stateCount = 0;
    private cityInfo city[];


    public stateInfo(String name) {
        stateName = name;
        city = new cityInfo[CITIES_MAX];
    }

    public stateInfo(String stateName, int statePop) {
        this.stateName = stateName;
        this.statePop = statePop;
    }

    public void setName(String stateName) {
        this.stateName = stateName;
        stateCount++;
    }

    public String getName() {
        return stateName;
    }

    public cityInfo getCity(int choice) {
        return city[choice-1];
    }

    public int getNumCities() {
        return numCities;
    }

    public int getPop() {
        return statePop;
    }

   /**********************************************************
    * Method Name    : addCity
    * Author         : Anthony Massicci
    * Date           : xxxxxxxxxxxxxxxx
    * Course/Section : CSC264
    * Program Description: xxxxxx
    *
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
    *       Update state population
    *       Update city population
    *       Call sortAddedCity(index)
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
    * Date           : xxxxxxxxxxxxxxxx
    * Course/Section : CSC264
    * Program Description: xxxxxx
    *
    * PSEUDOCODE - xxxxxxxxx
    **********************************************************/

   public boolean modifyCity(int index, String cityName, int pop)
   {
      //local constants

      //local variables
      boolean cityModified = false;

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

      return cityModified;
   } //end modifyCity method

   /**********************************************************
    * Method Name    : sortCity
    * Author         : Anthony Massicci
    * Date           : xxxxxxxxxxxxxxxx
    * Course/Section : CSC264
    * Program Description: Online insertion sort, places city
    * at specified position into the correct position in array.
    *
    * PSEUDOCODE - xxxxxxxxx
    **********************************************************/

   private void sortCity(int pos)
   {
      //local constants

      //local variables
      cityInfo key = city[pos];       // city to be sorted
      int index = pos;           // current position

      /********************   Start sortCity method  *****************/
      
      // while key is not sorted or we have not reached the end of array
      while (index > 0 && key.getPop() < city[index-1].getPop())
      {
         // move city to left at current index
         city[index] = city[index-1];

         // Decrement index
         index--;

      } // end while

      while (index < numCities && key.getPop() > city[index+1].getPop())
      {
         // move city to right to current index
         city[index] = city[index+1];

         // increment index
         index++;
      }

      // place key at current index
      city[index] = key;
   } //end main method

    public void removeCities(int index) {
        city [index] = null;
        for (int i = index; i < numCities; i++){
            city [i] = city [i+1];
        }
        numCities--;
    }

    public String toString() {
        String str = "";
        for(int i = 0; i < numCities; i++) {
            str += city[i].toString() + "\n";
        }
        return str;
    }
}
