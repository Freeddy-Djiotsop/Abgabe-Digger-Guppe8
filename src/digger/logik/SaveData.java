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
public class SaveData implements java.io.Serializable {
    
    private static final long serialVersionUID   = 1L; 
    public int [][]screenDataCurrent;
    public int scoreCurrent;
    public int d;
    public int dyingNrCurrent;
    public int [] monsterXCurrent;
    public int [] monsterYCurrent;
    public int diggerXCurrent;
    public int diggerYCurrent;
    public SaveData(int [][] screenData,int  score,int dyingNr,int [] monsterX,int [] monsterY,int diggerX,int diggerY){
    
    dyingNrCurrent=dyingNr;
    scoreCurrent=score;
    screenDataCurrent=screenData;
    monsterXCurrent=monsterX;
    monsterYCurrent=monsterY;
    diggerXCurrent=diggerX;
    diggerYCurrent=diggerY;
    }
    
    
}
