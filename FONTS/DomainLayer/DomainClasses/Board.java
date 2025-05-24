package DomainLayer.DomainClasses;
import java.util.*;

/**
 * Board.java
 * @author Alvaro Perez 
 * Esta clase se encarga de la configuracion y manipulacion del tablero,
 * constituido por una matriz de casillas (Box)
 */

public class Board
{
    private Box[][] board;
    private int size;
    private boolean isEmpty = true;

    /**
     * Constructor de la clase Board.
     * Inicializa el tablero con el tamaño especificado y asigna las casillas especiales
     * Pre: true
     * @param size El tamaño del tablero (7, 15 o 25).
     * Post: se ha creado un tablero con las dimensiones de size x size
     * @throws IllegalArgumentException si el tamaño no es 7, 15 o 25
     */

    public Board(int size)
    {
        if (size != 7 && size != 15 && size != 25) {
            throw new IllegalArgumentException("El tamaño del tablero debe ser 7, 15 o 25.");  
        }
        this.size = size;

        this.board = new Box[size + 1][size + 1];
        int[] doubleLetter = new int[0];
        int[] tripleLetter = new int[0];
        int[] doubleWord = new int[0];
        int[] tripleWord = new int[0];

        if (this.size == 7) {
            doubleLetter = new int[]{0,1, 0,5, 1,0, 1,6, 2,3, 3,2, 3,4, 4,3, 5,0, 5,6, 6,1, 6,5};
            tripleLetter = new int[]{1,3, 3,1, 3,5, 5,3};
            doubleWord = new int[]{1,1, 2,2, 3,3, 4,4, 5,5, 1,5, 2,4, 4,2, 5,1};
            tripleWord = new int[]{0,0, 0,3, 0,6, 3,0, 3,6, 6,0, 6,3, 6,6};
        }
        else if (this.size == 15) {
            doubleLetter = new int[]{0,3, 0,11, 2,6, 2,8, 3,0, 3,7, 3,14, 6,2, 6,6, 6,8, 6,12, 7,3, 7,11, 14,3, 14,11, 12,6, 12,8, 11,0, 11,7, 11,14, 8,2, 8,6, 8,8, 8,12};
            tripleLetter = new int[]{1,5, 1,9, 5,1, 5,5, 5,9, 5,13, 13,5, 13,9, 9,1, 9,5, 9,9, 9,13};
            doubleWord = new int[]{1,1, 2,2, 3,3, 4,4, 7,7, 10,10, 11,11, 12,12, 13,13, 1,13, 2,12, 3,11, 4,10, 10,4, 11,3, 12,2, 13,1};
            tripleWord = new int[]{0,0, 0,7, 0,14, 7,0, 7,14, 14,0, 14,7, 14,14};
        }
        else if (this.size == 25) {
            doubleLetter = new int[]{1,8, 1,16, 3,11, 3,13, 4,12, 5,8, 5,16, 7,11, 7,13, 8,12, 8,1, 8,5, 8,19, 8,23, 11,3, 11,7, 11,11, 11,13, 11,17, 11,21, 12,4, 12,8, 12,16, 12,20, 23,8, 23,16, 21,11, 21,13, 20,12, 19,8, 19,16, 17,11, 17,13, 16,12, 16,1, 16,5, 16,19, 16,23, 13,3, 13,7, 13,11, 13,13, 13,17, 13,21};
            tripleLetter = new int[]{2,10, 2,14, 6,10, 6,14, 10,10, 10,14, 14,10, 14,14, 18,10, 18,14, 22,10, 22,14, 10,2, 10,6, 14,2, 14,6, 10,18, 10,22, 14,18, 14,22};
            doubleWord = new int[]{1,1, 2,2, 3,3, 4,4, 5,5, 6,6, 7,7, 8,8, 9,9, 12,12, 15,15, 16,16, 17,17, 18,18, 19,19, 20,20, 21,21, 22,22, 23,23, 1,23, 2,22, 3,21, 4,20, 5,19, 6,18, 7,17, 8,16, 9,15, 15,9, 16,8, 17,7, 18,6, 19,5, 20,4, 21,3, 22,2, 23,1};
            tripleWord = new int[]{0,0, 0,12, 0,24, 12,0, 12,24, 24,0, 24,12, 24,24};
        }
        
        assignSpecialBoxes(doubleLetter, "doubleLetter");
        assignSpecialBoxes(tripleLetter, "tripleLetter");
        assignSpecialBoxes(doubleWord, "doubleWord");
        assignSpecialBoxes(tripleWord, "tripleWord");

        for (int i = 0; i <= this.size; i++) {
            for (int j = 0; j <= this.size; j++) {
                if (this.board[i][j] == null) this.board[i][j] = new Box(i, j);
            }
        }
    }

    /**
     * Consultora del parametro size
     * Pre: true
     * Post: se devuelve el parametro size
     * @return size
     */

    public int getSize() {
        return this.size;
    }

    /**
     * Imprime los crosschecks del tablero
     * Pre: ya existe un tablero
     * Post: se imprimen los crosschecks de las casillas ancla del tablero
     */

    public void printCrossChecks()
    {
        for (int row = 0; row < this.size; row++) 
        {
            for (int column = 0; column < this.size; column++) 
            {
                if(isAnchor(row,column))
                {   
                    System.out.println("CrossCheck for: " + "[" + column + "," + row + "]");
                    if(this.board[row][column].getCrossCheck(1).size() > 0)
                    {
                        System.out.println("Horizontal CrossCheck: ");
                        System.out.print(this.board[row][column].getCrossCheck(0) + "\n");
                    }
                    if(this.board[row][column].getCrossCheck(0).size() > 0)
                    {
                        System.out.println("Vertical CrossCheck: ");
                        System.out.print(this.board[row][column].getCrossCheck(1) + "\n");
                    }
                }
            }
        }
    }

    /**
     * Computa los crosschecks del tablero
     * Pre: ya existe un tablero y un conjunto de caracteres
     * Post: se actualizan los crosschecks de las casillas ancla del tablero
     * @param characters El conjunto de caracteres a considerar para los crosschecks
     * @param dawg El diccionario para verificar la existencia de palabras
     */

    public void computeCrossChecks(Set<String> characters, Dawg dawg)
    {
        for (int row = 0; row < this.size; row++) 
        {
            for (int column = 0; column < this.size; column++) 
            {
                if(isEmpty) this.board[row][column].setCrossCheck(characters);
                else if (isAnchor(row,column))
                {
                    System.out.println("Computing new crosschecks for: " + "[" + column + "," + row + "]");
                    String upWord = "";
                    String downWord = "";
                    String leftWord = "";
                    String rightWord = "";
                    for(int i = row - 1; i >= 0 && !board[i][column].isEmpty(); i--)
                    {
                        upWord = board[i][column].getSymbol() + upWord;
                    }
                    for(int i = row + 1; i < size && !board[i][column].isEmpty(); i++)
                    {
                        downWord += board[i][column].getSymbol();
                    }
                    for(int i = column - 1; i >= 0 && !board[row][i].isEmpty(); i--)
                    {
                        leftWord = board[row][i].getSymbol() + leftWord;
                    }
                    for(int i = column + 1; i < size && !board[row][i].isEmpty(); i++)
                    {
                        rightWord += board[row][i].getSymbol();
                    }
                    for(String character: characters)
                    {
                        String horizontalWord = leftWord + character + rightWord;
                        String verticalWord = upWord + character + downWord;
                        /*System.out.println("Possible horizontal word: " + horizontalWord + " Possible vertical word: " + verticalWord);
                        if(!dawg.existsWord(verticalWord))
                        {
                            System.out.println("The word " + verticalWord + " is not in the dictionary");
                        }
                        if(!dawg.existsWord(horizontalWord))
                        {
                            System.out.println("The word " + horizontalWord + " is not in the dictionary");
                        }*/
                        if(!verticalWord.equals(character) && !dawg.existsWord(verticalWord))
                        {
                            this.board[row][column].removeFromCrossCheck(character,0);  
                            //continue;
                        }
                        if(!horizontalWord.equals(character) && !dawg.existsWord(horizontalWord))
                        {
                            this.board[row][column].removeFromCrossCheck(character,1);
                            //continue;
                        }
                    }   
                }
            }
        }
    }

    /**
     * Verifica si una casilla está vacía
     * Pre: ya existe un tablero
     * @param row La fila de la casilla
     * @param column La columna de la casilla
     * @return true si la casilla está vacía, false en caso contrario
     */

    public boolean isEmpty(int row, int column) {
        return this.board[row][column].isEmpty();
    }

    /**
     * Verifica si una casilla es una casilla ancla
     * Pre: ya existe un tablero
     * @param row La fila de la casilla
     * @param column La columna de la casilla
     * @return true si la casilla es una casilla ancla, false en caso contrario
     */

    public boolean isAnchor(int row, int column) {
        if(this.board[row][column].isEmpty())
        {
            if (row > 0 && !board[row - 1][column].isEmpty()) return true;
            if (row < size - 1 && !board[row + 1][column].isEmpty()) return true;
            if (column > 0 && !board[row][column - 1].isEmpty()) return true;
            if (column < size - 1 && !board[row][column + 1].isEmpty()) return true;
        }
        return false;
    }
    
    /**
     * Consultora del tablero
     * Pre: true
     * Post: se devuelve el tablero
     * @return board
     */

    public Box[][] getBoard() {
        return this.board;
    }

    /**
     * Modificadora del tablero
     * Pre: ya existe un tablero
     * @param board El nuevo tablero
     * Post: se modifica el tablero
     */

    public void setBoard(Box[][] board) {
        this.board = board;
    }

    /**
     * Modificadora del parametro size
     * Pre: ya existe un size
     * @param size El nuevo tamaño del tablero
     * Post: se modifica el tamaño del tablero
     */

    public void setSize(int size) {
        if (size != 7 && size != 15 && size != 25) {
            throw new IllegalArgumentException("El tamaño del tablero debe ser 7, 15 o 25.");  
        }
        this.size = size;
    }

    /**
     * Verifica si una posición del tablero tiene una ficha
     * Pre: ya existe un tablero
     * @param row
     * @param column
     * @return true si la casilla tiene una ficha, false en caso contrario
    */

    public boolean hasLetter(int row, int column) {
        if (row >= 0 && row < size && column >= 0 && column < size) {
            return this.board[row][column].getSymbol() != null;
        } else {
            throw new IllegalArgumentException("Coordenadas fuera de los límites del tablero.");
        }
    }

    /**
     * Asigna las casillas especiales al tablero
     * Pre: ya existe un tablero
     * @param posicions Las posiciones de las casillas especiales encapsuladas en un vector
     * @param type El tipo de casilla especial (doubleLetter, tripleLetter, doubleWord, tripleWord)
     * Post: se asignan las casillas especiales al tablero
     */

    private void assignSpecialBoxes(int[] posicions, String type) {
        for (int i = 0; i < posicions.length; i += 2) {
            int row = posicions[i+1];
            int column = posicions[i];
            switch (type) {
                case "doubleLetter":
                    this.board[row][column] = new Box.DoubleLetter(row, column);
                    break;
                case "tripleLetter":
                    this.board[row][column] = new Box.TripleLetter(row, column);
                    break;
                case "doubleWord":
                    this.board[row][column] = new Box.DoubleWord(row, column);
                    break;
                case "tripleWord":
                    this.board[row][column] = new Box.TripleWord(row, column);
                    break;
            }
        }
    }

    /**
     * Coloca una letra en el tablero
     * Pre: ya existe un tablero
     * @param row La fila de la casilla
     * @param column La columna de la casilla
     * @param letter La letra a colocar
     * @param value El valor de la letra
     * Post: se coloca la letra en la casilla correspondiente
     */

    public void placeLetter(int row, int column, String letter, int value) {
        if (row >= 0 && row < size && column >= 0 && column < size) {
            isEmpty = false;
            this.board[row][column].setLetter(letter, value);
            //printBoard();
        }
    }

    /**
     * Verifica si el tablero está vacío
     * Pre: ya existe un tablero
     * @return true si el tablero está vacío, false en caso contrario
     */
    
    public boolean isEmpty() {
        return isEmpty;
    }

    /**
     * Destructora de casilla
     * Pre: ya existe un tablero
     * @param row
     * @param column
     * Post: se elimina la letra de la casilla correspondiente
     */

    public void removeLetter(int row, int column) {
        if (row >= 0 && row < size && row >= 0 && row < size) {
            this.board[row][column].setLetter(null, 0);
            printBoard();
        }
        else {
            System.out.println("Coordenadas fuera de rango.");
        }   
    }

    /**
     * Imprime el tablero en la consola
     * Pre: ya existe un tablero
     * Post: se imprime el tablero en la consola
     */

    public void printBoard() {
        // Imprimir los índices de las columnas
        System.out.print("     "); // Espacio inicial para alinear con los índices de las filas
        for (int j = 0; j < this.size; j++) {
            if (j < 10) System.out.print(" "); // Alineación para índices de una cifra
            System.out.print(j + "      "); // Imprime el índice de la columna con espacio
        }
        System.out.println(); // Salto de línea después de los índices de las columnas
    
        // Imprimir las filas con sus índices
        for (int i = 0; i < this.size; i++) {
            System.out.print(i + " "); // Imprime el índice de la fila
            if (i < 10) System.out.print(" "); // Alineación para índices de una cifra
            for (int j = 0; j < this.size; j++) {
                System.out.print(this.board[i][j].toString() + "  "); // Imprime la casilla
            }
            System.out.println();
            System.out.println(); // Salto de línea al final de cada fila
        }
    }

    /**
     * Retorna la casilla correspondiente a las coordenadas de entrada
     * Pre: ya existe un tablero
     * @param row La fila de la casilla
     * @param column La columna de la casilla
     * @return La casilla correspondiente a los valores de entrada, null si no existe
     */

    public Box getBox(int row, int column) {
        if (column >= 0 && column <= size && row >= 0 && row <= size) {
            return this.board[row][column];
        } else {
            //throw new IllegalArgumentException("Coordenadas fuera de los límites del tablero.");
            return null;
        }
    }




    /**
     * Retorna la letra de la casilla correspondiente a las coordenadas de entrada
     * Pre: ya existe un tablero con al menos una letra
     * @param row
     * @param column 
     * @return La letra de la casilla correspondiente a los valores de entrada
     */

    public String getLetter(int row, int column) { 
        return board[row][column].getSymbol();
    }
}