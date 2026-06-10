/*
 * Description: This subclass will store the characteristics of the 3-length ship. This ship could receive the repositioning characteristic.

 *
 * @author (Artem Tchernov)
 * @version (June 2nd - June 10th)
 */
import java.util.ArrayList;

public class Submarine extends Ship{
    //code a parameterized constructor to initialize the instance variable
    Submarine(ArrayList<String> a) {
        //initialize the coordlist through the superclass
        super(a);
    }
    
    //code a toString to be used when the ship is destroyed
    @Override
    public String toString() {
        return super.toString() + " The Submarine has been sunk.";
    }
}