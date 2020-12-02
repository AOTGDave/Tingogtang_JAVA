import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;


class Labyrint {
public static int rader = 0;
private static int kolonner = 0;
public static Rute labyrinter[][] = new Rute[rader][kolonner];
public static Lenkeliste<String> utveier = new Lenkeliste<>();
public static Lenkeliste<boolean[][]> utveierNye= new Lenkeliste<>();
private static int strl;



    private Labyrint(Rute labyrinter[][], int kolonner , int rader){
        this.labyrinter= labyrinter;
        this.kolonner = kolonner;
        this.rader = rader;

    }
    public static Labyrint lesFraFil(File filnavn)throws FileNotFoundException{
        int rader = 0;
        int kolonner = 0;
        Rute labyrinter[][]= new Rute[rader][kolonner];;

        Scanner scanner = null;
        scanner = new Scanner(filnavn);

        String innlest = scanner.nextLine();
        while(scanner.hasNextLine()){

        String [] labyrintSpeks = innlest.split(" ");
        rader = Integer.parseInt(labyrintSpeks[0]);
        kolonner = Integer.parseInt(labyrintSpeks[1]);
        labyrinter = new Rute[rader][kolonner];

        for(int i = 0; i < rader; i++){

            innlest = scanner.nextLine();
            labyrintSpeks = innlest.split("");

            for(int j = 0; j < kolonner; j++){

                if(labyrintSpeks[j].compareTo(".")==0){
                   if((i==0)||(i==rader-1)||(j==0)||(j==kolonner-1)){
                    Rute nyrute = new Aapning(j,i);
                        labyrinter[i][j] = nyrute;
                   }
                   else{
                        HvitRute nyrute = new HvitRute(j,i);
                        labyrinter[i][j] = nyrute;
                   }
                }

                else{
                    SortRute nyrRute = new SortRute(j,i);
                    labyrinter[i][j] = nyrRute;
                }
            }
        }
    }
    scanner.close();
    //Utskrift(labyrinter, rader, kolonner);
    Labyrint lab = new Labyrint(labyrinter, rader, kolonner);
    SettNaboOgLabyrint(lab,labyrinter, rader, kolonner);
    return lab;


    }
    public static void Utskrift(Rute labyrinter[][],int rader , int kolonner){
        for(int i = 0; i < rader; i++){
            System.out.println("");
            for(int j = 0; j < kolonner; j++){

                System.out.print(labyrinter[i][j].chartilTegn());


            }
        }
    }
    public static void SettNaboOgLabyrint(Labyrint lab,Rute labyrinter[][],int rader,int kolonner){
        for(int i = 0; i < rader; i++){
            for(int j = 0; j < kolonner; j++){

                if(labyrinter[i][j] instanceof HvitRute){
                    if(i-1>= 0){
                    labyrinter[i][j].nordSettNabo(labyrinter[i-1][j]);
                    }
                    if(i+1 <=rader-1){
                    labyrinter[i][j].sorSettNabo(labyrinter[i+1][j]);
                    }
                    if(j-1 >= 0){
                    labyrinter[i][j].vestSettNabo(labyrinter[i][j-1]);

                    }
                    if(j+1 <=kolonner-1){
                    labyrinter[i][j].oestSettNabo(labyrinter[i][j+1]);

                    }

                }
                labyrinter[i][j].settLabyrint(lab);
            }

        }

    }
    public static void leggTilListe(ArrayList<String> outveier){
        
        
        for(String utvei: outveier){
            utveier.leggTil(utvei);
        }
       
        
    }

    public static boolean[][] finnUtveiFra(int kol, int rad){
        utveier = new Lenkeliste<String>();
        
        labyrinter[rad][kol].finnUtvei();
        for(String utvei : utveier){
            boolean[][] utve =  losningStringTilTabell(utvei, getKolonner(),getRader());
            return utve; 
            
        }
        return null;
            
    }


    static boolean[][] losningStringTilTabell(String losningString, int bredde, int hoyde) {
        boolean[][] losning = new boolean[bredde][hoyde];
        java.util.regex.Pattern p = java.util.regex.Pattern.compile("\\(([0-9]+),([0-9]+)\\)");
        java.util.regex.Matcher m = p.matcher(losningString.replaceAll("\\s",""));
        while (m.find()) {
            int x = Integer.parseInt(m.group(1));
            int y = Integer.parseInt(m.group(2));
            losning[y][x] = true;
        }
        
        return losning;
    }
    
    public static int getKolonner() {
        return kolonner;
    }
   
    public static int getRader() {
        return rader;
    }
    
    public static Rute[][] getLabyrinter() {
        return labyrinter;
    }
   
    public static int getAntUtveier() {
        strl= 0;
        strl = utveier.stoerrelse();
        return strl;

        
    }
    
}
