package ch.oezcy.superfan;

import java.util.ArrayList;

public class TableSelection<E> {
    private ArrayList<E> selections = new ArrayList<>();

    public TableSelection<E> putElement(E element){
        //remove first Element if selection has 2 already
        if(selections.size() >= 2){
            selections.remove(0);
        }
        selections.add(element);
        return this;
    }

    public TableSelection<E> removeElement(){
        // remove last element
        if(selections.size() > 0){
            selections.remove(selections.size() - 1);
        }
        return this;
    }

    public TableSelection<E> removeElement(int index){
        if(selections.size() > 0){
            selections.remove(index);
        }
        return this;
    }
    public TableSelection<E> removeElement(E element){
        if(selections.size() > 0){
            selections.remove(selections.indexOf(element));
        }
        return this;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        for(E element : selections){
            str.append(element.toString() + " ");
        }

        return str.toString();
    }

    public int size(){
        return selections.size();
    }
}
