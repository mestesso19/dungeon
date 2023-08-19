package MainProject;

import Interfaces.*;
import ProjectUsefull.*;
import Main.*;
import Panel.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Gioco extends JFrame implements Runnable, KeyListener, ListaAttributi{
    
    private JPanel mainPan;
    private JPanel pan;
    public static Thread t;
    private Avventuriero umano;
    private Matrix [] mat;
    private Stanze stanze;
    public static mainPause p;
    
    public boolean wPressed, aPressed, sPressed, dPressed;
    public static boolean fPressed;
    public boolean spacePressed;
    public static boolean run = true;
    public static boolean pause = true;
    public int print, printCounter, printAttacco;
    public static int tasto = -1;
    
    private Graphics2D g2D;
    public Robot robot;
    
    public static int luminosita = 50;
    public static int musica = 50;
    public static int FPS = 60;          // Numero di volte che viene ripetuto il loop nel metodo run al secondo
    public static int modalita = -1;     // facile, normale o difficile
    
    public Gioco(){
        
        setUndecorated(true);
        
        addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                Dungeon.quitGame();
            }
        });
        
        mainPan = new JPanel();
        pan = new JPanel();
        umano = new Avventuriero();
        
        mat = new Matrix[14];
        
        mat[0] = new Matrix("FileStanze/stanzaIniziale.txt");
        mat[1] = new Matrix("FileStanze/stanzaSnodo.txt");
        mat[2] = new Matrix("FileStanze/stanzaD1.txt");
        mat[3] = new Matrix("FileStanze/stanzaS1.txt");
        mat[4] = new Matrix("FileStanze/stanzaC1.txt");
        mat[5] = new Matrix("FileStanze/stanzaS2.txt");
        mat[6] = new Matrix("FileStanze/stanzaS3.txt");
        mat[7] = new Matrix("FileStanze/stanzaS4.txt");
        mat[8] = new Matrix("FileStanze/stanzaS5.txt");
        mat[9] = new Matrix("FileStanze/stanzaD2.txt");
        mat[10] = new Matrix("FileStanze/stanzaD3.txt");
        mat[11] = new Matrix("FileStanze/stanzaD4.txt");
        mat[12] = new Matrix("FileStanze/stanzaD5.txt");
        mat[13] = new Matrix("FileStanze/stanzaC2.txt");

        stanze = new Stanze(mat);
        
        printCounter = 0;
        print = 0;
        printAttacco = 0;
        
        mainPan.setBackground(Color.black);
        
        pan.setPreferredSize(new Dimension(numCaselleOriz*caselleSize,numCaselleVer*caselleSize));
        pan.setDoubleBuffered(true);
        
        t = new Thread(this);
        t.start();
        
        addKeyListener(this);
        
        mainPan.add(pan);
        
        add(mainPan);
        setTitle("Dungeon");
        this.pack();
        //setSize(caselleOriz*caselleSize,caselleVer*caselleSize);
        setLocationRelativeTo(null);
    }
    
    public void run(){    //  metodo run con loop continuo
        
        double periodo = 1000000000/FPS;
        double delta = 0;
        long ultimoTempo = System.nanoTime();
        long tempoAttuale;
        
        run = true;
        pause = true;
        
        while(run){
            while(pause){
                tempoAttuale = System.nanoTime();
                delta += (tempoAttuale - ultimoTempo) / periodo;
                ultimoTempo = tempoAttuale;

                if(delta >= 1){
                    update();
                    printAttacco++;

                    repaint();

                    delta--;
                }
            }
            
            updateMappa();
        }
        
    }
    
    public void update(){      // Aggiornamenti del gioco che passeranno poi per il metodo paint()
        
        if(wPressed == true){
            umano.MovY(-speed);
            umano.setDirezione(0);
            stanze.controlloUscita(umano);    
        }
        if(aPressed == true){
            umano.MovX(-speed);
            umano.setDirezione(2);
            stanze.controlloUscita(umano);
        }
        if(sPressed == true){
            umano.MovY(speed);
            umano.setDirezione(4);
            stanze.controlloUscita(umano);
        }
        if(dPressed == true){
            umano.MovX(speed);
            umano.setDirezione(6);
            stanze.controlloUscita(umano);
        }
        if(spacePressed == true && printAttacco==10){
            spacePressed = false;
            printAttacco=0;
        }
        
        printCounter++;
        
        if(printCounter > 30){
            if(print == 0)     print = 1;
            else             print = 0;
            printCounter = 0;
        }
        
    }
    
    public void updateMappa(){
        if(tasto == 'F')  fPressed = true;
        else              fPressed = false;
        
        repaint();
    }
    
    public void paint(Graphics g){     // Grafica del gioco
        
        g2D = (Graphics2D) g;
        
        stanze.draw(g2D);
        if(tasto != 'F')
            umano.draw(g2D, print);
        
        g2D.dispose();
        
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        tasto = e.getKeyCode();
        
        if(tasto == KeyEvent.VK_W){
            wPressed = true;
        }
        if(tasto == KeyEvent.VK_A){
            aPressed = true;
        }
        if(tasto == KeyEvent.VK_S){
            sPressed = true;
        }
        if(tasto == KeyEvent.VK_D){
            dPressed = true;
        }
        if(tasto == ' '){
            spacePressed = true;
            printAttacco = 0;
        }
        if(tasto == 'E'){
            int result = stanze.controlloChest(umano);
            if(result != -1)
                umano.chiaveTrovata();
            stanze.controlloSerratura(umano);
        }
        if(tasto == 'G')
            stanze.setChiavi();
        if(e.getKeyCode() == 27){
            p = new mainPause();
            p.setVisible(true);
            this.pause = false;
        }
        if(tasto == 'F')
            fPressed = true;
    }

    @Override
    public void keyReleased(KeyEvent e) { 
        
        int tasto = e.getKeyCode();
        
        if(tasto == KeyEvent.VK_W){
            wPressed = false;
        }
        if(tasto == KeyEvent.VK_A){
            aPressed = false;
        }
        if(tasto == KeyEvent.VK_S){
            sPressed = false;
        }
        if(tasto == KeyEvent.VK_D){
            dPressed = false;
        }
        
    }
    
    public static void setValue(int l, int m, int FPS){
        luminosita = l;
        musica = m;
        Gioco.FPS = FPS;
    }
    
    public static void stopPause(){
        p.dispose();
    }
    
}
