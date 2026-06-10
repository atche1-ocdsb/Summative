/*
 * Description: This class will be responsible for constructing both the user’s 10x10 grid (user input) and the computer’s grid (randomized). 
 * When creating the grid, this class will create instances of the 5 ships and “position” them on the grid.
 *
 * @author (Artem Tchernov)
 * @version (June 2nd - June 10th)
 */
import java.util.ArrayList; //import ArrayLists to be used to store the coords of the ships

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
    
    //declare 5 user and 5 comp ship instance variables
    private Ship userCarrier;
    private Ship userBattleship;
    private Ship userDestroyer;
    private Ship userSubmarine;
    private Ship userPatrolboat;
    private Ship compCarrier;
    private Ship compBattleship;
    private Ship compDestroyer;
    private Ship compSubmarine;
    private Ship compPatrolboat;
    
    //code a parameterized constructor to initialize the coordinate when the user's board is being created
    Grid(ArrayList<String> c) {
        //initialize coords ArrayList
        this.coordsList = c;
        
        //create instances of all 5 ships
        this.userCarrier = new Carrier(allCoords(this.coordsList.get(0), this.coordsList.get(1)));
        this.userBattleship = new Battleship(allCoords(this.coordsList.get(2), this.coordsList.get(3)));
        this.userDestroyer = new Destroyer(allCoords(this.coordsList.get(4), this.coordsList.get(5)));
        this.userSubmarine = new Submarine(allCoords(this.coordsList.get(6), this.coordsList.get(7)));
        this.userPatrolboat = new Patrolboat(allCoords(this.coordsList.get(8), this.coordsList.get(9)));
        
        //using the given coordinates, populate the arrGrid
        this.arrUserGrid = initializeGrid();
        
        //output the grid
        for (int r = 0; r < this.arrUserGrid.length; r++) {
            for (int d = 0; d < this.arrUserGrid.length; d++) {
                //output
                System.out.print(arrUserGrid[r][d] + " ");
            }
            System.out.println();
        }
    }
    
    //code a default constructor to be called when creating the computer's board and used to randomize the ship coords
    Grid() {
        //initialize the coords ArrayList using random generated ships positions
        this.coordsList = generateShips();
        
        //create instances of all 5 ships
        this.compCarrier = new Carrier(allCoords(this.coordsList.get(0), this.coordsList.get(1)));
        this.compBattleship = new Battleship(allCoords(this.coordsList.get(2), this.coordsList.get(3)));
        this.compDestroyer = new Destroyer(allCoords(this.coordsList.get(4), this.coordsList.get(5)));
        this.compSubmarine = new Submarine(allCoords(this.coordsList.get(6), this.coordsList.get(7)));
        this.compPatrolboat = new Patrolboat(allCoords(this.coordsList.get(8), this.coordsList.get(9)));
        
        //using the given coordinates, populate the arrGrid
        this.arrCompGrid = initializeGrid();
        
        //output the grid
        for (int r = 0; r < this.arrCompGrid.length; r++) {
            for (int c = 0; c < this.arrCompGrid.length; c++) {
                //output
                System.out.print(arrCompGrid[r][c] + " ");
            }
            System.out.println();
        }
    }
    
    //code getters for all 10 ships
    public Ship getUserCarrier() { return this.userCarrier; }
    public Ship getUserBattleship() { return this.userBattleship; }
    public Ship getUserDestroyer() { return this.userDestroyer; }
    public Ship getUserSubmarine() { return this.userSubmarine; }
    public Ship getUserPatrolboat() { return this.userPatrolboat; }
    public Ship getCompCarrier() { return this.compCarrier; }
    public Ship getCompBattleship() { return this.compBattleship; }
    public Ship getCompDestroyer() { return this.compDestroyer; }
    public Ship getCompSubmarine() { return this.compSubmarine; }
    public Ship getCompPatrolboat() { return this.compPatrolboat; }
    
    //code a ArrayList<String> method to randomly generate the ship coords for the computer grid and return them into the default constructor
    public ArrayList<String> generateShips() {
        //declare and initlialize an ArrayList to store the coords of the ships
        ArrayList<String> shipList = new ArrayList<String>();
        
        //declare an array to store possible coords for extending ship (2nd coord of ship)
        String[] arrEndCoord;
        
        //declare two String ArrayLists to store all coords for current ship and comparing ship
        ArrayList<String> currentList = new ArrayList<String>();
        ArrayList<String> otherList = new ArrayList<String>();
        
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
            if (bytRow - (bytSize - 1) >= 0) { //can extend up
                //populate 1st slot of end coord array
                arrEndCoord[0] = COLUMN_INDEX[bytCol] + String.valueOf(bytRow - (bytSize - 1));
            }
            if (bytCol + (bytSize - 1) <= 9) { //can extend right
                //populate 2nd slot of end coord array
                arrEndCoord[1] = COLUMN_INDEX[bytCol + (bytSize - 1)] + String.valueOf(bytRow);
            }
            if (bytRow + (bytSize - 1) <= 9) { //can extend down
                //populate 3rd slot of end coord array
                arrEndCoord[2] = COLUMN_INDEX[bytCol] + String.valueOf(bytRow + (bytSize - 1));
            }
            if (bytCol - (bytSize - 1) >= 0) { //can extend left
                //populate 4th slot of end coord array
                arrEndCoord[3] = COLUMN_INDEX[bytCol - (bytSize - 1)] + String.valueOf(bytRow);
            }
            
            //for loop to loop through coord list and make sure it extands in a way to not overlap with other ships
            //k currently loops through each ship individually
            for (int k = 0; k < shipList.size(); k += 2) {   
                //populate all coords from comparing ship
                otherList = allCoords(shipList.get(k), shipList.get(k+1));
                
                //for loop to check if each way of expanding works
                for (int l = 0; l <= 3; l++) {
                    //if statement to check if coord exist
                    if (arrEndCoord[l].equals("0")) {
                        //skip
                        continue;
                    }
                    else {
                        //populate all current coords
                        currentList = allCoords(strCoord, arrEndCoord[l]);
                        
                        //check that no coords overlap
                        for (int m = 0; m < currentList.size(); m++) {
                            ////check against every "other" coordinate
                            for (int n = 0; n < otherList.size(); n++) {
                                if (currentList.get(m).equals(otherList.get(n))) {
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
            
            //reset bytOptions
            bytOptions = 0;
            
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
    
    //code a int method to get indexes in arrays
    public int getIndexOf(char[] arrIndex, char chrTarget) {
        //for loop to loop through the array
        for (int i = 0; i < arrIndex.length; i++) {
            //check if it is the target variable
            if (arrIndex[i] == chrTarget) {
                //return index
                return i;
            }
        }
        
        //if not found return -1
        return -1;
    }
    
    //code a ArrayList<String> method to return all of the coords of a ship given start and end
    public ArrayList<String> allCoords(String strStart, String strEnd) {
        //declare and initialize an ArrayList to store coords
        ArrayList<String> coordList = new ArrayList<String>();

        //check if ship is vertical or horizontal
        if (strStart.charAt(1) == strEnd.charAt(1)) {   //horizontal
            //check which way it extends
            if (getIndexOf(COLUMN_INDEX, strStart.charAt(0)) < getIndexOf(COLUMN_INDEX, strEnd.charAt(0))) { //extends right
                //for loop to loop until end coord
                for (int i = getIndexOf(COLUMN_INDEX, strStart.charAt(0)); i <= getIndexOf(COLUMN_INDEX, strEnd.charAt(0)); i++) {
                    //add coordinates
                    coordList.add(COLUMN_INDEX[i] + String.valueOf(strStart.charAt(1)));
                }
                
            }
            else { //extends left
                //for loop to loop until end coord
                for (int i = getIndexOf(COLUMN_INDEX, strStart.charAt(0)); i >= getIndexOf(COLUMN_INDEX, strEnd.charAt(0)); i--) {
                    //add coordinates
                    coordList.add(COLUMN_INDEX[i] + String.valueOf(strStart.charAt(1)));
                }
            }
        }
        else {  //vertical
            //check which way it extends
            if (Character.getNumericValue(strStart.charAt(1)) < Character.getNumericValue(strEnd.charAt(1))) { //down
                //for loop to loop until end coord
                for (int i = Character.getNumericValue(strStart.charAt(1)); i <= Character.getNumericValue(strEnd.charAt(1)); i++) {
                    //add coordinates
                    coordList.add(strStart.charAt(0) + String.valueOf(i));
                }
            }
            else { //up
                //for loop to loop until end coord
                for (int i = Character.getNumericValue(strStart.charAt(1)); i >= Character.getNumericValue(strEnd.charAt(1)); i--) {
                    //add coordinates
                    coordList.add(strStart.charAt(0) + String.valueOf(i));
                }
            }
        }
        
        //return ArrayList of coords
        return coordList;
    }
    
    //code a char[][] method to use the coordinate list to generate a 10 x 10 board with the ships in place
    public char[][] initializeGrid() {
        //declare and initialize a 10x10 grid to be populated
        char[][] arrBoard = new char[10][10];
        
        //declare an ArrayList to store all coords for current ship
        ArrayList<String> currentList = new ArrayList<String>();
        
        //fill the grid with water '~'
        for (int r = 0; r < arrBoard.length; r++) {
            for (int c = 0; c < arrBoard[r].length; c++) {
                //fill
                arrBoard[r][c] = '~';
            }
        }
        
        //for loop to loop through each ship
        for (int i = 0; i < this.coordsList.size(); i += 2) {
            //populate all coords from ship
            currentList = allCoords(this.coordsList.get(i), this.coordsList.get(i+1));
            
            //for loop to loop through each coordinate
            for (int j = 0; j < currentList.size(); j++) {
                //if statement to check what type of ship it is
                if (i == 0) {   //Carrier
                    //rewrite 'C' for all coords
                    arrBoard[Character.getNumericValue(currentList.get(j).charAt(1))][getIndexOf(COLUMN_INDEX, currentList.get(j).charAt(0))] = 'C';
                }
                else if (i == 2) {  //Battleship
                    //rewrite 'B' for all coords
                    arrBoard[Character.getNumericValue(currentList.get(j).charAt(1))][getIndexOf(COLUMN_INDEX, currentList.get(j).charAt(0))] = 'B';
                }
                else if (i == 4) {  //Destroyer
                    //rewrite 'D' for all coords
                    arrBoard[Character.getNumericValue(currentList.get(j).charAt(1))][getIndexOf(COLUMN_INDEX, currentList.get(j).charAt(0))] = 'D';
                }
                else if (i == 6) {  //Submarine
                    //rewrite 'S' for all coords
                    arrBoard[Character.getNumericValue(currentList.get(j).charAt(1))][getIndexOf(COLUMN_INDEX, currentList.get(j).charAt(0))] = 'S';
                }
                else if (i == 8) {  //Patrolboat
                    //rewrite 'P' for all coords
                    arrBoard[Character.getNumericValue(currentList.get(j).charAt(1))][getIndexOf(COLUMN_INDEX, currentList.get(j).charAt(0))] = 'P';
                }
            }
        }
        
        //return the grid
        return arrBoard;
    }
}