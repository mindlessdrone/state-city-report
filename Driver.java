import java.io.*;

/**********************************************************
 * Program Name   : States-citiesIO
 * Author         : Shao yu Cheng, Anthony Massacci
 * Date           : Mar 31 , 2017
 * Course/Section : CSC 264 - 001
 * Program Description: The program will ask user to insert
 *    a file which writes States, cities, and city's population.
 *    List of menu to ask user if wants to do any changes, then
 *    the program will write a file report, contain States, cities,
 *    State's population and city's population.
 *
 *
 * Methods: 
 *       + menu() 								                         : int
 *       + readFile(int numStates, stateInfo states[])             : int
 *			+ listStates(int numState, stateInfo states[])	          : void
 *			+ modState(int numState, stateInfo states[])	             : void
 *			+ modCity(int numState, stateInfo states[])               : void
 *			+ rmvState(int numState, stateInfo states[])	             : int
 *		   + rmvCity(int numState, stateInfo states[])               : void
 *		   + sortState(stateInfo states[], int pos, int numStates)   : void
 *		   + proReport(int numState, stateInfo states[])             : void
 *
 **********************************************************/
public class Driver 
{
   /**********************************************************
    * Method Name    : main
    * Author         : Shao yu Cheng
    * Date           : Mar 31 , 2017
    * Course/Section : CSC 264 - 001
    * Program Description: The main method, call the methods to use.
    *
    * BEGIN main
    *    call menu method and get returned choice
    *    WHILE (choice is not quit)
    *       IF (is to read file)
    *          call read file method and get returned number of states
    *       ELSE IF (is to modify states)
    *          call modify states method
    *       ELSE IF (is to modify city)
    *          call modify city method
    *       ELSE IF (is to remove states)
    *          call remove states method
    *       ELSE IF (is to remove city)
    *          call remove city method
    *       ELSE IF (is to sort states)
    *          call sort states method
    *       ELSE IF (is to sort city)
    *          call sort cities method
    *       ELSE IF (is to produce report)
    *          call produce report method
    *       ELSE
    *          output invalid choice msg
    *       END IF
    *       call menu method and get returned choice
    *    END WHILE
    *    output closing program msg
    * END main
    **********************************************************/
   public static void main(String[] args) throws Exception
   {
      //local constants
      final int QUIT           = 0;   //to quit the program
      final int READ_FILE      = 1;   //to read an external text file
      final int MOD_STATE      = 2;   //to modify state, change state name
      final int REMOVE_STATE   = 3;   //to remove state from the array
      final int REMOVE_CITY    = 4;   //to remove city from the array
      final int PRODUCE_REPORT = 5;   //to write the information to a file
      final int STATES_MAX     = 50;  //states' maximum of the state array

      //local variables
      int choice;                                         //menu choice of user
      int numStates      = 0;                             //count how many states
      StateInfo[] states = new StateInfo[STATES_MAX];     //array to hold 50 states

      /***************************************************************/

      //call menu method and get returned choice
      choice = menu();

      //WHILE (choice is not quit)
      while (choice != QUIT)
      {
         //IF (is to read file)
         if (choice == READ_FILE)
         {
            //call read file method and get returned number of states
            numStates = readFile(numStates,states);
         }

         //ELSE IF (is to modify states)
         else if (choice == MOD_STATE)
         {
            //call modify states method
            modState(numStates,states);
         }

         //ELSE IF (is to remove states)
         else if (choice == REMOVE_STATE)
         {
            //call remove states method
            numStates = rmvState(numStates,states);
         }

         //ELSE IF (is to remove city)
         else if (choice == REMOVE_CITY)
         {
            //call remove city method
            rmvCity(numStates,states);
         }

         //ELSE IF (is to produce report)
         else if (choice == PRODUCE_REPORT)
         {
            //call produce report method
            proReport(numStates,states);
         }

         //ELSE
         else
         {
            //output invalid choice msg
            System.out.println("\n\t\t<ERROR: Invalid Choice.>");
         } //END IF

         //call menu method and get returned choice
         choice = menu();
      } //END WHILE

      //output closing program msg
      System.out.println("\n\t\t<Program Closed. Have a Nice Day!>\n");
   } //END main

   /**********************************************************
    * Method Name    : menu
    * Author         : Shao yu Cheng
    * Date           : Mar 31 , 2017
    * Course/Section : CSC 264 - 001
    * Program Description: To print menu and get choice from user.
    *
    * BEGIN menu
    *    list of menu
    *    get choice from user
    *    return user choice
    * END menu
    **********************************************************/
   public static int menu()

   {
      //local constants

      //local variables
      int choice;     //user's choice

      /*****************************************************************************/

      //list of menu
      System.out.println("\n\n\t\t\tMain Menu");
      System.out.println("\t\t1 : Read File(s)");
      System.out.println("\t\t2 : Modify State Information");
      System.out.println("\t\t3 : Remove State");
      System.out.println("\t\t4 : Remove City");
      System.out.println("\t\t5 : Produce Report");
      System.out.print("\t\t0 : Quit\n\t\t");

      //get choice from user
      System.out.println("Enter Selection : ");
      choice = Keyboard.readInt();

      //return user choice
      return choice;
   } //END menu

   /**********************************************************
    * Method Name    : readFile
    * Author         : Anthony Massicci
    * Date           : March 31, 2017
    * Course/Section : CSC264
    * Program Description: Reads states with associated cities
    * from text file into an array, returns new number of states.
    *
    * BEGIN readFile(numStates, states)
    *    index = -1
    *    Ask user for file name
    *    Input fileName
    *    WHILE (fileName is not quit)
    *       Open file for reading
    *       IF (opening file was successful)
    *          Read line from file
    *          WHILE (there are lines to read)
    *             Split line into tokens by delim
    *             IF (line is state line)
    *                name = first token
    *                index = 0
    *                WHILE (state with name is not found
    *                       and end of array is not reached)
    *                   Increment index
    *                END WHILE
    *                IF (index is valid)
    *                   IF (states[index] is not initalized)
    *                      Create state object at index
    *                      Increment numStates
    *                   END IF
    *                ELSE
    *                   Display error
    *                   index = -1
    *                END IF
    *             ELSE IF (line is city line)
    *                name = first token
    *                population = second token
    *                IF (population is valid)
    *                   Add city to state at index
    *                   Call sortState(states, index, numStates)
    *                ELSE
    *                   Display error
    *                END IF
    *             END IF
    *             Read line from file
    *          END WHILE
    *       ELSE
    *          Display error message
    *       END IF
    *       Ask user for file name
    *       Input fileName
    *    END WHILE
    *    Return numStates
    * END readFile
    **********************************************************/
   public static int readFile(int numStates, StateInfo[] states) throws IOException 
   {
      // local constants
      final String QUIT = "-1";

      // local variables
      FileReader fr;      // FileReader object for current file
      BufferedReader br;  // BufferedReader object for current file
      String fileName;    // file name that user has entered
      String line;        // current line read from file
      String name;        // current nae
      String[] tokens;    // tokens from current line
      int index = -1;     // index used for linear search
      int pop;            // population of current city

      /***********************Start readFile method*********************************/

      // Ask user for file name
      System.out.print("\n\t\tEnter File to Read (-1 to Quit): ");
      fileName = Keyboard.readString();

      // while file name is not quit
      while (!fileName.equals(QUIT))
      {
         // open file for reading
         try 
         {
            fr = new FileReader(fileName);
            br = new BufferedReader(fr);

            // read line from file
            line = br.readLine();

            // while there are more lines to read
            while (line != null)
            {
               // split line into tokens
               tokens = line.split("\\t");

               // if line is a state line
n               if (tokens.length == 1)
               {
                  // set name to first token
                  name = tokens[0];

                  // set index to beginning of array
                  index = 0;

                  // while state with name is not found
                  while (index < numStates &&
                        !states[index].getName().equals(name))
                  {
                     // increment index
                     index++;

                  } // end while

                  // if states is not full
                  if (index < states.length)
                  {
                     // if the state with read name was not found
                     if (states[index] == null)
                     {
                        // create new state object at index
                        states[index] = new StateInfo(name);

                        // increment numStates
                        numStates++;
                     } 
                  }
                  else
                  {
                     // states is full display error
                     System.err.printf("\n\t\t<ERROR: State, %s was Not " +
                           "Added. State List is Full!>\n",
                           name);

                     // set index to error/blank state
                     index = -1;
                  } // end if
               } 
               else if (tokens.length == 2) // if line is a city line
               {
                  // set name to first token
                  name = tokens[0];

                  // set pop to second token
                  try
                  {
                     pop = Integer.parseInt(tokens[1]);

                     // if index is valid
                     if (index >= 0)
                     {
                        // add city to current state
                        states[index].addCity(name, pop);

                        // restore order to array
                        sortState(states, index, numStates);
                     }
                     else
                     {
                        // Display error
                        System.err.printf("\n\t\t<ERROR: City %s was Not " +
                              "Added. No State Found.>\n",
                              name);
                     }
                  } catch (NumberFormatException ex)
                  {
                     // population token was not numeric, print error
                     System.err.printf("\n\t\t<ERROR: Population Value is Non-Numeric on " +
                           "Line:\t%s>\n", line);
                  } // end try-catch
               } // end if
               // read line from file
               line = br.readLine();

            } // end while
         } 
         catch(FileNotFoundException ex)
         {
            // the file could not be found, print error message
            System.err.printf("\n\t\t<ERROR: File %s Not Found.>\n", fileName);
         }      

         // Ask user for file name
         System.out.print("\n\t\tEnter File to Read (-1 to Quit): ");
         fileName = Keyboard.readString();
      } // end while

      // Return numStates  
      return numStates;
   } // end readFile method

   /**********************************************************
    * Method Name    : listStates
    * Author         : Shao yu Cheng
    * Date           : Mar 31 , 2017
    * Course/Section : CSC 264 - 001
    * Program Description: To print the list of States.
    *
    * BEGIN listStates
    *    print states list msg
    *    FOR ()
    *       get state's name, population and print
    *    END FOR
    * END listStates
    **********************************************************/
   public static void listStates(int numState, StateInfo states[])
   {
      //local constants

      //local variables

      /***************************************************/

      //print states list msg
      System.out.println("\n\n\t\tList of States: ");

      //FOR ()
      for (int i = 0; i < numState; i++)
      {
         //get state's name  and print
         System.out.println("\t\t" + (i+1) + ". " + states[i].getName());
      } //END FOR

      //print new line
      System.out.println("\n");
   } //END listStates

   /**********************************************************
    * Method Name    : modState
    * Author         : Shao yu Cheng
    * Date           : Mar 31 , 2017
    * Course/Section : CSC 264 - 001
    * Program Description: To modify the states' name.
    *
    * BEGIN modState
    *    IF (number of states is greater than zero)
    *       call listStates method
    *       get choice from user
    *       WHILE (choice is not quit)
    *          IF (choice is between the number of states)
    *             get state's new name
    *             IF (new name is not null)
    *                set state's name to new name and print success msg
    *             ELSE
    *                print error msg
    *             END IF
    *          ELSE
    *             print error msg
    *          END IF
    *       call listStates method
    *       get choice from user
    *       END WHILE
    *    ELSE
    *       print error msg
    *    END IF
    * END modState
    **********************************************************/
   public static void modState(int numState, StateInfo states[])
   {
      //local constants
      final int QUIT = -1;    //quit value

      //local variables
      int choice;             //choice form user
      String newName;         //state's new name

      /*******************************************************/

      //IF (number of states is greater than zero)
      if (numState > 0)
      {
         //call listStates method
         listStates(numState, states);

         //get choice from user
         System.out.print("\t\tSelect State to Modify (-1 to Quit): ");
         choice = Keyboard.readInt();

         //WHILE (choice is not quit)
         while (choice != QUIT)
         {
            //IF (choice is between the number of states)
            if (0 < choice && choice <= numState)
            {
               //get state's new name
               System.out.print("\t\tEnter New Name for State: ");
               newName = Keyboard.readString();

               //IF (new name is not null)
               if (newName != null)
               {
                  //set state's name to new name and print success msg
                  states[choice - 1].setName(newName);
                  System.out.println("\n\t\t<Name Changed Successfully.>\n");
               }

               //ELSE
               else
               {
                  //print error msg
                  System.out.println("\n\t\t<ERROR: Invalid Name.>\n");
               } //END IF
            }

            //ELSE
            else
            {
               //print error msg
               System.out.println("\n\t\t<ERROR: Invalid Choice.>\n");
            } //END IF

            //call listStates method
            listStates(numState, states);

            //get choice from user
            System.out.print("\t\tEnter State to Modify (-1 to Quit): ");
            choice = Keyboard.readInt();
         } //END WHILE

      }

      //ELSE
      else
      {
         //print error msg
         System.out.println ("\n\t\t<ERROR: No States in File.>\n");
      } //END IF

   } //END modState

   /**********************************************************
    * Method Name    : rmvState
    * Author         : Shao yu Cheng
    * Date           : Mar 31 , 2017
    * Course/Section : CSC 264 - 001
    * Program Description: To remove the state from the list.
    *
    * BEGIN rmvState
    *    IF (number of states is greater than zero)
    *       call listStates method
    *       get state choice of user
    *       WHILE (choice is not quit)
    *          IF (choice is valid)
    *             remove state
    *             FOR ()
    *                move the states forward
    *             END FOR
    *             number of states reduce 1
    *             print success msg
    *          ELSE
    *             print error msg
    *          END IF
    *          call listStates method
    *          get state choice of user
    *       END WHILE
    *    ELSE
    *       print error msg
    *    END IF
    *    return the new number of states
    * END rmvState
    **********************************************************/
   public static int rmvState(int numState, StateInfo states[])
   {
      //local constants
      final int QUIT = -1;    //quit value

      //local variables
      int choice;             //state choice of user

      /********************************************************************************/

      //IF (number of states is greater than zero)
      if (numState > 0)
      {
         //call listStates method
         listStates(numState, states);

         //get state choice of user
         System.out.print("\t\tEnter State to Remove (-1 to Quit): ");
         choice = Keyboard.readInt();

         //WHILE (choice is not quit)
         while (choice != QUIT)
         {
            //IF (choice is valid)
            if (choice <= numState && choice > 0)
            {
               //remove state
               states[choice - 1] = null;

               //FOR ()
               for (int i = choice; i < numState; i++)
               {
                  //move the states forward
                  states[i - 1] = states[i];
               } //END FOR

               //number of states reduce 1
               numState--;

               //print success msg
               System.out.println("\n\t\t<State Removed Successfully.>\n");
            }

            //ELSE
            else
            {
               //print error msg
               System.out.println("\n\t\t<ERROR: Invalid Choice>\n");
            } //END IF

            //call listStates method
            listStates(numState, states);

            //get state choice of user
            System.out.print("\t\tEnter State to Remove (-1 to Quit): ");
            choice = Keyboard.readInt();
         } //END WHILE
      }

      //ELSE
      else
      {
         //print error msg
         System.out.println("\n\t\t<ERROR: No States in File.>\n");
      } //END IF

      //return the new number of states
      return numState;
   } //END rvmState

   /**********************************************************
    * Method Name    : rmvCity
    * Author         : Shao yu Cheng
    * Date           : Mar 31 , 2017
    * Course/Section : CSC 264 - 001
    * Program Description: To remove city from the list.
    *
    * BEGIN rmvCity
    *    IF (number of states is greater than zero)
    *       call listStates method
    *       get state choice of user
    *       WHILE (choice is not quit)
    *          IF (choice is valid)
    *             print list of cities
    *             get city choice
    *             WHILE (city choice is not quit)
    *                IF (city choice is valid)
    *                   remove city from the list
    *                   print success msg
    *                ELSE
    *                   print error msg
    *                END IF
    *                print list of cities
    *                get city choice
    *             END WHILE
    *             Resort states array
    *          ELSE
    *             print error msg
    *          END IF
    *          call listStates method
    *          get state choice of user
    *       END WHILE
    *    ELSE
    *       print error msg
    *    END IF
    * END rmvCity
    **********************************************************/
   public static void rmvCity(int numState, StateInfo states[])
   {
      //local constants
      final int QUIT = -1;    //quit value

      //local variables
      int choice;             //choice of state
      int cityChoice;         //choice of city

      /***********************************************************************/

      //IF (number of states is greater than zero)
      if (numState > 0)
      {
         //call listStates method
         listStates(numState, states);

         //get state choice of user
         System.out.print("\t\tEnter State of City to Be Removed (-1 to Quit): ");
         choice = Keyboard.readInt();

         //WHILE (choice is not quit)
         while (choice != QUIT)
         {
            //IF (choice is valid)
            if (choice <= numState && choice > 0)
            {
               
               // blank space
               System.out.println("\n\n");

               // print list of cities
               for (int i = 0; i < states[choice - 1].getNumCities(); i++)
               {
                  // print name of city
                  System.out.println("\t\t" + (i+1) + ". " + 
                                     states[choice-1].getCity(i).getName()); 
               } // end for

               // blank space
               System.out.println("\n");
               
               //get city choice
               System.out.print("\t\tEnter City to Remove (-1 to Quit): ");
               cityChoice = Keyboard.readInt();

               //WHILE (city choice is not quit)
               while (cityChoice != QUIT)
               {
                  //IF (city choice is valid)
                  if (cityChoice <= states[choice - 1].getNumCities() && 0 < cityChoice)
                  {
                     //remove city from the list
                     states[choice - 1].removeCity(cityChoice);

                     //print success msg
                     System.out.println("\n\t\t<City Removed Successfully.>\n");
                  }

                  //ELSE
                  else
                  {
                     //print error msg
                     System.out.println("\n\t\t<ERROR: Invalid Choice.>\n");
                  } //END IF

                  // blank space
                  System.out.println("\n\n");
                  
                  // print list of cities
                  for (int i = 0; i < states[choice - 1].getNumCities(); i++)
                  {
                     // print name of city
                     System.out.println("\t\t" + (i+1) + ". " + 
                           states[choice-1].getCity(i).getName()); 
                  } // end for

                  // blank space
                  System.out.println("\n\n");

                  //get city choice
                  System.out.print("\t\tEnter City to Remove (-1 to Quit): ");
                  cityChoice = Keyboard.readInt();
               } //END WHILE

               // resort states array
               sortState(states, choice - 1, numState);
            }

            //ELSE
            else
            {
               //print error msg
               System.out.println("\n\t\t<ERROR: Invalid Choice.>\n");
            } //END IF

            //call listStates method
            listStates(numState, states);

            //get state choice
            System.out.print("\t\tEnter State of City to be Removed (-1 to Quit): ");
            choice = Keyboard.readInt();
         } //END WHILE

      }

      //ELSE
      else
      {
         //print error msg
         System.out.println("\n\t\t<ERROR: No States in File.>\n");
      } //END IF
   } //END rmvCity


   /**********************************************************
    * Method Name    : sortState
    * Author         : Anthony Massicci
    * Date           : March 31, 2017
    * Course/Section : CSC264
    * Program Description: Online insertion sort, places state
    * at specified position into the correct position in array.
    *
    * BEGIN sortState(states, pos)
    *    key = state at pos
    *    index = pos
    *    WHILE (index is not at left most position and key is less than
    *           value on left)
    *       Move city on left to current index
    *       Decrement index
    *    END WHILE
    *    WHILE (index is not at right most position and key is
    *           greater than value on right)
    *       Move city on right to current index
    *       Increment index
    *    END WHILE
    *    states[index] = key
    * END sortState
    **********************************************************/
   public static void sortState(StateInfo states[], int pos, int numStates) 
   {
      // local constants

      // local variables 
      StateInfo key = states[pos];    // state to be sorted
      int index = pos;                // current index

      /**************************START sortState method************************/

      // while index is not at left most position and key is less than value on left
      while (index > 0 && key.getPop() < states[index - 1].getPop())
      {
         // move state on left to current position
         states[index] = states[index - 1];

         // decrement index
         index--;
      } // end while

      // while index is not at right most position 
      // and key is greater than value on right
      while (index < numStates - 1 && key.getPop() > states[index + 1].getPop())
      {
         // move state on right to current position
         states[index] = states[index + 1];

         // increment index
         index++;

      } // end while

      // set current position to key
      states[index] = key;

   } // end sortState

   /**********************************************************
    * Method Name    : proReport
    * Author         : Shao yu Cheng
    * Date           : Mar 31 , 2017
    * Course/Section : CSC 264 - 001
    * Program Description: To produce the final report.
    *
    * BEGIN proReport
    *    IF (number of states is greater than zero)
    *       ask for file's name
    *       call new buffered writer
    *       IF (file name is not quit)
    *          FOR ()
    *             write states and cities' name and population into file
    *          END FOR
    *          close the buffered writer
    *          print success msg
    *       END IF
    *    ELSE
    *       print error msg
    *    END IF
    * END proReport
    **********************************************************/
   public static void proReport(int numState, StateInfo states[]) throws IOException
   {
      //local constants
      final String QUIT = "-1";   //quit value

      //local variables
      String fileName;            //report file's name
      BufferedWriter bw;          //buffered writer to write the text to file

      /*******************************************************************/

      //IF (number of states is greater than zero)
      if (numState > 0)
      {
         //ask for file's name
         System.out.print("\t\tEnter File Name (-1 to Quit): ");
         fileName = Keyboard.readString();

         //call new buffered writer
         bw = new BufferedWriter(new FileWriter(fileName));

         //IF (file name is not quit)
         if (fileName != QUIT)
         {
            // blank space
            System.out.print("\n\n\n");

            //FOR ()
            for (int i = 0; i < numState; i++)
            {
               //write states and cities' name and population into file
               System.out.println("\t\t" + states[i].getName() + "\t\t" + states[i].getPop());
               bw.write(states[i].toString());
            } //END FOR

            // blank space
            System.out.print("\n\n\n");

            //close the buffered writer
            bw.close();

            //print success msg
            System.out.println("\n\t\t<Report File Produced Successfully.>\n");
         } //END IF
      }

      //ELSE
      else
      {
         //print error msg
         System.out.println("\n\t\t<ERROR: No States in File.>\n");
      } //END IF

   } //END proReport
}
