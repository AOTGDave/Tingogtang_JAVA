class SortRute extends Rute{


    public SortRute(int xKoord,int yKoord){
        super(xKoord,yKoord);
    }
    @Override
    public String chartilTegn(){
        return "#";
    }
    @Override
    public Boolean utvei(){
        return false;
    }
                            
}