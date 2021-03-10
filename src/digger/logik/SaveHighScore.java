/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digger.logik;

/**
 *
 * @author ghait
 */
public class SaveHighScore implements java.io.Serializable {
 
	private static final long serialVersionUID = 1L;
	public int [] highScore;
  
     
     
     SaveHighScore(int [] high){
     
     highScore=high;
     }
}
