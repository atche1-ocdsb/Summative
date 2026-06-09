/*
 * Description: This class will store the username, password and high score of the user (counted in lowest amount of shots to win the game).
 *
 * @author (Artem Tchernov)
 * @version (June 2nd - June 10th)
 */

public class Player {
    //declare private strings to store name and password
    private String strName;
    private String strPassword;
    
    //declare a private byte to store score
    private byte bytScore;
    
    //code a parameterized constructor to initialize the instance variables
    Player(String n, String p, byte s) {
        //initialize the instance variables
        this.strName = n;
        this.strPassword = p;
        this.bytScore = s;
    }
    
    //code a getter method for the score
    public byte getScore() {
        return this.bytScore;
    }
    
    //code a setter method for the score
    public void setScore(byte bytNewScore) {
        this.bytScore = bytNewScore;
    }
}