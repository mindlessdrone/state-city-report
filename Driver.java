import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

/**
 * Created by Shelly on 2017/3/10.
 */
public class Driver {
    public static void main(String[] args) throws Exception {
        //local constants
        final int QUIT = 0;
        final int READ_FILE = 1;
        final int MOD_STATE = 2;
        final int MOD_CITY = 3;
        final int REMOVE_STATE = 4;
        final int REMOVE_CITY = 5;
        final int SORT_STATES = 6;
        final int SORT_CITIES = 7;
        final int PRODUCE_REPORT = 8;

        //local variables
        int choice;
        int numStates = 0;
        stateInfo[] states = new stateInfo[50];

        /********************************************/
        choice = menu();
        while (choice != QUIT) {
            if (choice == READ_FILE) {
                numStates = readFile(numStates, states);
            } else if (choice == MOD_STATE) {
                modState(numStates,states);
            } else if (choice == MOD_CITY) {
                modCity(numStates,states);
            } else if (choice == REMOVE_STATE){
                rmvState(numStates,states);
            } else if (choice == REMOVE_CITY) {
                rmvCity(numStates,states);
            } else if (choice == SORT_STATES) {
                sortState(numStates,states);
            } else if (choice == SORT_CITIES) {
                sortCity(numStates,states);
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
        System.out.println("\t\t\t3. MODIFY CITY INFORMATION.");
        System.out.println("\t\t\t4. REMOVE STATE.");
        System.out.println("\t\t\t5. REMOVE CITY.");
        System.out.println("\t\t\t6. SORT STATES.");
        System.out.println("\t\t\t7. SORT CITIES.");
        System.out.println("\t\t\t8. PRODUCE REPORTS.");
        System.out.println("\t\t\t0. QUIT.");
        choice = Keyboard.readInt();

        return choice;
    }

    public static int readFile(int numStates, stateInfo[] states) throws IOException {
        final String QUIT = "-1";

        BufferedReader br;
        String fileName = "";
        String line;
        String cityName;
        int pop;
        StringTokenizer token;
        //stateInfo states[];

        System.out.print("Enter the file's name (or -1 to quit): ");
        fileName = Keyboard.readString();
        br = new BufferedReader(new FileReader(fileName));

        while (!fileName.equals(QUIT)){
            if (new File(fileName).exists()){
                line = br.readLine();
                while (line != null) {
                    token = new StringTokenizer(line, "\t");
                    if (token.countTokens() == 1) {
                        states[numStates] = new stateInfo(token.nextToken());
                        numStates++;
                    } else if (token.countTokens() == 2) {
                        cityName = token.nextToken();
                        try {
                            pop = Integer.parseInt(token.nextToken());
                            System.out.println(pop);
                            states[numStates-1].addCity(cityName, pop);
                        } catch (NumberFormatException e) {
                            System.err.println("Population has to be a numeric value.");
                        }
                    }
                    line = br.readLine();
                }
            }
            else {
                System.out.print("The file is not found.");
            }
            System.out.print("Enter the file's name (or -1 to quit): ");
            fileName = Keyboard.readString();
        }
        return numStates;
    }

    public static void listStates(int numState, stateInfo states[]){
        System.out.println("\t\t\tThe list of States:");
        for(int i = 0; i < numState; i++){
            System.out.println("\t\t\t" + (i+1) + ":\t" +  states[i].getName());
        }
    }

    public static void modState(int numState, stateInfo states[]){
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

    public static void modCity(int numState, stateInfo states[]){
        final int QUIT = -1;

        int choice;
        int cityChoice;
        String cityName;
        int cityPop;
        String newName;
        int newPop = 0;
        boolean result;

        if (numState > 0 ) {
            listStates(numState, states);
            System.out.print("Enter the choice of the State you want to modify (or -1 to QUIT):");
            choice = Keyboard.readInt();
            while (choice != QUIT) {
                if (choice < numState && 0 < choice) {
                    states[choice - 1].toString();
                    System.out.print("Enter the choice of the City you want to modify (or -1 to QUIT):");
                    cityChoice = Keyboard.readInt();
                    while (cityChoice != QUIT) {
                        if (cityChoice < states[choice - 1].getNumCities() && 0 < cityChoice) {
                            cityName = states[choice - 1].getCity(cityChoice).getCityName();
                            cityPop = states[choice - 1].getCity(cityChoice).getCityPop();

                            System.out.print("Enter the name of the City will be changed to: ");
                            newName = Keyboard.readString();
                            if (newName != null) {
                                cityName = newName;
                                System.out.print("Enter the population of the City will be changed to: ");
                                newPop = Keyboard.readInt();
                                if (newPop >= 0) {
                                    cityPop = newPop;
                                    result = states[choice - 1].modifyCity(cityName, cityPop, choice - 1);
                                    if (result == true) {
                                        System.out.print("Changing name is success.");
                                    } else {
                                        System.out.print("Changing name failed.");
                                    }
                                } else {
                                    System.out.print("The population is invalid.");
                                }
                            } else {
                                System.out.print("The name is invalid.");
                            }
                        } else {
                            System.out.print("The modification is invalid.");
                        }
                        states[choice - 1].toString();
                        System.out.print("Enter the choice of the City you want to modify (or -1 to QUIT):");
                        cityChoice = Keyboard.readInt();
                    }
                } else {
                    System.out.print("The choice is invalid.");
                }
                listStates(numState, states);
                System.out.print("Enter the choice of the State you want to modify (or -1 to QUIT):");
                choice = Keyboard.readInt();
            }
        } else {
            System.out.println("There's no States in the file.");
        }
    }

    public static void rmvState(int numState, stateInfo states[]){
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

    public static void rmvCity(int numState, stateInfo states[]){
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

    public static void sortState(int numState, stateInfo states[]) {
        int choice;
        if (numState > 0 ) {
            System.out.print("1. SORT BY NAME.");
            System.out.print("2. SORT BY POPULATION.");
            choice = Keyboard.readInt();
            if (numState > 0) {
                if (choice == 1) {
                    System.out.print("Before sorting:");
                    listStates(numState, states);
                    Arrays.sort(states, Comparator.comparing(state -> state.stateName));
                    System.out.print("Sorting method is success.");
                    System.out.print("After sorting:");
                    listStates(numState, states);
                } else if (choice == 2) {
                    System.out.print("Before sorting:");
                    listStates(numState, states);
                    for (int i = 0; i < numState; i++) {
                        states[i] = new stateInfo(states[i].getName(), states[i].getStatePop());
                    }
                    Arrays.sort(states, Comparator.comparing(state -> state.statePop));
                    System.out.print("After sorting:");
                    listStates(numState, states);
                } else {
                    System.out.print("Invalid choice.");
                }
            } else {
                System.out.print("There's no States in the file.");
            }
        } else {
            System.out.println("There's no States in the file.");
        }
    }

    public static void sortCity(int numState, stateInfo states[]) {
        final int QUIT = -1;

        int choice;
        int stateChoice;

        if (numState > 0 ) {
            listStates(numState, states);
            System.out.print("Enter the choice of the State of the cities that you want to sort by (or -1 to QUIT):");
            stateChoice = Keyboard.readInt();

            while (stateChoice != QUIT) {
                if (stateChoice < numState && 0 < stateChoice) {
                    System.out.print("SORT BY (or -1 to QUIT): ");
                    System.out.print("1. SORT BY NAME.");
                    System.out.print("2. SORT BY POPULATION.");
                    choice = Keyboard.readInt();
                    states[stateChoice-1].sortCities(choice);
                } else {
                    System.out.print("Invalid choice.");
                }
                listStates(numState, states);
                System.out.print("Enter the choice of the State of the cities that you want to sort by (or -1 to QUIT):");
                stateChoice = Keyboard.readInt();
            }
        } else {
            System.out.println("There's no States in the file.");
        }
    }

    public static void proReport(int numState, stateInfo states[]) throws IOException {
        final String QUIT = "-1";

        String fileName;
        BufferedWriter bw;
        if (numState > 0 ) {

            System.out.print("Enter the file's name (or -1 to quit): ");
            fileName = Keyboard.readString();
            bw = new BufferedWriter(new FileWriter(fileName));
            if (fileName != QUIT) {
                for (int i = 0; i <= numState; i++) {
                    bw.write(states[i].getName() + "   " + states[i].getStatePop());
                    bw.write(states[i].toString());
                }
                System.out.print("The final report file is produced.");
            }
        } else {
            System.out.println("There's no States in the file.");
        }
    }
}
