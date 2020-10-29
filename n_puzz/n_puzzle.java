import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner; // Import the Scanner class to read text files
class n_puzzle{
    static int n;
    static int[][] goalstate;
    static int states = 0;
    public static void main(String [] args){
       
        int[][] board = readFromFile(args[0]);
        Boards start = new Boards(board,n,0);
        //Uloeselig hvis antall inverse er oddetall.
        int inversions = findInversions(board);
        System.out.println(inversions);
        
        if(inversions%2!=0){
          System.out.println("NOT SOLVABLE");
         // return;
        }

        System.out.println("inversions "+ inversions);

        
        Boards solution = solve(start);
        writeToFile(solution,board, args[1]);
      /*
        
        
        int [][] feil = new int[][] {{1,2,0},{4,5,6},{7,3,8}};
        int [][] bipbop = new int[][]{{1,2,3},{0,6,5},{7,8,4}};
        int [][] bipbop2 = new int[][]{{1,2,3},{4,5,6},{7,8,0}};
        Boards boars = new Boards(feil, 3, 0);
        Boards board = new Boards(bipbop, 3, 2);
        Boards board3 = new Boards(bipbop2, n, 3);
        PriorityQueue<Boards> queue = new PriorityQueue<Boards>(new The_Comparator());
        queue.add(board3);
        queue.add(board);
        queue.add(boars);

        for(int i = 0;i<3;i++){
          System.out.println(queue.poll().get__steps());
         
        }
*/
    }
    public static void writeToFile(Boards board,int[][] startState,String filename) {
      String path = board.getPath();


      try {
        FileWriter myWriter = new FileWriter(filename);       
        for (int[] js : startState) {
          for (int numb : js) {
            myWriter.write(numb + " ");
          }
          myWriter.write("\n");
        }
        myWriter.write("Solution: "+ path.split(" ").length + ", "+ path.replaceAll(" ", ""));    
        myWriter.write("\nStates seen: " + states);
        myWriter.close();
      } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
  }

      
    public static int findInversions(int[][] tabell) {
      int inversions = 0;
      for (int i = 0; i < tabell.length; i++) {
        for (int j = 0; j < tabell.length; j++) {
          for (int i2 = 0; i2 < tabell.length; i2++) {
            for (int j2 = 0; j2 < tabell.length; j2++) {
              if(tabell[i][j]>tabell[i2][j2] && tabell[i][j]!= 0 &&tabell[i2][j2]!=0){
                
                if(i<i2){
                  inversions++;
                }
                else if(i==i2 && j2>j){
                  inversions++;
                }
              }
            }
          }
          
        }
        
      }


      return inversions;

  }
      
    
    public static int[][] readFromFile(String filename){
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            n = Integer.parseInt(myReader.nextLine());
            int board[][] = new int[n][n];
            int count_rows =0;
            while (myReader.hasNextLine()) {
              String data = myReader.nextLine();
              String[] numbers= data.split(" ");
              int count =0;
              for(String numb:numbers){
                board[count_rows][count] = Integer.parseInt(numb);
                count++;
              }
              count_rows++;
            }
            myReader.close();
            return board;
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
          int[][] board = new int[234][232];

          return board;



    }
    public static Boards solve(Boards board){
      //en prioritetskoe som sorterer etter heurestikk + antall steg
      PriorityQueue<Boards> queue = new PriorityQueue<Boards>(new The_Comparator());
      board.setParent(null);
      queue.add(board);
    
      
      while(queue.size()!=0){
    
        
      
        int[][] up = new int[n][n];
        int[][] down = new int[n][n];
        int[][] left = new int[n][n];
        int[][] right = new int[n][n];
        Boards boardToMod = queue.peek();
        queue.remove();
        //maa lage nye lister fordi jeg ikke oensker at jeg bare skal endre paa den ene listen, men heller nye.
        for(int i =0 ; i< n;i++){
          for(int j=0 ; j< n;j++){
            up[i][j]=boardToMod.get_board()[i][j];
            down[i][j]=boardToMod.get_board()[i][j];
            left[i][j]=boardToMod.get_board()[i][j];
            right[i][j]=boardToMod.get_board()[i][j];
          }
        }
        
            
        
       // System.out.println(queue.size());
        //metoder som tar seg av flyttingen av brikkene. hvis et flytt ikke er mulig vil den returenere et array med bare 0
        int[][] rightBoard = moveRight(right);
       
        int[][] leftBoard = moveLeft(left);
       
        int[][] upBoard = moveUp(up);
        
        int[][] downBoard = moveDown(down);
        
        
       
        /*for(int[] i: boardToMod.get_board()){
          System.out.println(i.);
          /*for(int tall:i){
            System.out.print(tall+" ");
          }
        }*/
       
        boolean grandparent_exist= true;
        if(boardToMod.getParent()==null){
       
          grandparent_exist = false;
        }
        else if(boardToMod.getParent().getParent()==null){
          grandparent_exist = false;

        } 
        int ant = 0;
        if((rightBoard[0][0]!= 0 || rightBoard[1][0]!=0)){
          
          if(grandparent_exist==false ){
            ant++;
            
            
            int v = boardToMod.get__steps()+1;
            Boards rightShift = new Boards(rightBoard,n,v);
            String path = boardToMod.getPath() + "R ";
            rightShift.path(path);
            rightShift.setParent(boardToMod);
            queue.add(rightShift);
            
          }
          else if(isTheSame(boardToMod.getParent().getParent().get_board(),rightBoard)){
            ant++;
            int v = boardToMod.get__steps()+1;
            
            String path = boardToMod.getPath() + "R ";
            Boards rightShift = new Boards(rightBoard,n,v);
            rightShift.path(path);
            rightShift.setParent(boardToMod);
            queue.add(rightShift);
            
          }
        }
        
        if(leftBoard[0][0]!= 0 || leftBoard[1][0]!=0){
          
          if(grandparent_exist==false ){
            ant++;
            int v = boardToMod.get__steps()+1;
            
            Boards leftShift = new Boards(leftBoard,n,v);
            String path = boardToMod.getPath() + "L ";
            leftShift.path(path);
            leftShift.setParent(boardToMod);
            queue.add(leftShift);
          
          }
          else if(isTheSame(boardToMod.getParent().getParent().get_board(),leftBoard)){
            ant++;
            int v = boardToMod.get__steps()+1;
           
            
            Boards leftShift = new Boards(leftBoard,n,v);
            String path = boardToMod.getPath() + "L ";
            leftShift.path(path);
            leftShift.setParent(boardToMod);
            
            queue.add(leftShift);
            
          }
        }
        
        if(downBoard[0][0]!= 0 || downBoard[1][0]!=0){
          if(grandparent_exist==false){
            ant++;
            int v = boardToMod.get__steps()+1;
            
            Boards downShift = new Boards(downBoard,n,v);
            String path = boardToMod.getPath() + "D ";
            downShift.path(path);
            downShift.setParent(boardToMod);
            queue.add(downShift);
         
          }
          else if(isTheSame(boardToMod.getParent().getParent().get_board(),downBoard)){
            ant++;
            int v = boardToMod.get__steps()+1;
            Boards downShift = new Boards(downBoard,n,v);
            String path = boardToMod.getPath() + "D ";
            downShift.path(path);
            downShift.setParent(boardToMod);
            queue.add(downShift);
           
          }
          if(queue.peek().get__herustics()==0){
            System.out.println("steps it took: " + queue.peek().get__steps());
            return queue.peek();
          }
          states += ant;
        }
        if(queue.peek().get__herustics()==0){
          return queue.peek();
        }
        if((upBoard[0][0]!= 0 || upBoard[1][0]!=0)){
          
          if(grandparent_exist==false){
            int v = boardToMod.get__steps()+1;
            states++;
            
            Boards upShift = new Boards(upBoard, n, v);
            String path = boardToMod.getPath() + "U ";
            upShift.path(path);
            upShift.setParent(boardToMod);
            queue.add(upShift);
           
          }
          else if(isTheSame(boardToMod.getParent().getParent().get_board(),upBoard)){
            states++;
            int v = boardToMod.get__steps();
            Boards upShift = new Boards(upBoard, n, v);
            String path = boardToMod.getPath() + "U ";
            upShift.path(path);
            upShift.setParent(boardToMod);
            queue.add(upShift);
         
          }
        }
        if(queue.peek().get__herustics()==0){
          System.out.println("steps it took: " + queue.peek().get__steps());
          return queue.peek();
        }
        
      }
      return board;
    }
    public static int[][] moveRight(int[][] nested){
   
      int [][] feil = new int[][] {{0,0,0},{0,0,0},{0,0,0}};
      for(int i = 0; i<n; i++){
        if(nested[i][n-1] == 0){
          return feil;
        }
      }
      for(int i = 0; i<n;i++){
        for(int j = 0; j<n;j++){
          if(nested[i][j] == 0){
            
            int[][] greie  =  move(i,j,i,j+1,nested);
            
            return greie;
          }
        }
      }
      return feil;
    }    
    public static int[][] moveLeft(int[][] nested) {
      int [][] feil = new int[][] {{0,0,0},{0,0,0},{0,0,0}};
      for(int i = 0; i<n; i++){
        if(nested[i][0] == 0){
          return feil;
        }
      }
      for(int i = 0; i<n;i++){
        for(int j = 0; j<n;j++){
          if(nested[i][j] == 0){
            return move(i,j,i,j-1,nested);
          }
        }
      }
      return feil;
    }
    public static int[][] moveUp(int[][] nested){
      int [][] feil = new int[][] {{0,0,0},{0,0,0},{0,0,0}};
      for(int i = 0; i<n; i++){
        if(nested[0][i] == 0){
          return feil;
        }
      }
      for(int i = 0; i<n;i++){
        for(int j = 0; j<n;j++){
          if(nested[i][j] == 0){
            return move(i-1,j,i,j,nested);
          }
        }
      }
      return feil;

    }
    public static int[][] moveDown(int[][] nested) {
      int [][] feil = new int[][] {{0,0,0},{0,0,0},{0,0,0}};
      for(int i = 0; i<n; i++){
        if(nested[n-1][i] == 0){
          return feil;
        }
      }
      for(int i = 0; i<n;i++){
        for(int j = 0; j<n;j++){
          if(nested[i][j] == 0){
            


           
            return move(i+1,j,i,j,nested);
          }
        }
      }
      return feil;
      
    }
    public static int[][] move(int index01,int index02,int indexRest1,int indexRest2,int[][] board) {
     
      int a = board[index01][index02];
      int b = board[indexRest1][indexRest2];
      board[index01][index02] = b;
      board[indexRest1][indexRest2] = a;
     
      
      return board;
  }
    public static boolean isTheSame(int[][] board1, int[][] board2){
    for(int i = 0;  i < n; i++){
      for(int j = 0;  j < n; j++){
        if(board1[i][j] != board2[i][j]){
          return true;
        }
      }
    }
    return false;

  }

}
class The_Comparator implements Comparator<Boards> { 
  public int compare(Boards board1, Boards boards2) 
  { 
      return boards2.compareTo(board1); 
  } 
} 