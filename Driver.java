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
        final int REMOVE_STATE = 4;
        final int REMOVE_CITY = 5;
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

    public static int readFile(int numStates, stateInfo[] states) throws IOException {
        
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
