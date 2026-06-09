/*
 * Description: This subclass will store the characteristics of the 5-length ship. This ship could receive the recon ability.
 *
 * @author (Artem Tchernov)
 * @version (June 2nd - June 10th)
 */
import java.util.ArrayList;

public class Carrier extends Ship{
    //code a parameterized constructor to initialize the instance variable
    Carrier(ArrayList<String> a) {
        //initialize the coordlist through the superclass
        super(a);
    }
}