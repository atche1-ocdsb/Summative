/*
 * Description: This class will run the game, calling the other classes 
 * and creating an ordered structure to the program. 
 *
 * @author (Artem Tchernov)
 * @version (June 2nd - June _)
 */
import java.util.Scanner;
import java.io.*;

public class GameManager {
    //code a void driver method to run the rest of the program
    public void run() {
        //output instructions
        instructions();
        
        //prompt the player to login
        logIn();
        
        //create comp and user grids
        createBoards();
    }
    
    //code a void method to output the instructions
    public void instructions() {
        //output greeting and instructions
        System.out.println("Welcome to BattleShip+!");
        System.out.println("This is a strategy game where both you and the computer are given your own 10x10 grid, "
        + "\nonto which you must secretly place your 5 ships of different lengths (5,4,3,3,2 spaces long). "
        + "\nWhile alternating turns, you will fire upon one another’s boards, one grid space at a time, "
        + "\nattempting to destroy all of the opponents ships before the opponent destroys yours. "
        + "\nOnce fired upon, the program will declare if a ship has been hit or if the shot has missed - a hit would result in a repeated shot for the opponent. "
        + "\nOnce a ship is fully destroyed, the program will declare that ship sunk."
        + "\n");
    }
    
    //code a void method that will handle all of the login steps
    public void logIn() {
        //create a scanner
        Scanner sc = new Scanner(System.in);
        
        //declare a boolean for error trapping
        boolean bolLoop = false;
        
        //declare a int to store input
        int intInput = 0;
        
        //declare 2 strings to store name input and password input
        String strName;
        String strPassword;
        
        //declare a String array to store input from fileio
        String[] arrData = new String[3];
        
        //prompt the user to sign in or create an account
        System.out.println("\nWould you like to: "
        + "\n  1. Login"
        + "\n  2. Sign up");
        
        //do while loop to check input
        do {
            //dont loop
            bolLoop = false;
            
            //check if integer
            if (sc.hasNextInt()) {
                //populate
                intInput = sc.nextInt();
                
                //check if not an option
                if (!(intInput == 1 || intInput == 2)) {
                    //loop
                    bolLoop = true;
                    
                    //error msg
                    System.out.println("Error! Enter 1 or 2: ");
                }
            }
            else {
                //error msg
                System.out.println("Error! Enter the numerical value: ");
                
                //loop
                bolLoop = true;
            }
            
            //clear scanner
            sc.nextLine();
        }
        while (bolLoop);
        
        //prompt for username
        System.out.println("\nEnter your username: ");
        
        //do/while loop to get username
        do {
            //dont loop
            bolLoop = false;
            
            //populate user
            strName = sc.nextLine();
            
            //check for spaces
            for (int i = 0; i < strName.length(); i++) {
                if (strName.charAt(i) == ' ' && !bolLoop) {
                    //error msg
                    System.out.println("Error! Username cannot contain any spaces. Try again: ");
                    
                    //loop
                    bolLoop = true;
                }
            }
            
            //if contains spaces dont run the rest of code
            if (!bolLoop) {
                //check for username in use
                try {
                    //declare a scanner for fileio use
                    Scanner reader = new Scanner(new FileReader("Players.txt"));
                    
                    //while loop to loop through file
                    while(reader.hasNextLine()) {
                        //populate line's data to array
                        arrData = reader.nextLine().split(",");
                        
                        //check if usernames match
                        if (arrData[0].equals(strName)) {
                            //check option
                            if (intInput == 2) {
                                //error msg
                                System.out.println("Error! Username already in use. Try another one: ");
                                
                                //loop
                                bolLoop = true;
                            }
                        }
                        else {
                            //check option
                            if (intInput == 1) {
                                //error msg
                                System.out.println("Error! Username not found. Try again: ");
                                
                                //loop
                                bolLoop = true;
                            }
                        }
                    }
                    
                    //close reader
                    reader.close();
                }
                catch (Exception e) {
                    //error msg
                    System.out.println("Corrupt file!");
                }
            }
        }
        while (bolLoop);
        
        //prompt for password
        System.out.println("\nEnter your password: ");
        
        //do/while loop to get password
        do {
            //dont loop
            bolLoop = false;
            
            //populate user
            strPassword = sc.nextLine();
            
            //check for spaces
            for (int i = 0; i < strPassword.length(); i++) {
                if (strPassword.charAt(i) == ' ' && !bolLoop) {
                    //error msg
                    System.out.println("Error! Password cannot contain any spaces. Try again: ");
                    
                    //loop
                    bolLoop = true;
                }
            }
            
            //if contains spaces dont run the rest of code
            if (!bolLoop) {
                //check for username in use
                try {
                    //declare a scanner for fileio use
                    Scanner reader = new Scanner(new FileReader("Players.txt"));
    
                    //while loop to loop through file
                    while(reader.hasNextLine()) {
                        //populate line's data to array
                        arrData = reader.nextLine().split(",");
                        
                        //check option
                        if (intInput == 1) {
                            //check if usernames match
                            if (arrData[0].equals(strName)) {
                                //check if passwords match
                                if (!arrData[1].equals(strPassword)) {
                                    //error msg
                                    System.out.println("Error! Password incorrect. Try again: ");
                                    
                                    //loop
                                    bolLoop = true;
                                }
                            }
                        }
                    }
                    
                    //close reader
                    reader.close();
                }
                catch (Exception e) {
                    //error msg
                    System.out.println("Corrupt file!");
                }
            }
        }
        while (bolLoop);
        
        //check if login or sign up
        if (intInput == 1) {
            //try/catch to read from file
            try {
                //declare a scanner for fileio use
                Scanner reader = new Scanner(new FileReader("Players.txt"));

                //while loop to loop through file
                while(reader.hasNextLine()) {
                    //populate line's data to array
                    arrData = reader.nextLine().split(",");
                    
                    //check if usernames match
                    if (arrData[0].equals(strName)) {
                        //create an instance of this player
                        Player p1 = new Player(arrData[0], arrData[1], Byte.parseByte(arrData[2]));
                    }
                }
                
                //close reader
                reader.close();
            }
            catch (Exception e) {
                //error msg
                System.out.println("Corrupt file!");
            }
        }
        else {
            //create a new player with score 0
            Player p1 = new Player(strName, strPassword, (byte)0);
            
            //try/catch to add the player to the file
            try {
                //create a filewriter to add user
                FileWriter writer = new FileWriter ("Players.txt", true);
                
                //write player
                writer.write(p1.toString());
                
                //close writer
                writer.close();
            }
            catch (Exception e) {
                //error msg
                System.out.println("Error writing to file!");
            }
        }
    }

    //code a void method to create/initialize the player's and comp's boards by calling the Grid class
    public void createBoards() {
        //create comp grid
        Grid gridComp = new Grid();
        
        //
    }
}