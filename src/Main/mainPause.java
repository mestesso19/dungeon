package Main;

import Interfaces.*;
import ProjectUsefull.*;
import MainProject.*;
import Panel.*;
        
import java.awt.*;
import javax.swing.*;

public class mainPause extends JFrame{
    public static final Dimension DIM_FRAME_PAUSE = new Dimension(290,270);
    private panelPause p;
    
    public mainPause(){
        p = new panelPause();

        add(p);
        setTitle("Pause");
        setSize(new Dimension(DIM_FRAME_PAUSE));
        setLocationRelativeTo(null);
        setUndecorated(true);
    }
    
}
