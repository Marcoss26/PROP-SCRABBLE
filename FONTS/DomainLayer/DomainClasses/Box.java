package DomainLayer.DomainClasses;
import java.util.*;

/**
 * Box.java
 * @author Alvaro Perez
 * Clase Box que representa una casilla del juego
 * Una casilla se constituye por sus coordenadas x e y, la letra y puntuacion de la misma
 */

enum Color {
    //Color end string, color reset
    RESET("\033[0m"),

    // Regular Colors. Normal color, no bold, background color etc.
    BLACK("\033[0;30m"),    // BLACK
    RED("\033[0;31m"),      // RED
    GREEN("\033[0;32m"),    // GREEN
    YELLOW("\033[0;33m"),   // YELLOW
    BLUE("\033[0;34m"),     // BLUE
    MAGENTA("\033[0;35m"),  // MAGENTA
    CYAN("\033[0;36m"),     // CYAN
    WHITE("\033[0;37m");    // WHITE

    private final String code;

    Color(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}

class RunApp {
    public static void main(String[] args) {
        System.out.print(Color.RESET);

        System.out.print(Color.YELLOW);
        System.out.println("YELLOW");
        System.out.print(Color.RESET);
    }
}

public class Box
{
    protected int column;
    protected int row;
    private String symbol;
    private int value;
    private Set<String> HorizontalcrossCheck = new HashSet<>(); 
    private Set<String> VerticalcrossCheck = new HashSet<>(); 
    
    /**
     * Constructor de la clase Box
     * Pre: no hay ninguna casilla creada
     * @param row
     * @param column
     * Post: se crea una casilla con coordenadas row y column y sin letra ni puntuacion
     * @throws IllegalArgumentException si las coordenadas son negativas o mayores que el tamaño del tablero
     */

    public Box(int row, int column)
    {
        if (column < 0 || row < 0) {
            throw new IllegalArgumentException("Las coordenadas no pueden ser negativas");
        }
        this.column = column;
        this.row = row;
        this.symbol = null;
        this.value = 0;
    }
    
    /**
     * Consultora de la fila
     * Pre: ya existe una casilla
     * Post: se retorna la fila de la casilla
     * @return La fila de la casilla
     */
    
    public Set<String> getCrossCheck(int horizontal)
    {
        if (horizontal == 1) {
            return HorizontalcrossCheck;
        }
        return VerticalcrossCheck;
    }

    /**
     * Modificadora de las letras que pueden formar palabras en la casilla
     * Pre: ya existe una casilla
     * @param crossCheck Las letras que pueden formar palabras en la casilla
     * Post: se asignan las letras que pueden formar palabras en la casilla
     */

    public void setCrossCheck(Set<String> crossCheck)   //Esto es al principio cuando el tablero es vacio, todas las casillas tienen las mismas crosschecks
    {
        this.HorizontalcrossCheck = new HashSet<>(crossCheck);
        this.VerticalcrossCheck = new HashSet<>(crossCheck);
    }

    /**
     * Modificadora de las letras que pueden formar palabras en la casilla
     * Pre: ya existe una casilla
     * @param HorizontalcrossCheck Las letras que pueden formar palabras en la casilla horizontal
     * @param VerticalcrossCheck Las letras que pueden formar palabras en la casilla vertical
     * Post: se asignan las letras que pueden formar palabras en la casilla horizontal y vertical
     */

    public void setCrossCheck(Set<String> HorizontalcrossCheck, Set<String> VerticalcrossCheck) //Esto es para sacar el crosscheck del json
    {
        this.HorizontalcrossCheck = new HashSet<>(HorizontalcrossCheck);
        this.VerticalcrossCheck = new HashSet<>(VerticalcrossCheck);
    }
    
    /**
     * Modificadora de las letras que pueden formar palabras en la casilla
     * Pre: ya existe una casilla
     * @param letter La letra que se quiere eliminar del crossCheck
     * @param horizontal 1 si es horizontal, 0 si es vertical
     * Post: se elimina la letra del crossCheck de la casilla
     */

    public void removeFromCrossCheck(String letter, int horizontal)
    {
        //System.out.println("Removing " + letter + " from " + where + "crossCheck at position " + this.column + ", " + this.row);
        if(horizontal == 1) {
            this.HorizontalcrossCheck.remove(letter);
        }
        else {
            this.VerticalcrossCheck.remove(letter);
        }
    }

    /**
     * Consultora de si la casilla tiene una letra que puede formar palabras
     * Pre: ya existe una casilla
     * Post: se retorna true si la casilla tiene una letra que puede formar palabras, false en caso contrario
     * @param letter La letra que se quiere comprobar
     * @return true si la casilla tiene una letra que puede formar palabras, false en caso contrario
     */

    public boolean hasCrossCheck(String letter, int horizontal)
    {
        /*System.out.println("letter: " + letter);
        System.out.println("Horizontal crossCheck:");
        for (String s : this.HorizontalcrossCheck) {
            System.out.println(s);
        }
        System.out.println("Vertical crossCheck:");
        for (String s : this.VerticalcrossCheck) {
            System.out.println(s);
        }*/
        if(horizontal == 0) {
            return this.HorizontalcrossCheck.contains(letter);
        }
        return this.VerticalcrossCheck.contains(letter);
    }

    /**
     * Consultora de la columna de la casilla
     * Pre: ya existe una casilla
     * Post: se retorna la coordenada x de la casilla
     * @return La coordenada correspondiente a la columna de la casilla
     */

    public int getColumn()
    {
        return column;
    }

    /**
     * Consultora de la fila de la casilla
     * Pre: ya existe una casilla
     * Post: se retorna la coordenada y de la casilla
     * @return La coordenada correspondiente a la fila de la casilla
     */

    public int getRow()
    {
        return row;
    }

    /**
     * Modificadora de la letra y el valor
     * Pre: ya existe una casilla
     * @param lletra La letra a colocar
     * @param valor El valor de la letra
     * Post: se asigna la letra y el valor a la casilla
     * @throws IllegalArgumentException si la letra es null/no es del abecedario de ese idioma o el valor es negativo/no
     * es ninguno de los valores posibles
     */

    public void setLetter(String lletra, int valor)
    {
        this.symbol = lletra;
        this.value = valor;
    }

    /**
     * Consultora de la letra
     * Pre: ya existe una casilla
     * Post: se retorna la letra de la casilla
     * @return La letra de la casilla
     */

    public String getSymbol()
    {
        return symbol;
    }

    /**
     * Consultora de la puntuacion
     * Pre: ya existe una casilla
     * Post: se retorna la puntuacion de la casilla
     * @return La puntuacion de la casilla
     */

    public int getValue()
    {
        return value;
    }

    /**
     * Consultora de Box
     * Pre: ya existe una casilla
     * @param row
     * @param column
     * Post: se retorna la coordenada row y column de la casilla
     * @return La casilla correspondiente a row y column
     */

    public Box getBox(int row, int column)
    {
        if (this.column == column && this.row == row) {
            return this;
        }
        return null;
    }

    /**
     * Consultora de si la casilla esta vacia
     * Pre: ya existe una casilla
     * Post: se retorna true si la casilla esta vacia, false en caso contrario
     * @return true si la casilla esta vacia, false en caso contrario
     */

    public boolean isEmpty()
    {
        return this.symbol == null;
    }

    /**
     * Consultora de 1 casilla
     * Pre: ya existe una casilla
     * Post: se retorna la casilla con el sistema de () y colores
     * @return La casilla con el sistema de () y colores
     */

    @Override
    public String toString() {
        if (getSymbol() != null) {
            if (getSymbol().length() == 3) {
                return Color.GREEN + "(" + getSymbol() + getValue() + ")" + Color.RESET;
            }
            else if (getSymbol().length() == 2) {
                return Color.GREEN + "(" + getSymbol() + "_" + getValue() + ")" + Color.RESET;
            }
            return Color.GREEN + "(" + getSymbol() + "__" + getValue() + ")" + Color.RESET;
        }
        return Color.GREEN + "(____)" + Color.RESET;
    }

    /**
     * Subclase DoubleLetter,
     * encargada de la gestion de las casillas de doble letra
     */

    public static class DoubleLetter extends Box
    {
        /**
         * Constructor de la clase DoubleLetter
         * Pre: no hay ninguna casilla creada
         * @param row
         * @param column
         * Post: se crea una casilla double letter con coordenadas row y column
         * @throws IllegalArgumentException si las coordenadas son negativas o mayores que el tamaño del tablero
         */

        public DoubleLetter(int row, int column)
        {
            super(row, column);
        }

        /**
         * Consultora de 1 casilla double letter
         * Pre: ya existe una casilla
         * Post: se retorna la casilla con el sistema de () y colores
         * @return La casilla con el sistema de () y colores
         */

        @Override
        public String toString() {
            if (getSymbol() != null) {
                if (getSymbol().length() == 3) {
                    return Color.CYAN + "(" + getSymbol() + getValue() + ")" + Color.RESET;
                }
                else if (getSymbol().length() == 2) {
                    return Color.CYAN + "(" + getSymbol() + "_" + getValue() + ")" + Color.RESET;
                }
                return Color.CYAN + "(" + getSymbol() + "__" + getValue() + ")" + Color.RESET;
            }
            return Color.CYAN + "(____)" + Color.RESET;
        }
    }

    /**
     * Subclase TripleLetter,
     * encargada de la gestion de las casillas de triple letra
     */

    public static class TripleLetter extends Box
    {
        /**
         * Constructor de la clase TripleLetter
         * Pre: no hay ninguna casilla creada
         * @param row
         * @param column
         * Post: se crea una casilla triple letter con coordenadas row y column
         * @throws IllegalArgumentException si las coordenadas son negativas o mayores que el tamaño del tablero
         */

        public TripleLetter(int row, int column)
        {
            super(row, column);
        }

        /**
         * Consultora de 1 casilla triple letter
         * Pre: ya existe una casilla
         * Post: se retorna la casilla con el sistema de () y colores
         * @return La casilla con el sistema de () y colores
         */

        @Override
        public String toString() {
            if (getSymbol() != null) {
                if (getSymbol().length() == 3) {
                    return Color.BLUE + "(" + getSymbol() + getValue() + ")" + Color.RESET;
                }
                else if (getSymbol().length() == 2) {
                    return Color.BLUE + "(" + getSymbol() + "_" + getValue() + ")" + Color.RESET;
                }
                return Color.BLUE + "(" + getSymbol() + "__" + getValue() + ")" + Color.RESET;
            }
            return Color.BLUE + "(____)" + Color.RESET;
        }
    }

    /**
     * Subclase DoubleWord,
     * encargada de la gestion de las casillas de doble palabra
     */

    public static class DoubleWord extends Box
    {
        /**
         * Constructor de la clase DoubleWord
         * Pre: no hay ninguna casilla creada
         * @param row
         * @param column
         * Post: se crea una casilla double word con coordenadas row y column
         * @throws IllegalArgumentException si las coordenadas son negativas o mayores que el tamaño del tablero
         */

        public DoubleWord(int row, int column)
        {
            super(row, column);
        }

        /**
         * Consultora de 1 casilla double word
         * Pre: ya existe una casilla
         * Post: se retorna la casilla con el sistema de () y colores
         * @return La casilla con el sistema de () y colores
         */

        @Override
        public String toString() {
            if (getSymbol() != null) {
                if (getSymbol().length() == 3) {
                    return Color.MAGENTA + "(" + getSymbol() + getValue() + ")" + Color.RESET;
                }
                else if (getSymbol().length() == 2) {
                    return Color.MAGENTA + "(" + getSymbol() + "_" + getValue() + ")" + Color.RESET;
                }
                return Color.MAGENTA + "(" + getSymbol() + "__" + getValue() + ")" + Color.RESET;
            }
            return Color.MAGENTA + "(____)" + Color.RESET;
        }
    }

    /**
     * Subclase TripleWord,
     * encargada de la gestion de las casillas de triple palabra
     */

    public static class TripleWord extends Box
    {
        /**
         * Constructor de la clase TripleWord
         * Pre: no hay ninguna casilla creada
         * @param row
         * @param column
         * Post: se crea una casilla triple word con coordenadas row y column
         * @throws IllegalArgumentException si las coordenadas son negativas o mayores que el tamaño del tablero
         */

        public TripleWord(int row, int column)
        {
            super(row, column);
        }

        /**
         * Consultora de 1 casilla triple word
         * Pre: ya existe una casilla
         * Post: se retorna la casilla con el sistema de () y colores
         * @return La casilla con el sistema de () y colores
         */

        @Override
        public String toString() {
            if (getSymbol() != null) {
                if (getSymbol().length() == 3) {
                    return Color.RED + "(" + getSymbol() + getValue() + ")" + Color.RESET;
                }
                else if (getSymbol().length() == 2) {
                    return Color.RED + "(" + getSymbol() + "_" + getValue() + ")" + Color.RESET;
                }
                return Color.RED + "(" + getSymbol() + "__" + getValue() + ")" + Color.RESET;
            }
            return Color.RED + "(____)" + Color.RESET;
        }
    }
}