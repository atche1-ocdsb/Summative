/*
 * Description: This class will be the driver class, which will run the rest 
 * of the program. It will call the Gamemanager class to take over managing 
 * the program.
 *
 * @author (Artem Tchernov)
 * @version (June 2nd - June _)
 */

public class TestClass {
    public static void main(String args[]) {
        //create an instance of the GameManager class
        GameManager g1 = new GameManager();
        
        //call the run method in gamemanager to run the game
        g1.run();
    }
}