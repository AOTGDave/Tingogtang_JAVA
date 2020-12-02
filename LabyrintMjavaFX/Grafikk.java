import java.util.ArrayList;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.event.ActionEvent;
import javafx.event.*;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextField;


public class Grafikk extends Application {
    
    public Lenkeliste utveier = null;
    public static Labyrint l = null ;
    public static int rader, kolonner;
    public static GridPane pane = null;
    public static MinKlasse handle = null;
    public static int strls;
    public static Rectangle[][] listemedRektangler = new Rectangle[rader][kolonner]; 
    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage teater){
        Labyrint l = null ;
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(teater);
        
        try {
            l = Labyrint.lesFraFil(selectedFile);
        } catch (FileNotFoundException e) {
            System.out.println("feil!");
            System.exit(1);
        }
        
        HBox hbox = new HBox();
        GridPane pane = new GridPane();
        Rute[][] labyrinten = l.getLabyrinter();
        MinKlasse handle = new MinKlasse(hbox);
        rader = l.getRader();
        kolonner = l.getKolonner();
        listemedRektangler = new Rectangle[rader][kolonner];
        if(kolonner < rader){
            strls = 600/kolonner;
        }
        else{
            strls = 600/rader; 
        }
        
        for (int i = 0; i < kolonner; i++){
            for (int j = 0; j < rader; j++){

                Rute sjekke = labyrinten[i][j];
                
                if(sjekke instanceof HvitRute){
                   
                    Rectangle boks = new Rectangle(strls, strls, Color.WHITE);
                    boks.setOnMouseClicked(handle);
                   // boks.setStroke(Color.BLACK);
                    GridPane.setColumnIndex(boks, i);
                    GridPane.setRowIndex(boks, j);
                    StackPane beholder = new StackPane();

                    beholder.getChildren().add(boks);
                    pane.add(beholder, j, i);          
                    listemedRektangler[j][i]= boks;
                }
                else{
                
                    Rectangle boks = new Rectangle(strls, strls, Color.BLACK);
                    boks.setStroke(Color.BLACK);
                    StackPane beholder = new StackPane();
                    beholder.getChildren().add(boks);
                    pane.add(beholder, j, i);
                    listemedRektangler[j][i]= boks;
                
            }                
        }
    }
    


        hbox.getChildren().add(pane);
        Scene scene = new Scene(hbox);

        teater.setScene(scene);
        teater.setTitle("Labyrint");
        teater.show();
    }
}
class MinKlasse implements EventHandler<MouseEvent>{
    HBox hbox = new HBox();
    Text tekst = new Text();
    
    static ArrayList<Rectangle> listeMedRoede= new ArrayList<Rectangle>();
    
    public MinKlasse(HBox hbox){
        this.hbox = hbox;
        
    }
        
    public void handle(MouseEvent e){
        tekst.setText("");

        int strls = Grafikk.strls;

        Rectangle denne = (Rectangle) e.getSource();
        int y = GridPane.getColumnIndex(denne);
        int x = GridPane.getRowIndex(denne);
        Labyrint labyrint = Grafikk.l;
       

        
        boolean[][] utvei = labyrint.finnUtveiFra(x, y);
        if(listeMedRoede.size()!=0){
            for(int i = 0 ;i< listeMedRoede.size();i++){
                listeMedRoede.get(i).setFill(Color.WHITE);
            }
        }
        
        if(utvei== null){
            System.out.println("ingen utvei");
                    
        }
        else{
            for(int i = 0; i< labyrint.getRader();i++){
                for(int j = 0; j< labyrint.getKolonner();j++){
                    if(utvei[j][i]==true){
                        Grafikk.listemedRektangler[i][j].setFill(Color.RED);
                        listeMedRoede.add(Grafikk.listemedRektangler[i][j]);
                    }
                }
            }
        }
       
        tekst.setText("Antall utveier "+ Labyrint.getAntUtveier());
       // Grafikk.pane.add(tekst, 1000, 1000);
        hbox.getChildren().add(tekst);
        //hbox.getChildren().addAll(pane,tekst); 
       

    }
}









        
       /* if(utvei == null){
            System.out.println("lol, ingen utvei");
        }
        else{
            for(int i = 0; i<Grafikk.kolonner; i++){
                for(int j = 0; j<Grafikk.rader; j++){
                    if(utvei[i][j]==true){
                        for (Node rute :pane.getChildren()) {
                            StackPane stack = rute;
                            if (GridPane.getColumnIndex(rek) == j && GridPane.getRowIndex(rek) == i) {
                              //  rek.setFill(Color.RED);
                            }
                        }    
                    } 
                }
            }     
        }
    }
}*/

   


