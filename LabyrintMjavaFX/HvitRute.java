public class HvitRute extends Rute{

protected int xKoord;
protected int yKoord;
    public HvitRute(int xKoord,int yKoord){
        super(xKoord,yKoord);
    }
    @Override
    public String chartilTegn(){
        return ".";
    }
    

}
