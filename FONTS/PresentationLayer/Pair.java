package PresentationLayer;
import java.util.*;

public class Pair<F,S> {
    private F First;
    private S Second;

    Pair(F first, S second){
        this.First = first;
        this.Second = second;
    }

    public F first(){
        return this.First;
    }

    public S second(){
        return this.Second;
    }

    public void setFirst(F first){
        this.First = first;
    }

    public void setSecond(S second){
        this.Second = second;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?,?> pair = (Pair<?,?>) o;
        return First == pair.First && Second == pair.Second;
    }

    @Override
    public int hashCode() {
        return Objects.hash(First, Second);
    }
}
