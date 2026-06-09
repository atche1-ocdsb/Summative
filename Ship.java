/*
 * Description: This class will act as the superClass for all the ship types, responsible for storing the general/shared methods 
 * for all the ships to be inherited by all of the specific ship subclasses. 
 *
 * @author (Artem Tchernov)
 * @version (June 2nd - June 10th)
 */
import java.util.ArrayList;

public class Ship {
    //declare private instance ArrayList<String> variable to store all ship coords
    private ArrayList<String> allCoordsList;
    
    //code a parameterized constructor to initialize the instance variable
    Ship(ArrayList<String> a) {
        //initialize instance variable
        this.allCoordsList = a;
    }
    
    //code a boolean method to check if the ship has been hit
    public boolean isHit(String strShot, ArrayList<String> coordsList) {
        //for loop to compare the shot coordinate to the ship coordinates
        for (int i = 0; i < coordsList.size(); i++) {
            //if statement to check
            if (strShot.equals(coordsList.get(i))) {
                //return that the ship has been hit
                return true;
            }
        }
        
        //if looped through with no hits, return false
        return false;
    }
    
    //code a getter for the coordlist
    public ArrayList<String> getCoordList() {
        return this.allCoordsList;
    }
    
    //code a boolean method to check if the ship has been destroyed
    public boolean isDestroyed(String[] arrHits, ArrayList<String> coordsList) {
        //declare a boolean array of same length as coords array
        boolean[] arrIsHit = new boolean[coordsList.size()];
        
        //loop through the boolean aray to set everything as false
        for (int i = 0; i < arrIsHit.length; i++) {
            //set to false
            arrIsHit[i] = false;
        }
        
        //for loop to compare the two arrays
        for (int i = 0; i < coordsList.size(); i++) {
            //for loop to loop through second array
            for (int j = 0; j < arrHits.length; j++) {
                //if statement to check if this coord exists
                if (coordsList.get(i).equals(arrHits[j])) {
                    //set the coord index to true
                    arrIsHit[i] = true;
                }
            }
        }
        
        //check if the entire boolean array is true
        for (int i = 0; i < arrIsHit.length; i++) {
            //if statement to check if true
            if (arrIsHit[i] == false) {
                //not destroyed
                return false;
            }
        }
        
        //since all indexes are true, the ship is destroyed, return true
        return true;
    }
}