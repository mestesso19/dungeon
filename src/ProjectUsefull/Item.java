package ProjectUsefull;

import Interfaces.*;
import Panel.*;
import Main.*;
import MainProject.*;
        
public class Item {
    
    public int numChiave;
    public int numDisegno;
    
    public Item(int num){
        this.numDisegno = num;
    }
    
    public int getNumDisegno(){
        return numDisegno;
    }
    
    public int getNumItem(){
        return -1;
    }
    
}
