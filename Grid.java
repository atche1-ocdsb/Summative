/*
 * Description: This class will be responsible for constructing both the user’s 10x10 grid (user input) and the computer’s grid (randomized). 
 * When creating the grid, this class will create instances of the 5 ships and “position” them on the grid.
 *
 * @author (Artem Tchernov)
 * @version (June 2nd - June _)
 */
import java.util.ArrayList; //import ArrayLists to be used to store the coords of the ships

public class Grid {
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
    
    //declare and initialize a private 2d array of type char to be used as the 10 by 10 game grids
    private char[][] arrGrid = new char[10][10];
    
    
    //code a parameterized constructor to initialize the coordinate when the user's board is being created
    Grid(ArrayList<String> c) {
        //initialize coords ArrayList
        this.coordsList = c;
        
        
    }
    
    //code a default constructor to be called when creating the computer's board and used to randomize the ship coords
    Grid() {
        //initialize the coords ArrayList using random generated ships positions
        this.coordsList = generateShips();
        
        
    }
    
    //code a ArrayList<String> method to randomly generate the ship coords for the computer grid and return them into the default constructor
    public ArrayList<String> generateShips() {
        //declare and initlialize an ArrayList to store the coords of the ships
        ArrayList<String> shipList = new ArrayList<String>();
        
        //declare and initialize a char array of the 10 letters representing the columns
        char[] arrColumns = new char[]{'A','B','C','D','E','F','G','H','I','J'};
        
        //declare an array to store possible coords for extending ship (2nd coord of ship)
        String[] arrEndCoord;
        
        //declare two bytes to store row index and col index
        byte bytRow;
        byte bytCol;
        
        //declare a string to momentarily store the created coords
        String strCoord;
        
        //declare a boolean to deal with regeneration
        boolean bolLoop;
        
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
                strCoord = arrColumns[bytCol] + String.valueOf(bytRow);
                
                //check if this coordinate is already in the list, if so regenerate
                for (int j = 0; j < shipList.size(); j++) {
                    if (shipList.get(j).equals(strCoord)) {
                        //regenerate
                        bolLoop = true;
                    }
                }
            }
            while (bolLoop);
            
            //add the coordinate to the arrayList
            shipList.add(strCoord);
            
            //reset end coord array to default of 0
            arrEndCoord = new String[]{"0", "0", "0", "0"};
            
            //check which ship it is based on i variable to generate second coord
            if (i == 0) {       //Carrier - size 5
                //check how many of the four positions are possible to extend: up, right, down, left
                if (bytRow - 4 >= 0) { //can extend up
                    //populate 1st slot of end coord array
                    arrEndCoord[0] = arrColumns[bytCol] + String.valueOf(bytRow - 4);
                }
                if (bytRow - 4 >= 0) { //can extend right
                    //populate 1st slot of end coord array
                    arrEndCoord[0] = arrColumns[bytCol] + String.valueOf(bytRow - 4);
                }
                if (bytRow + 4 >= 0) { //can extend down
                    //populate 3rd slot of end coord array
                    arrEndCoord[0] = arrColumns[bytCol] + String.valueOf(bytRow + 4);
                }
                if (bytRow - 4 >= 0) { //can extend up
                    //populate 1st slot of end coord array
                    arrEndCoord[0] = arrColumns[bytCol] + String.valueOf(bytRow - 4);
                }
            }
            else if (i == 1) {  //Battleship - size 4
                
            }
            else if (i == 2) {  //Destroyer - size 3
                
            }
            else if (i == 3) {  //Submarine - size 3
                
            }
            else if (i == 4) {  //Patrolboat - size 2
                
            }
        }
        
        //return the coord list
        return shipList;
    }
}