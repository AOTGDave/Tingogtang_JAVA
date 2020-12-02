import java.util.Iterator;
class Lenkeliste<T> implements Liste<T>{
  protected Node forste;
  protected int storrelse;
  protected Node siste;

@Override
public void leggTil(T t){

  Node ny = new Node(t);
    storrelse++;

    if (forste == null){
        forste = ny;
        siste = ny;

        return;
    }
    else{
      siste.neste=ny;
      ny.forrige=siste;
      siste=ny;

    }
    }

@Override
public void leggTil(int pos, T t){
    Node ny = new Node(t);
  
    if(pos==0 && forste==null){
      siste = ny;
      forste = ny;
      storrelse++;
    }
    else if(pos==0 && forste!=null){
      ny.neste = forste;
      forste.forrige = ny;
      forste=ny;
      storrelse++;
    }
    else if(pos==storrelse){
      leggTil(t);
    }
    else{
      Node denne=forste;
      for(int i = 0; i<pos; i++){
        denne = denne.neste;
      }
      ny.neste = denne;
      ny.forrige = denne.forrige;
      denne.forrige.neste=ny;
      denne.forrige = ny;
      storrelse++;
    }
  }


@Override
public T hent(int pos){
  Node ditta = forste;
  int counter = 0;

  while(ditta !=null && counter < pos){
    ditta = ditta.neste;
    counter++;
  }
  return ditta.innhold;


}

@Override
public int stoerrelse(){
  return storrelse;
}

@Override
public T fjern(){
   

       if (storrelse == 1){
         storrelse--;
         return forste.innhold;
       }

       else {
           Node denne = forste;
           forste = denne.neste;
           storrelse--;
           return denne.innhold;
       }
   }
  

@Override
public T fjern(int pos){


  if(pos==0){
    return fjern();
  }

  else if (storrelse-1 == pos){
    Node returvare = siste;
    siste = siste.forrige;
    siste.neste = null;
    storrelse--;
    return returvare.innhold;
  }
  else{
    Node denne = forste;
    for(int i = 0; i<pos; i++){
      denne = denne.neste;
    }
    Node returvare = denne;
    denne.neste.forrige = denne.forrige;
    denne.forrige.neste = denne.neste;
    storrelse--;
    return returvare.innhold;

  }
}

@Override
public void sett(int pos, T x){
  Node ny = new Node(x);
 
  if (storrelse == 0 && pos == 0){
    forste = ny;
    siste = ny;
  }
  else if (pos == 0){
    Node denne = forste.neste;

    ny.neste = denne;
    forste = ny;
  }
  else if(pos == storrelse-1){
    Node denne = siste.forrige;
    denne.neste = ny;
    siste = ny;
  }
  else{
    Node denne = forste;
    for(int i = 0; i < pos; i++){
      denne = denne.neste;
    }
    denne.neste.forrige = ny;
    ny.forrige = denne.forrige;
    ny.neste = denne.neste;
    denne.forrige.neste = ny;
    denne = ny;


  }
}


























































public Iterator<T> iterator(){
      return new LenkelisteIterator();
  }


private class LenkelisteIterator implements Iterator<T>{

    private Node denne;

    public LenkelisteIterator(){
        denne = forste;
    }

    @Override
    public boolean hasNext(){
        if (denne != null){
            return true;
        }
          return false;
        }

    @Override
    public T next(){

        T data = denne.innhold;
        denne = denne.neste;
        return data;
    }
    @Override
    public void remove(){
      return;
    }
  }




protected class Node{
    protected Node neste;
    protected T innhold;
    protected Node forrige;

    public Node(T t){
        innhold = t;
    }

  }

}
