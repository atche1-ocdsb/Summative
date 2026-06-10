/*
 * Description: This class will run the game, calling the other classes 
 * and creating an ordered structure to the program. 
 *
 * @author (Artem Tchernov)
 * @version (June 2nd - June _)
 */
import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;

public class GameManager {
    //code a void driver method to run the rest of the program
    public void run() {
        //declare a grid arrayList to store user and comp grids
        ArrayList<Grid> gridList = new ArrayList<Grid>();
        
        //declare a byte to store user's score
        byte bytScore;
        
        //output instructions
        instructions();
        
        //prompt the player to login
        logIn();
        
        //create comp and user grids
        gridList = createBoards();
        
        //start the user's turn with score of 0 and an empty hit array and populate the final score to the byte variable
        bytScore = turn(new ArrayList<String>(), new ArrayList<String>(), gridList.get(0), gridList.get(1), (byte)0);
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
        
        //declare a boolean to check if fileio has found a name
        boolean bolFound = false;
        
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
            bolFound = false;
            
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
                            //has benn found
                            bolFound = true;
                            
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
                            if (intInput == 1 && !bolFound) {
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

    //code a ArrayList<Grid> method to create/initialize the player's and comp's boards by calling the Grid class and return both grids
    public ArrayList<Grid> createBoards() {
        //create a grid arraylist
        ArrayList<Grid> gridList = new ArrayList<Grid>();
        
        //create comp grid
        Grid gridComp = new Grid();
        
        //create a scanner
        Scanner sc = new Scanner(System.in);
        
        //declare an arrayList to store the coordinates of the ships
        ArrayList<String> shipList = new ArrayList<String>();
        
        //declare arrayLists to store current ship coords and comparing ship coords
        ArrayList<String> currentList = new ArrayList<String>();
        ArrayList<String> comparingList = new ArrayList<String>();
        
        //create a 10x10 char 2d array
        char[][] arrGrid = new char[10][10];
        
        //declare a string to store current ship name
        String strShip = "";
        
        //declare a byte to store ship size
        byte bytSize = 0;
        
        //declare 2 booleans for error checking
        boolean bolLoop = false;
        boolean bolOutsideLoop = false;
        
        //fill the grid with water '~'
        for (int r = 0; r < arrGrid.length; r++) {
            for (int c = 0; c < arrGrid[r].length; c++) {
                //fill
                arrGrid[r][c] = '~';
            }
        }
        
        //output the grid 
        System.out.println("  A B C D E F G H I J ");
        for (int r = 0; r < arrGrid.length; r++) {
            System.out.print(r + " ");
            for (int c = 0; c < arrGrid.length; c++) {
                //output
                System.out.print(arrGrid[r][c] + " ");
            }
            System.out.println();
        }
        
        //for loop to place the ships
        for (int i = 0; i < 5; i++) {
            //check index i
            if (i == 0) {
                strShip = "Carrier";
                bytSize = 5;
            }
            else if (i == 1) {
                strShip = "Battleship";
                bytSize = 4;
            }
            else if (i == 2) {
                strShip = "Destroyer";
                bytSize = 3;
            }
            else if (i == 3) {
                strShip = "Submarine";
                bytSize = 3;
            }
            else if (i == 4) {
                strShip = "Patrolboat";
                bytSize = 2;
            }
            
            //do/while for all ship generation
            do {
                //clear the currentList
                currentList.clear();
                
                //dont loop
                bolOutsideLoop = false;
                
                //prompt the user for the first coord
                System.out.println("\n\nEnter the starting coordinate for the " + strShip + " of size " + bytSize + ": ");
                
                //do while loop to get ship coords
                do {
                    //add the coordinate to current ship list
                    currentList.add(sc.nextLine());
                    
                    //check if its a real coordinate
                    if (currentList.get(0).length() == 2) {
                        //check if first character is appropriate
                        switch(currentList.get(0).charAt(0)) {
                            //is fine so dont loop
                            case 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J' -> bolLoop = false;
                            default -> {
                                bolLoop = true;
                                
                                //error msg
                                System.out.println("Error! First character must be a capital letter A - J. Try again: ");
                            } 
                        }
                        
                        //if 1st is fine check 2nd
                        if (!bolLoop) {
                            //check if 2nd character is appropriate
                            switch(currentList.get(0).charAt(1)) {
                                //is fine font loop
                                case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> bolLoop = false;
                                default -> {
                                    bolLoop = true;
                                    
                                    //error msg
                                    System.out.println("Error! Second character must be a number ranging 0 - 9. Try again: ");
                                } 
                            }
                        }
                    }
                    else {
                        //loop 
                        bolLoop = true;
                        
                        //error msg
                        System.out.println("Error! Coordinate must be formated as a capital letter followed by a number (ex. A1). Try again: ");
                    }
                    
                    //if loop, remove the false coordinate
                    if (bolLoop) {
                        currentList.remove(0);
                    }
                }
                while (bolLoop);
                
                //prompt the user to enter end coordinate
                System.out.println("Enter the end coordinate for the " + strShip + " of size " + bytSize + ": ");
                
                //do while loop to get ship coords
                do {
                    //add the coordinate to current ship list
                    currentList.add(sc.nextLine());
                    
                    //check if its a real coordinate
                    if (currentList.get(1).length() == 2) {
                        //check if first character is appropriate
                        switch(currentList.get(1).charAt(0)) {
                            //is fine so dont loop
                            case 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J' -> bolLoop = false;
                            default -> {
                                bolLoop = true;
                                
                                //error msg
                                System.out.println("Error! First character must be a capital letter A - J. Try again: ");
                            } 
                        }
                        
                        //if 1st is fine check 2nd
                        if (!bolLoop) {
                            //check if 2nd character is appropriate
                            switch(currentList.get(1).charAt(1)) {
                                //is fine font loop
                                case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> bolLoop = false;
                                default -> {
                                    bolLoop = true;
                                    
                                    //error msg
                                    System.out.println("Error! Second character must be a number ranging 0 - 9. Try again: ");
                                } 
                            }
                        }
                    }
                    else {
                        //loop 
                        bolLoop = true;
                        
                        //error msg
                        System.out.println("Error! Coordinate must be formated as a capital letter followed by a number (ex. A1). Try again: ");
                    }
                    
                    //check if same as starting coord 
                    if (currentList.get(0).equals(currentList.get(1))) {
                        //loop also
                        bolLoop = true;
                        
                        //error msg
                        System.out.println("Error! End coordinate must not match the starting coordinate. Try again: ");
                    }
                    else {
                        //make sure they share at least row or column
                        if (currentList.get(0).charAt(0) != currentList.get(1).charAt(0) && currentList.get(0).charAt(1) != currentList.get(1).charAt(1) && !bolLoop) {
                            //loop also
                            bolLoop = true;
                            
                            //error msg
                            System.out.println("Error! The ship may not be placed diagonally. Try again: ");
                        }
                    }
                    
                    //if loop, remove the false coordinate
                    if (bolLoop) {
                        currentList.remove(1);
                    }
                }
                while (bolLoop);
                
                //call the grid allCoords method to get all coords using the starting and ending coords
                currentList = (gridComp.allCoords(currentList.get(0), currentList.get(1)));
                
                //check that the size of the ship is correct
                if (currentList.size() != bytSize) {
                    //error msg
                    System.out.println("Error! The " + strShip + " must be of size " + bytSize + "! Try again.");
                    
                    //loop outside
                    bolOutsideLoop = true;
                }
                else {
                    //check for interference
                    for (int j = 0; j < currentList.size(); j++) {
                        //compare to every other ships coords
                        for (int k = 0; k < i; k++) {
                            comparingList = (gridComp.allCoords(shipList.get(2 * k), shipList.get(2 * k + 1)));
                            
                            //compare to each element
                            for (int l = 0; l < comparingList.size(); l++) {
                                if (currentList.get(j).equals(comparingList.get(l)) && !bolOutsideLoop) {
                                    //loop outside
                                    bolOutsideLoop = true;
                                    
                                    //error msg
                                    System.out.println("Error! The ships may not overlap! Try again.");
                                }
                            }
                        }
                    }
                }
            }
            while (bolOutsideLoop);
            
            //since coords are well generated, add to shipList
            shipList.add(currentList.get(0));
            shipList.add(currentList.get(bytSize - 1));
            
            //fill in the ship
            for (int j = 0; j < currentList.size(); j++) {
                //fill in by adjusting ASCII values for columns
                arrGrid[Character.getNumericValue(currentList.get(j).charAt(1))][currentList.get(j).charAt(0) - 65] = strShip.charAt(0);
            }
            
            //output grid msg
            System.out.println("\nUpdated grid: ");
            
            //output grid
            System.out.println("  A B C D E F G H I J ");
            for (int r = 0; r < arrGrid.length; r++) {
                System.out.print(r + " ");
                for (int c = 0; c < arrGrid.length; c++) {
                    //output
                    System.out.print(arrGrid[r][c] + " ");
                }
                System.out.println();
            }
        }
            
        //initialize a player grid
        Grid gridUser = new Grid(shipList);
        
        //populate array with both grids
        gridList.add(gridComp);
        gridList.add(gridUser);
        
        //return an arraylist of both grids
        return gridList;
    }
    
    //code a recursive byte turn method that will manage what happens each turn
    public byte turn(ArrayList<String> compHitsList, ArrayList<String> hitsList, Grid gridComp, Grid gridUser, byte bytScore) {
        //create a Scanner
        Scanner sc = new Scanner(System.in);
        
        //create an arrCompGrid and arrUserGrid for output
        char[][] arrCompGrid = gridComp.getCompGrid();
        char[][] arrUserGrid = gridUser.getUserGrid();
        char[][] arrDisplayGrid = new char[10][10];
        
        //create an instance of the ship class to use its toString
        Ship s1 = new Ship();
        
        //declare a String to store current shot
        String strShot;
        
        //declare two bytes to store row index and col index
        byte bytRow;
        byte bytCol;
        
        //declare a boolean for error checking
        boolean bolLoop = false;
        
        //only run the first time
        if (bytScore == 0) {
            //output the two grids to the user
            System.out.println("\n\n\n      Your grid:                Computer grid: ");
            
            //output grid
            System.out.println("  A B C D E F G H I J " + "        A B C D E F G H I J ");
            for (int r = 0; r < gridUser.initializeGrid().length; r++) {
                System.out.print(r + " ");
                for (int c = 0; c < gridUser.initializeGrid()[r].length; c++) {
                    //output
                    System.out.print(gridUser.initializeGrid()[r][c] + " ");
                }
                System.out.print("      " + r + " ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ");
                System.out.println();
            }
        }
        
        //prompt the user to shoot
        System.out.println("\nYour turn -> To shoot, enter a coordinate on the opponents board: ");
        
        //do/while loop to get a valid shot
        do {
            //populate the shot
            strShot = sc.nextLine();
            
            //check if its a real coordinate
            if (strShot.length() == 2) {
                //check if first character is appropriate
                switch(strShot.charAt(0)) {
                    //is fine so dont loop
                    case 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J' -> bolLoop = false;
                    default -> {
                        bolLoop = true;
                        
                        //error msg
                        System.out.println("Error! First character must be a capital letter A - J. Try again: ");
                    } 
                }
                
                //if 1st is fine check 2nd
                if (!bolLoop) {
                    //check if 2nd character is appropriate
                    switch(strShot.charAt(1)) {
                        //is fine font loop
                        case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> bolLoop = false;
                        default -> {
                            bolLoop = true;
                            
                            //error msg
                            System.out.println("Error! Second character must be a number ranging 0 - 9. Try again: ");
                        } 
                    }
                }
            }
            else {
                //loop 
                bolLoop = true;
                
                //error msg
                System.out.println("Error! Coordinate must be formated as a capital letter followed by a number (ex. A1). Try again: ");
            }
            
            //check if this shot has already been taken
            for (int i = 0; i < hitsList.size(); i++) {
                if (strShot.equals(hitsList.get(i))) {
                    //loop also
                    bolLoop = true;
                    
                    //error msg
                    System.out.println("Error! This shot has already been taken. Try again: ");
                }
            }
        }
        while (bolLoop);
        
        //add the shot to the hits list
        hitsList.add(strShot);
        
        //update the comp board
        arrCompGrid = gridComp.updateCompBoard(strShot);
        
        //shallow copy into the display grid
        for (int r = 0; r < arrCompGrid.length; r++) {
            for (int c = 0; c < arrCompGrid[r].length; c++) {
                //copy
                arrDisplayGrid[r][c] = arrCompGrid[r][c];
            }
        }
        
        //hide the ships on the display board
        for (int r = 0; r < arrDisplayGrid.length; r++) {
            for (int c = 0; c < arrDisplayGrid[r].length; c++) {
                //hide all letters
                switch (arrDisplayGrid[r][c]) {
                    case 'C','B','D','S','P' -> arrDisplayGrid[r][c] = '~';
                }
            }
        }
        
        //output the two grids to the user
        System.out.println("\n\n\n      Your grid:                Computer grid: ");
        
        //output grid
        System.out.println("  A B C D E F G H I J " + "        A B C D E F G H I J ");
        for (int r = 0; r < arrUserGrid.length; r++) {
            System.out.print(r + " ");
            for (int c = 0; c < arrUserGrid[r].length; c++) {
                //output
                System.out.print(arrUserGrid[r][c] + " ");
            }
            System.out.print("      " + r + " ");
            for (int c = 0; c < arrDisplayGrid[r].length; c++) {
                //output
                System.out.print(arrDisplayGrid[r][c] + " ");
            }
            System.out.println();
        }
        
        //check if the shot has hit a ship
        if (gridComp.getCompCarrier().isHit(strShot, gridComp.getCompCarrier().getCoordList()) && !gridComp.getCompCarrier().isDestroyed(hitsList, gridComp.getCompCarrier().getCoordList())) {
            //output a msg using the toString
            System.out.println(s1.toString());
        }
        else if (gridComp.getCompCarrier().isDestroyed(hitsList, gridComp.getCompCarrier().getCoordList())) {
            //output destroyed msg using toString
            System.out.println(gridComp.getCompCarrier().toString());
        }
        else if (gridComp.getCompBattleship().isHit(strShot, gridComp.getCompBattleship().getCoordList()) && !gridComp.getCompBattleship().isDestroyed(hitsList, gridComp.getCompBattleship().getCoordList())) {
            //output a msg using the toString
            System.out.println(s1.toString());
        }
        else if (gridComp.getCompBattleship().isDestroyed(hitsList, gridComp.getCompBattleship().getCoordList())) {
            //output destroyed msg using toString
            System.out.println(gridComp.getCompBattleship().toString());
        }
        else if (gridComp.getCompDestroyer().isHit(strShot, gridComp.getCompDestroyer().getCoordList()) && !gridComp.getCompDestroyer().isDestroyed(hitsList, gridComp.getCompDestroyer().getCoordList())) {
            //output a msg using the toString
            System.out.println(s1.toString());
        }
        else if (gridComp.getCompDestroyer().isDestroyed(hitsList, gridComp.getCompDestroyer().getCoordList())) {
            //output destroyed msg using toString
            System.out.println(gridComp.getCompDestroyer().toString());
        }
        else if (gridComp.getCompSubmarine().isHit(strShot, gridComp.getCompSubmarine().getCoordList()) && !gridComp.getCompSubmarine().isDestroyed(hitsList, gridComp.getCompSubmarine().getCoordList())) {
            //output a msg using the toString
            System.out.println(s1.toString());
        }
        else if (gridComp.getCompSubmarine().isDestroyed(hitsList, gridComp.getCompSubmarine().getCoordList())) {
            //output destroyed msg using toString
            System.out.println(gridComp.getCompSubmarine().toString());
        }
        else if (gridComp.getCompPatrolboat().isHit(strShot, gridComp.getCompPatrolboat().getCoordList()) && !gridComp.getCompPatrolboat().isDestroyed(hitsList, gridComp.getCompPatrolboat().getCoordList())) {
            //output a msg using the toString
            System.out.println(s1.toString());
        }
        else if (gridComp.getCompPatrolboat().isDestroyed(hitsList, gridComp.getCompPatrolboat().getCoordList())) {
            //output destroyed msg using toString
            System.out.println(gridComp.getCompPatrolboat().toString());
        }
        else {
            //output miss message
            System.out.println("\nYou missed your shot!");
        }
        
        //base case 1:
        if (gridComp.getCompCarrier().isDestroyed(hitsList, gridComp.getCompCarrier().getCoordList())
            && gridComp.getCompBattleship().isDestroyed(hitsList, gridComp.getCompBattleship().getCoordList())
            && gridComp.getCompDestroyer().isDestroyed(hitsList, gridComp.getCompDestroyer().getCoordList())
            && gridComp.getCompSubmarine().isDestroyed(hitsList, gridComp.getCompSubmarine().getCoordList())
            && gridComp.getCompPatrolboat().isDestroyed(hitsList, gridComp.getCompPatrolboat().getCoordList())
            ) {
            //comp lost, user won, return score
            return bytScore;
        }
        
        //computer's turn -> randomized coordinate
        //do/while loop to ensure the hit generated isnt a repeat
        do {
            //dont initialy regenerate/loop
            bolLoop = false;
            
            //generate random coords
            bytRow = (byte)(Math.random() * 10);
            bytCol = (byte)(Math.random() * 10);
            
            //find the column letter using ASCII codes and add it to the string, followed by row number
            strShot = (char)(bytCol + 65) + String.valueOf(bytRow);
            
            //check if this coordinate is already in the list, if so regenerate
            for (int i = 0; i < compHitsList.size(); i++) {
                if (compHitsList.get(i).equals(strShot)) {
                    //regenerate
                    bolLoop = true;
                }
            }
        }
        while (bolLoop); 
        
        //add the shot to the list
        compHitsList.add(strShot);
        
        //output computer shot
        System.out.println("\nYour opponent shoots at " + strShot);
        
        //update the user board
        arrUserGrid = gridUser.updateUserBoard(strShot);
        
        //output the two grids to the user
        System.out.println("\n\n\n      Your grid:                Computer grid: ");
        
        //output grid
        System.out.println("  A B C D E F G H I J " + "        A B C D E F G H I J ");
        for (int r = 0; r < arrUserGrid.length; r++) {
            System.out.print(r + " ");
            for (int c = 0; c < arrUserGrid[r].length; c++) {
                //output
                System.out.print(arrUserGrid[r][c] + " ");
            }
            System.out.print("      " + r + " ");
            for (int c = 0; c < arrDisplayGrid[r].length; c++) {
                //output
                System.out.print(arrDisplayGrid[r][c] + " ");
            }
            System.out.println();
        }
        
        //check if the shot has hit a ship
        if (gridUser.getUserCarrier().isHit(strShot, gridUser.getUserCarrier().getCoordList()) && !gridUser.getUserCarrier().isDestroyed(compHitsList, gridUser.getUserCarrier().getCoordList())) {
            //output a msg using the toString
            System.out.println(s1.toString());
        }
        else if (gridUser.getUserCarrier().isDestroyed(compHitsList, gridUser.getUserCarrier().getCoordList())) {
            //output destroyed msg using toString
            System.out.println(gridUser.getUserCarrier().toString());
        }
        else if (gridUser.getUserBattleship().isHit(strShot, gridUser.getUserBattleship().getCoordList()) && !gridUser.getUserBattleship().isDestroyed(compHitsList, gridUser.getUserBattleship().getCoordList())) {
            //output a msg using the toString
            System.out.println(s1.toString());
        }
        else if (gridUser.getUserBattleship().isDestroyed(compHitsList, gridUser.getUserBattleship().getCoordList())) {
            //output destroyed msg using toString
            System.out.println(gridUser.getUserBattleship().toString());
        }
        else if (gridUser.getUserDestroyer().isHit(strShot, gridUser.getUserDestroyer().getCoordList()) && !gridUser.getUserDestroyer().isDestroyed(compHitsList, gridUser.getUserDestroyer().getCoordList())) {
            //output a msg using the toString
            System.out.println(s1.toString());
        }
        else if (gridUser.getUserDestroyer().isDestroyed(compHitsList, gridUser.getUserDestroyer().getCoordList())) {
            //output destroyed msg using toString
            System.out.println(gridUser.getUserDestroyer().toString());
        }
        else if (gridUser.getUserSubmarine().isHit(strShot, gridUser.getUserSubmarine().getCoordList()) && !gridUser.getUserSubmarine().isDestroyed(compHitsList, gridUser.getUserSubmarine().getCoordList())) {
            //output a msg using the toString
            System.out.println(s1.toString());
        }
        else if (gridUser.getUserSubmarine().isDestroyed(compHitsList, gridUser.getUserSubmarine().getCoordList())) {
            //output destroyed msg using toString
            System.out.println(gridUser.getUserSubmarine().toString());
        }
        else if (gridUser.getUserPatrolboat().isHit(strShot, gridUser.getUserPatrolboat().getCoordList()) && !gridUser.getUserPatrolboat().isDestroyed(compHitsList, gridUser.getUserPatrolboat().getCoordList())) {
            //output a msg using the toString
            System.out.println(s1.toString());
        }
        else if (gridUser.getUserPatrolboat().isDestroyed(compHitsList, gridUser.getUserPatrolboat().getCoordList())) {
            //output destroyed msg using toString
            System.out.println(gridUser.getUserPatrolboat().toString());
        }
        else {
            //output miss message
            System.out.println("\nYour opponent missed their shot!");
        }
        
        //base case 2:
        if (gridUser.getUserCarrier().isDestroyed(compHitsList, gridUser.getUserCarrier().getCoordList())
            && gridUser.getUserBattleship().isDestroyed(compHitsList, gridUser.getUserBattleship().getCoordList())
            && gridUser.getUserDestroyer().isDestroyed(compHitsList, gridUser.getUserDestroyer().getCoordList())
            && gridUser.getUserSubmarine().isDestroyed(compHitsList, gridUser.getUserSubmarine().getCoordList())
            && gridUser.getUserPatrolboat().isDestroyed(compHitsList, gridUser.getUserPatrolboat().getCoordList())
            ) {
            //user lost, return score of -1
            return -1;
        }
        
        //since neither player won on this turn, proceed to next turn
        return turn(compHitsList, hitsList, gridComp, gridUser, bytScore += 1);
    }
}