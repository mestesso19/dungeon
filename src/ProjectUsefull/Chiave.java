package ProjectUsefull;

import Interfaces.*;
import Panel.*;
import Main.*;
import MainProject.*;
 
public class Chiave extends Item{
    
    public int numChiave;
    
    public Chiave(int num, int disegno){
        super(disegno);
        this.numChiave = num;
    }
    
    public int getNumDisegno(){
        return super.numDisegno;
    }
    
    public int getNumItem(){
        return numDisegno;
    }
    
}
