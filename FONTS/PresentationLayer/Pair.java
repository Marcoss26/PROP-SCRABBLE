package PresentationLayer;
import java.util.*;

public class Pair {
    private int First;
    private int Second;

    Pair(int first, int second){
        this.First = first;
        this.Second = second;
    }

    public Integer first(){
        return this.First;
    }

    public Integer second(){
        return this.Second;
    }

    public void setFirst(int first){
        this.First = first;
    }

    public void setSecond(int second){
        this.Second = second;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return First == pair.First && Second == pair.Second;
    }

    @Override
    public int hashCode() {
        return Objects.hash(First, Second);
    }
}
