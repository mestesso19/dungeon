package MainProject;

import Interfaces.*;
import ProjectUsefull.*;
import Main.*;
import Panel.*;

import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;

public class Stanze implements ListaAttributi{
    
    private Matrix [] mat;
    private int [][] m;
    private int num, numMat;
    private Image [] img;
    private Image img2;
    public Chest [] chest = new Chest[2];

    private Item [] inventario;
    private Item chiaveBlu = new Chiave(0, 6);
    private Item chiaveRossa = new Chiave(1, 7);
    
    private int NchiaveBlu = 0;
    private int NchiaveRossa = 0;
    
    private int chiaviInserite = 0;
    private int portaAperta = 0;
    
    public Stanze(Matrix [] mat){
        this.mat = mat;
        this.numMat = 0;
        
        inventario = new Item[5];
        
        for(int i=0;i<5;i++)
            inventario[i] = new Item(5);
        
        setM(0);      // imposto la stanza iniziale
        ImgIniz();
        
        chest[0] = new Chest(0);
        chest[1] = new Chest(1);
    }
    
    public void setChiavi(){
        inventario[0] = chiaveBlu;
        inventario[1] = chiaveRossa;
        NchiaveBlu = 1;
        NchiaveRossa = 1;
        m[9][9] = 6;
        m[9][10] = 7;
    }
    
    public void setM(int num){
        m = mat[num].getMat();
        numMat = num;
        
        for(int i=0;i<5;i++)
            m[9][9+i] = inventario[i].getNumDisegno();
    }
    
    public void ImgIniz(){
        
        img = new Image[14];
        
        try{
            img2 = ImageIO.read(new File("ImgGioco/mappa.png"));
            
            img[0] = ImageIO.read(new File("ImgStanze/vuoto.jpg"));
            img[1] = ImageIO.read(new File("ImgStanze/muro.jpg"));
            img[2] = ImageIO.read(new File("ImgStanze/pavimento.jpg"));
            img[3] = ImageIO.read(new File("ImgStanze/acqua.jpg"));
            img[4] = ImageIO.read(new File("ImgStanze/chest.png"));
            img[5] = ImageIO.read(new File("ImgStanze/inventario.png"));
            img[6] = ImageIO.read(new File("ImgStanze/inv_ChiaveBlu.png"));
            img[7] = ImageIO.read(new File("ImgStanze/inv_ChiaveRossa.png"));
            img[8] = ImageIO.read(new File("ImgStanze/porta.png"));
            img[9] = ImageIO.read(new File("ImgStanze/serratura.png"));
            img[10] = ImageIO.read(new File("ImgStanze/serraturaBlu.png"));
            img[11] = ImageIO.read(new File("ImgStanze/serraturaRossa.png"));
            img[12] = ImageIO.read(new File("ImgStanze/portaAperta.png"));
            img[13] = ImageIO.read(new File("ImgStanze/chestAperta.png"));

        }
        catch(IOException ex){
            System.out.println("Errore nel trovare immagini");
        }
        
    }
    
    public void draw(Graphics2D g2D){
        if(Gioco.fPressed == false){
            for (int i=0, y=0; i < numCaselleVer; i++, y += caselleSize) {
                for (int j=0, x=0; j < numCaselleOriz; j++, x += caselleSize) {
                    g2D.drawImage(img[m[i][j]], x, y, caselleSize, caselleSize, null);
                }
            }
            Gioco.pause = true;
        }
        else{
            Gioco.pause = false;
            g2D.drawImage(img2, 250, 100, 600, 550, null);
        }
    }
    
    public void controlloUscita(Avventuriero umano){
        for(int i=0;i<4;i++){
            if(mat[numMat].usc[i] != null){
                num = mat[numMat].usc[i].controlloUscita(umano);
                if(num != -1){
                    if(numMat != 4 || portaAperta != 0){
                        mat[numMat].usc[i].respawn(umano);
                        setM(num);
                    }
                }
            }
        }
    }
    
    public int controlloChest(Avventuriero umano){
        if(this.numMat == 8 && chest[0].controlloChest(umano) == 0){
            if(insertItem(chiaveBlu) == true){
                NchiaveBlu = 1;
                m[2][7] = 13;
                return 0;
            }
        }
        else if(this.numMat == 12 && chest[1].controlloChest(umano) == 1){
            if(insertItem(chiaveRossa) == true){
                NchiaveRossa = 1;
                m[2][7] = 13;
                return 1;
            }
        }
        return -1;
    }
    
    public void controlloSerratura(Avventuriero umano){
        if(NchiaveBlu == 1 && umano.getX() >= 340 && umano.getX() <= 420 && umano.getY() <= 225){
            m[1][5] = 10;
            cancelItem(chiaveBlu);
            NchiaveBlu = 0;
            chiaviInserite++;
        }
        else if(NchiaveRossa == 1 && umano.getX() >= 640 && umano.getX() <= 720 && umano.getY() <= 225){
            m[1][9] = 11;
            cancelItem(chiaveRossa);
            NchiaveRossa = 0;
            chiaviInserite++;
        }
        if(chiaviInserite == 2){
            m[1][7] = 12;
            chiaviInserite = 0;
            portaAperta = 1;
        }
    }
    
    public boolean insertItem(Item o){
        for(int i=0;i<5;i++){
            if(inventario[i] == o)
                break;
            if(inventario[i].getClass() == Item.class){
                inventario[i] = o;
                m[9][9+i] = o.getNumItem();
                return true;
            }
        }
        
        return false;
    }
    
    public void cancelItem(Item o){
        for(int i=0;i<5;i++){
            if(inventario[i] == o){
                inventario[i] = new Item(5);
                m[9][9+i] = 5;
                sortInventario();
            }
        }
    }
    
    private void sortInventario(){
        for(int i=0;i<5;i++){
            if(inventario[i].getClass() == Item.class){
                for(int j=i;j<5;j++){
                    if(inventario[j].getClass() != Item.class){
                        inventario[i] = inventario[j];
                        inventario[j] = new Item(5);
                    }
                }
            }
        }
        setM(numMat);
    }
    
}