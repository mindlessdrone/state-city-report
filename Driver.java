import java.io.*;

/**
 * Created by Shelly on 2017/3/10.
 */
public class Driver {
    public static void main(String[] args) throws Exception {
        //local constants
        final int QUIT = 0;
        final int READ_FILE = 1;
        final int MOD_STATE = 2;
        final int REMOVE_STATE = 4;
        final int REMOVE_CITY = 5;
        final int PRODUCE_REPORT = 8;

        //local variables
        int choice;
        int numStates = 0;
        StateInfo[] states = new StateInfo[1];

        /********************************************/
        choice = menu();
        while (choice != QUIT) {
            if (choice == READ_FILE) {
                numStates = readFile(numStates, states);
            } else if (choice == MOD_STATE) {
                modState(numStates,states);
            } else if (choice == REMOVE_STATE){
                rmvState(numStates,states);
            } else if (choice == REMOVE_CITY) {
                rmvCity(numStates,states);
            } else if (choice == PRODUCE_REPORT) {
                proReport(numStates,states);
            } else {
                System.out.print("Your choice is invalid. Please enter again.");
            }
            choice = menu();
        }
        System.out.print("The program is closed. Have a nice day.");
    }

    public static int menu(){
        int choice;
        System.out.println("\t\t\tHello, menu is below, please input your choice:");
        System.out.println("\t\t\t1. READ A FILE.");
        System.out.println("\t\t\t2. MODIFY STATE INFORMATION.");
        System.out.println("\t\t\t4. REMOVE STATE.");
        System.out.println("\t\t\t5. REMOVE CITY.");
        System.out.println("\t\t\t8. PRODUCE REPORTS.");
        System.out.println("\t\t\t0. QUIT.");
        choice = Keyboard.readInt();

        return choice;
    }

   /**********************************************************
    * Method Name    : sortState
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
       System.out.print("Please enter a file to read from (-1 to quit): ");
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
                if (tokens.length == 1)
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
                       System.err.printf("ERROR: State, %s was not " +
                                         "added. State list is full\n",
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
                            System.err.printf("ERROR: City %s was not " +
                                              "added. No state to add it to.\n",
                                              name);
                        }
                    } catch (NumberFormatException ex)
                    {
                       // population token was not numeric, print error
                       System.err.printf("ERROR: population value is not numeric on " +
                                         "line:\t%s\n", line);
                    } // end try-catch
                } // end if
                // read line from file
                line = br.readLine();

             } // end while
          } 
          catch(FileNotFoundException ex)
          {
             // the file could not be found, print error message
             System.err.printf("ERROR: the file %s could not be found.\n", fileName);
          }      

          // Ask user for file name
          System.out.print("Please enter a file to read from (-1 to quit): ");
          fileName = Keyboard.readString();
       } // end while
     
       // Return numStates  
       return numStates;
    } // end readFile method


    public static void listStates(int numState, StateInfo states[]){
        System.out.println("\t\t\tThe list of States:");
        for(int i = 0; i < numState; i++){
            System.out.println("\t\t\t" + (i+1) + ":\t" +  states[i].getName());
        }
    }

    public static void modState(int numState, StateInfo states[]){
        final int QUIT = -1;

        int choice;
        String newName;
        if (numState > 0 ) {
            listStates(numState, states);
            System.out.print("Enter the choice of the State you want to modify (or -1 to QUIT):");
            choice = Keyboard.readInt();
            while (choice != QUIT) {
                if (0 < choice && choice <= numState) {
                    System.out.print("Enter the name will be changed to:");
                    newName = Keyboard.readString();
                    if (newName != null) {
                        states[choice - 1].setName(newName);
                        System.out.print("Changing name is success.");
                    } else {
                        System.out.print("The name is invalid.");
                    }
                } else {
                    System.out.print("The choice is invalid.");
                }
                listStates(numState, states);
                System.out.print("Enter the choice of the State you want to modify (or -1 to QUIT):");
                choice = Keyboard.readInt();
            }
        } else {
            System.out.println ("There's no States in the file.");
        }
    }

    public static void rmvState(int numState, StateInfo states[]){
        final int QUIT = -1;

        int choice;
        if (numState > 0 ) {
            listStates(numState, states);
            System.out.print("Enter the choice of the State you want to remove(or -1 to QUIT):");
            choice = Keyboard.readInt();
            while (choice != QUIT) {
                if (choice < numState && choice > 0) {
                    states[choice - 1] = null;
                    for (int i = choice; i < numState; i++) {
                        states[i - 1] = states[i];
                    }
                    numState--;
                    System.out.print("The State is removed.");
                } else {
                    System.out.print("The choice is invalid.");
                }
                listStates(numState, states);
                System.out.print("Enter the choice of the State you want to remove(or -1 to QUIT):");
                choice = Keyboard.readInt();
            }
        } else {
            System.out.println("There's no States in the file.");
        }
    }

    public static void rmvCity(int numState, StateInfo states[]){
        final int QUIT = -1;

        int choice;
        int cityChoice;

        if (numState > 0 ) {
            listStates(numState, states);
            System.out.print("Enter the choice of the State where the city at that you want to remove(or -1 to QUIT):");
            choice = Keyboard.readInt();
            while (choice != QUIT) {
                if (choice < numState && choice > 0) {
                    states[choice - 1].toString();
                    System.out.print("Enter the choice of the City you want to remove (or -1 to QUIT):");
                    cityChoice = Keyboard.readInt();
                    while (cityChoice != QUIT) {
                        if (cityChoice < states[choice - 1].getNumCities() && 0 < cityChoice) {
                            states[choice - 1].removeCities(cityChoice);
                            System.out.print("City is removed.");
                        } else {
                            System.out.print("The choice is invalid.");
                        }
                        states[choice - 1].toString();
                        System.out.print("Enter the choice of the City you want to remove (or -1 to QUIT):");
                        cityChoice = Keyboard.readInt();
                    }
                } else {
                    System.out.print("The choice is invalid.");
                }
                listStates(numState, states);
                System.out.print("Enter the choice of the State where the city at that you want to remove(or -1 to QUIT):");
                choice = Keyboard.readInt();
            }
        } else {
            System.out.println("There's no States in the file.");
        }
    }

    
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

    public static void proReport(int numState, StateInfo states[]) throws IOException {
        final String QUIT = "-1";

        String fileName;
        BufferedWriter bw;
        if (numState > 0 ) {

            System.out.print("Enter the file's name (or -1 to quit): ");
            fileName = Keyboard.readString();
            bw = new BufferedWriter(new FileWriter(fileName));
            if (fileName != QUIT) {
                for (int i = 0; i <= numState; i++) {
                    bw.write(states[i].getName() + "   " + states[i].getPop());
                    bw.write(states[i].toString());
                }
                System.out.print("The final report file is produced.");
            }
        } else {
            System.out.println("There's no States in the file.");
        }
    }
}
