package Main;

import Interfaces.*;
import ProjectUsefull.*;
import MainProject.*;
import Panel.*;
        
import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class mainMenu extends JFrame{
   
    public static final Dimension DIM_FRAME_MENU = new Dimension(1000,600);
    private panelMenu p;
    
    public mainMenu(){

        p = new panelMenu();

        add(p);
        setTitle("Dungeon");
        setSize(new Dimension(DIM_FRAME_MENU));
        setLocationRelativeTo(null);
        setUndecorated(true);
    }
    
}
