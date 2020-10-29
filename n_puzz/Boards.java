class Boards implements Comparable<Boards>{
    int steps,herustics,tot,n;
    int[][] board;
    Boards parent;
    String path="";
    int inversions;
   
    static int[][] goalstate;
    public Boards(int[][] board,int n, int steps){
        this.board = board;
        this.n = n;
        this.steps = steps;
        goalstate = new int[n][n];
        int verdi = 1;
        for(int i = 0; i<n; i++){
            for(int j = 0; j<n; j++){
                if(verdi != n*n){
                    goalstate[i][j] = verdi;
                    verdi++;
                }
                else{
                    goalstate[i][j] = 0;
                }

            }
        }
        
        set_Herustics();
        tot = herustics + steps;
  
       
        
        
        
    }
    public void path(String letter){
        path=letter;
    }
    public String getPath(){
        return path;
    }
    
    public int get__steps(){
        return steps;
    }
    public void setGoalstate(int[][] board){
        goalstate = board;
        set_Herustics();
    }
    public int get__herustics(){

        return herustics;
    }
    public int get__tot(){
        return tot;
    }
    public int[][] get_board(){
        return board;
    }
    public void set_Herustics(){
        
        int feilplass = 0;
        for(int i = 0; i<n;i++){
        
            for(int j = 0; j<n;j++){
                 if(board[i][j]!=0 && board[i][j]!= (i*3+j+1)){
                     int wantedi= 0;
                     int wantedj=0;
                     
                     boolean found = false;
                     for(int k = 0; k<n;k++ ){
                         if(found){
                             break;
                         }
                         for(int l = 0; l<n;l++){
                             if(goalstate[k][l]==board[i][j]){
                                 wantedi = k;
                                 wantedj = l;
                                 found=true;
                                 
                                 break;
                             }
                         }
                         
                     }
                     int iDiff = 0;
                     if(wantedi<i){
                         
                         iDiff = i-wantedi;
                     }
                     else{
                         iDiff = wantedi-i;
                     }
                     int jDiff = 0;
                     if(wantedj>j){
                         jDiff= wantedj-j;
                     }
                     else{
                         jDiff=j-wantedj;
                     }
                     
                     feilplass+=iDiff+jDiff;
                 }
            }
        }
      
        herustics = feilplass;
    }
    public void setParent(Boards board){
        parent = board;
    }
    public Boards getParent(){
        return parent;
    }
    public int getInversions(){
        return inversions;
    }
    @Override
    public int compareTo(Boards board) {
        return ((this.get__tot()-board.get__tot())*-1);
    }



    












    

}