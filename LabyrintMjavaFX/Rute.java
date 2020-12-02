import java.util.ArrayList;
import java.io.*;
abstract class Rute{

    public ArrayList<Rute> listemedNaboer = new ArrayList<Rute>();
    public static ArrayList<Rute> besokte = new ArrayList<Rute>();
    public static ArrayList<String> listemedutveier = new ArrayList<String>();
    protected Rute nord = null;
    protected Rute sor = null;
    protected Rute oest = null;
    protected Rute vest = null;
    protected int xKoord = 0;
    protected int yKoord = 0;
    protected Labyrint laby = null;
   
   // protected String utskrift = "";
    public Rute(int xKoord,int yKoord){
            this.xKoord = xKoord;
            this.yKoord = yKoord;
    }
    
    public void nordSettNabo(Rute rute){
        nord = rute;
        listemedNaboer.add(rute);        
    }
    
    public void sorSettNabo(Rute rute){
        sor = rute;
        listemedNaboer.add(rute);
    }
    
    public void oestSettNabo(Rute rute){
        oest = rute;
        listemedNaboer.add(rute);
    }
    
    public void vestSettNabo(Rute rute){
        vest = rute;
        listemedNaboer.add(rute);
    }

    public void settLabyrint(Labyrint labyrinten){
        laby=labyrinten;
    }
    public int hentXkoord(){
        return xKoord;
    }
    public int hentYkoord(){
        return yKoord;
    }


    public void gaa(String utskrift){ 
    
        if(this.utvei()){
            utskrift+="("+hentXkoord() + "," + hentYkoord()+")";
            listemedutveier.add(utskrift);
            return;

            
        }
        
        for(Rute nabo : listemedNaboer){
            if (!besokte.contains(nabo) && nabo.chartilTegn().compareTo(".")==0){
                besokte.add(this);
                
                utskrift+="("+hentXkoord() + "," + hentYkoord()+")"+"-->";
                nabo.gaa(utskrift);
                
            }
        }
        return;
        
        

    }
    public void finnUtvei(){
        besokte.clear();
        besokte.add(this);
        gaa("");
        laby.leggTilListe(listemedutveier);
        
    
        tomListe();
        
        
       
    }
    
    public Boolean utvei(){
        return false;
    }
    public void tomListe(){
        listemedutveier.clear();
      
      

    }

    abstract public String chartilTegn();
}


















