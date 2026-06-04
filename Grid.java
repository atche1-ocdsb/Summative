/*
 * Description: This class will be responsible for constructing both the user’s 10x10 grid (user input) and the computer’s grid (randomized). 
 * When creating the grid, this class will create instances of the 5 ships and “position” them on the grid.
 *
 * @author (Artem Tchernov)
 * @version (June 2nd - June _)
 */
import java.util.ArrayList; //import ArrayLists to be used to store the coords of the ships
import java.util.Arrays;    //used to use indexOf method on normal arrays

public class Grid {
    //declara a final global char array of the 10 letters representing the columns
    final char[] COLUMN_INDEX = new char[]{'A','B','C','D','E','F','G','H','I','J'};
    //declare a private instance ArrayList to be initialized throught the parameterized constructor to store the user ship coords
    /* Ship order:
     *  Index 0 & 1 -> Carrier start & end
     *  Index 2 & 3 -> Battleship start & end
     *  Index 4 & 5 -> Destroyer start & end
     *  Index 6 & 7 -> Submarine start & end
     *  Index 8 & 9 -> Patrolboat start & end
     * Coords Syntax: 
     *  Columns: A,B,C,D,E,F,G,H,I,J
     *  Rows:    0,1,2,3,4,5,6,7,8,9
     */
    private ArrayList<String> coordsList;
    
    //declare and initialize a private 2d array of type char to be used as the 10 by 10 game grids to store users grid
    private char[][] arrUserGrid = new char[10][10];
    
    //declare and initialize a private 2d array of type char to be used as the 10 by 10 game grids to store computers grid
    private char[][] arrCompGrid = new char[10][10];
    
    
    //code a parameterized constructor to initialize the coordinate when the user's board is being created
    Grid(ArrayList<String> c) {
        //initialize coords ArrayList
        this.coordsList = c;
        
        //using the given coordinates, populate the arrGrid
        this.arrUserGrid = initializeGrid();
    }
    
    //code a default constructor to be called when creating the computer's board and used to randomize the ship coords
    Grid() {
        //initialize the coords ArrayList using random generated ships positions
        this.coordsList = generateShips();
        
        //using the given coordinates, populate the arrGrid
        this.arrCompGrid = initializeGrid();
    }
    
    //code a ArrayList<String> method to randomly generate the ship coords for the computer grid and return them into the default constructor
    public ArrayList<String> generateShips() {
        //declare and initlialize an ArrayList to store the coords of the ships
        ArrayList<String> shipList = new ArrayList<String>();
        
        //declare an array to store possible coords for extending ship (2nd coord of ship)
        String[] arrEndCoord;
        
        //declare two String arrays to store all coords for current ship and comparing ship
        String[] arrCurrent;
        String[] arrOther;
        
        //declare two bytes to store row index and col index
        byte bytRow;
        byte bytCol;
        
        //declare a byte to store ship length
        byte bytSize;
        
        //declare a byte to store the final amount of possible expansions
        byte bytOptions = 0;
        
        //declare a string to momentarily store the created coords
        String strCoord;
        
        //declare a boolean to deal with regeneration
        boolean bolLoop;
        
        //declare a boolean to deal with overlapping
        boolean bolOverlap = false;
        
        //for loop to run five times
        for (int i = 0; i < 5; i++) {
            //do/while loop to ensure the coord generated doesn't repeat
            do {
                //dont initialy regenerate/loop
                bolLoop = false;
                
                //generate random coords
                bytRow = (byte)(Math.random() * 10);
                bytCol = (byte)(Math.random() * 10);
                
                //find the column letter and add it to the string, followed by row number
                strCoord = COLUMN_INDEX[bytCol] + String.valueOf(bytRow);
                
                //check if this coordinate is already in the list, if so regenerate
                for (int j = 0; j < shipList.size(); j++) {
                    if (shipList.get(j).equals(strCoord)) {
                        //regenerate
                        bolLoop = true;
                    }
                }
            }
            while (bolLoop);
            
            //check which ship it is based on i variable to generate second coord
            if (i == 0) {       //Carrier - size 5
                bytSize = 5;
            }
            else if (i == 1) {  //Battleship - size 4
                bytSize = 4;
            }
            else if (i == 2) {  //Destroyer - size 3
                bytSize = 3;
            }
            else if (i == 3) {  //Submarine - size 3
                bytSize = 3;
            }
            else {  //Patrolboat - size 2
                bytSize = 2;
            }
            
            //reset end coord array to default of 0
            arrEndCoord = new String[]{"0", "0", "0", "0"};
            
            //check how many of the four positions are possible to extend: up, right, down, left
            if (bytRow - (bytSize + 1) >= 0) { //can extend up
                //populate 1st slot of end coord array
                arrEndCoord[0] = COLUMN_INDEX[bytCol] + String.valueOf(bytRow - (bytSize + 1));
            }
            if (bytCol + (bytSize + 1) <= 9) { //can extend right
                //populate 2nd slot of end coord array
                arrEndCoord[1] = COLUMN_INDEX[bytCol + (bytSize + 1)] + String.valueOf(bytRow);
            }
            if (bytRow + (bytSize + 1) <= 9) { //can extend down
                //populate 3rd slot of end coord array
                arrEndCoord[2] = COLUMN_INDEX[bytCol] + String.valueOf(bytRow + (bytSize + 1));
            }
            if (bytCol - (bytSize + 1) >= 0) { //can extend left
                //populate 4th slot of end coord array
                arrEndCoord[3] = COLUMN_INDEX[bytCol - (bytSize + 1)] + String.valueOf(bytRow);
            }
            
            //for loop to loop through coord list and make sure it extands in a way to not overlap with other ships
            //k currently loops through each ship individually
            for (int k = 0; k < shipList.size(); k += 2) {    
                //populate all coords from comparing ship
                arrOther = allCoords(shipList.get(k), shipList.get(k+1));
                
                //for loop to check if each way of expanding works
                for (int l = 1; l <= 4; l++) {
                    //if statement to check if coord exist
                    if (arrEndCoord[l].equals("0")) {
                        //skip
                        continue;
                    }
                    else {
                        //populate all current coords
                        arrCurrent = allCoords(strCoord, arrEndCoord[l]);
                        
                        //check that no coords overlap
                        for (int m = 0; m < arrCurrent.length; m++) {
                            ////check against every "other" coordinate
                            for (int n = 0; n < arrOther.length; n++) {
                                if (arrCurrent[m].equals(arrOther[n])) {
                                    //overlapping
                                    bolOverlap = true;
                                }
                            }
                        }
                    }
                    
                    //if overlapping set this extension to 0
                    if (bolOverlap) {
                        //set to 0
                        arrEndCoord[l] = "0";
                        
                        //reset overlap
                        bolOverlap = false;
                    }
                }
            }
            
            //check how many non zero end coords are left
            for (int j = 0; j < arrEndCoord.length; j++) {
                if (!arrEndCoord[j].equals("0")) {
                    //add to counter
                    bytOptions++;
                }
            }
            
            //if none, restart entire loop - regenerate new start
            //otherwise randomise from the ones left
            if (bytOptions == 0) {
                //rerandomize by adjusting the "i" for loop variable. This will rerandomize the same ship
                i--;
            }
            else {
                //since it can be expanded add 1st coord to the arrayList
                shipList.add(strCoord);
                
                //randomly choose out of the x amount of options
                do {
                    //repopulate coord by reusing coord variable
                    strCoord = arrEndCoord[(int)(Math.random() * 4)];
                }
                while (strCoord.equals("0")); //loop if 0 is picked
                
                //add the end coord
                shipList.add(strCoord);
            }
        }
        
        //return the coord list
        return shipList;
    }
    
    //code a String[] method to return all of the coords of a ship given start and end
    public String[] allCoords(String strStart, String strEnd) {
        //declare an array to store the coordinates from the arrayList
        String[] arrCoords;
        
        //declare and initialize an ArrayList to store coords
        ArrayList<String> coordList = new ArrayList<String>();

        //check if ship is vertical or horizontal
        if (strStart.charAt(1) == strEnd.charAt(1)) {   //horizontal
            //check which way it extends
            if (Arrays.asList(COLUMN_INDEX).indexOf(strStart.charAt(0)) < Arrays.asList(COLUMN_INDEX).indexOf(strEnd.charAt(0))) { //extends right
                //for loop to loop until end coord
                for (int i = Arrays.asList(COLUMN_INDEX).indexOf(strStart.charAt(0)); i <= Arrays.asList(COLUMN_INDEX).indexOf(strEnd.charAt(0)); i++) {
                    //add coordinates
                    coordList.add(COLUMN_INDEX[i] + String.valueOf(strStart.charAt(1)));
                }
                
            }
            else { //extends left
                //for loop to loop until end coord
                for (int i = Arrays.asList(COLUMN_INDEX).indexOf(strStart.charAt(0)); i >= Arrays.asList(COLUMN_INDEX).indexOf(strEnd.charAt(0)); i--) {
                    //add coordinates
                    coordList.add(COLUMN_INDEX[i] + String.valueOf(strStart.charAt(1)));
                }
            }
        }
        else {  //vertical
            //check which way it extends
            if (strStart.charAt(1) < strEnd.charAt(1)) { //down
                //for loop to loop until end coord
                for (int i = strStart.charAt(1); i <= strEnd.charAt(1); i++) {
                    //add coordinates
                    coordList.add(strStart.charAt(0) + String.valueOf(i));
                }
            }
            else { //up
                //for loop to loop until end coord
                for (int i = strStart.charAt(1); i >= strEnd.charAt(1); i++) {
                    //add coordinates
                    coordList.add(strStart.charAt(0) + String.valueOf(i));
                }
            }
        }
        
        //initialize array with arraylist size
        arrCoords = new String[coordList.size()];
        
        //populate the array with the values from the arrayList
        for (int i = 0; i < coordList.size(); i++) {
            arrCoords[i] = coordList.get(i);
        }
        
        //return array of coords
        return arrCoords;
    }
    
    //code a char[][] method to use the coordinate list to generate a 10 x 10 board with the ships in place
    public char[][] initializeGrid() {
        //declare and initialize a 10x10 grid to be populated
        char[][] arrBoard = new char[10][10];
        
        //fill the grid with water '~'
        for (int r = 0; r < arrBoard.length; r++) {
            for (int c = 0; c < arrBoard[r].length; c++) {
                //fill
                arrBoard[r][c] = '~';
            }
        }
        
        
        
        
        //return the grid
        return arrBoard;
    }
}