import java.util.*;

public class Letter
{
    //Atributos de una ficha
    private String symbol;
    private String idPlayer;
    private int value;
    

    public Letter(String symbol, int value){
        this.symbol = symbol;
        this.value = value;
    }

    public String getSymbol(){
        return symbol;
    }
    
    public int getValue(){
        return value;
    }

    public String getIdPlayer(){
        return idPlayer;
    }

   

    public void setSymbol(String symbol){
        this.symbol = symbol;
    }

    public void setIdPlayer(String idPlayer){
        this.idPlayer = idPlayer;
    }

    public void setValue(int value){
        this.value = value;
    }

  
    //

    public void displayLetter(){
        System.out.println("[" + symbol + ", " + value + "]");
    }
    public void printLetter(){
        int size = symbol.length();
        String top = "┌" + "─".repeat(size+2) + "┐" + "\n";
        String middle = "│" + ' ' + symbol + ' ' + "│" + "\n";
        String middle2 = "│" + " " + value + " ".repeat(size) +  "│" + "\n";
        String bottom = "└" + "─".repeat(size+2) + "┘" + "\n";
        System.out.println(top + middle + middle2 + bottom); 
    }


    
        
    
}