package DomainLayer.DomainClasses;
import java.util.*;
import Utils.Pair;


/**
 * Board.java
 * @author: Alvaro Perez 
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
     * Post: se ha creado un tablero con las dimensiones de size x size
     *       y se han asignado las casillas especiales (DoubleLetter, TripleLetter, DoubleWord, TripleWord)
     *       Si el tamaño no es 7, 15 o 25, lanza una IllegalArgumentException
     * @param size el tamaño del tablero (7, 15 o 25)
     * @throws IllegalArgumentException si el tamaño no es 7, 15 o 25
     */

    public Board(int size)
    {
        if (size != 7 && size != 15 && size != 25) {
            throw new IllegalArgumentException("El tamaño del tablero debe ser 7, 15 o 25.");  
        }
        this.size = size;
        this.board = new Box[size + 1][size + 1];

        Set<Pair<Integer,Integer>> doubleLetter = new HashSet<>();
        Set<Pair<Integer,Integer>> tripleLetter = new HashSet<>();
        Set<Pair<Integer,Integer>> doubleWord = new HashSet<>();
        Set<Pair<Integer,Integer>> tripleWord = new HashSet<>();

        if (size == 7) {

            doubleLetter = new HashSet<>(Arrays.asList(new Pair<>(0,1), new Pair<>(0,5), new Pair<>(1,0), new Pair<>(1,6), new Pair<>(2,3), new Pair<>(3,2), new Pair<>(3,4), new Pair<>(4,3), new Pair<>(5,0), new Pair<>(5,6), new Pair<>(6,1), new Pair<>(6,5)));

            tripleLetter = new HashSet<>(Arrays.asList(new Pair<>(1,3), new Pair<>(3,1), new Pair<>(3,5), new Pair<>(5,3)));

            doubleWord = new HashSet<>(Arrays.asList(new Pair<>(1,1), new Pair<>(2,2), new Pair<>(4,4), new Pair<>(5,5), new Pair<>(1,5), new Pair<>(2,4), new Pair<>(4,2), new Pair<>(5,1)));

            tripleWord = new HashSet<>(Arrays.asList(new Pair<>(0,0), new Pair<>(0,3), new Pair<>(0,6), new Pair<>(3,0), new Pair<>(3,6), new Pair<>(6,0), new Pair<>(6,3), new Pair<>(6,6)));



        }

        else if(size == 15) {

            doubleLetter = new HashSet<>(Arrays.asList(new Pair<>(0,3), new Pair<>(0,11), new Pair<>(2,6), new Pair<>(2,8), new Pair<>(3,0), new Pair<>(3,7), new Pair<>(3,14), new Pair<>(6,2), new Pair<>(6,6), new Pair<>(6,8), new Pair<>(6,12), new Pair<>(7,3), new Pair<>(7,11), new Pair<>(14,3), new Pair<>(14,11), new Pair<>(12,6), new Pair<>(12,8), new Pair<>(11,0), new Pair<>(11,7), new Pair<>(11,14), new Pair<>(8,2), new Pair<>(8,6), new Pair<>(8,8), new Pair<>(8,12)));

            tripleLetter = new HashSet<>(Arrays.asList(new Pair<>(1,5) ,new Pair<>(1,9) ,new Pair<>(5,1) ,new Pair<>(5,5) ,new Pair<>(5,9) ,new Pair<>(5,13) ,new Pair<>(13,5) ,new Pair<>(13,9) ,new Pair<>(9,1) ,new Pair<>(9,5) ,new Pair<>(9,9) ,new Pair<>(9,13)));

            doubleWord = new HashSet<>(Arrays.asList(new Pair<> (1,1) ,new Pair<> (2,2) ,new Pair<> (3,3) ,new Pair<> (4,4)  ,new Pair<> (10,10) ,new Pair<> (11,11) ,new Pair<> (12,12) ,new Pair<> (13,13),
                            new Pair<> (1,13) ,new Pair<> (2,12) ,new Pair<> (3,11) ,new Pair<> (4,10) ,
                            new Pair<> (10,4) ,new Pair<> (11,3) ,new Pair<> (12,2),
                            new Pair<> (13,1)));

            tripleWord =  new HashSet<>(Arrays.asList(new Pair<> (0,0),
                            new Pair<> (0,7),
                            new Pair<> (0,14),
                            new Pair<> (7,0),
                            new Pair<> (7,14),
                            new Pair<> (14,0),
                            new Pair<> (14,7),
                            new Pair<> (14,14)));
        }


        else {

            doubleLetter = new HashSet<>(Arrays.asList(new Pair<>(1,8), new Pair<>(1,16), new Pair<>(3,11), new Pair<>(3,13), new Pair<>(4,12), new Pair<>(5,8), new Pair<>(5,16), new Pair<>(7,11), new Pair<>(7,13),
                            new Pair<>(8,12), new Pair<>(8,1), new Pair<>(8,5), new Pair<>(8,19), new Pair<>(8,23), new Pair<>(11,3),
                            new Pair<>(11,7), new Pair<>(11,11), new Pair<>(11,13), new Pair<>(11,17), new Pair<>(11,21), new Pair<>(12,4), new Pair<>(12,8), new Pair<>(12,16), new Pair<>(12,20),
                            new Pair<> (23,8) ,new Pair<> (23,16) ,new Pair<> (21,11) ,new Pair<> (21,13) ,new Pair<> (20,12) ,new Pair<> (19,8) ,new Pair<> (19,16) ,new Pair<> (17,11) ,new Pair<> (17,13) ,
                            new Pair<> (16,12) ,new Pair<> (16,1) ,new Pair<> (16,5) ,new Pair<> (16,19) ,new Pair<> (16,23) ,
                            new Pair<> (13,3) ,new Pair<> (13,7) ,new Pair<> (13,11) ,new Pair<> (13,13) ,new Pair<> (13,17) ,new Pair<> (13,21)));


            tripleLetter = new HashSet<>(Arrays.asList(new Pair<>(2,10), new Pair<>(2,14), new Pair<>(6,10), new Pair<>(6,14), new Pair<>(10,10), new Pair<>(10,14), new Pair<>(14,10), new Pair<>(14,14), new Pair<>(18,10), new Pair<>(18,14), new Pair<>(22,10), new Pair<>(22,14), new Pair<>(10,2), new Pair<>(10,6), new Pair<>(14,2), new Pair<>(14,6), new Pair<>(10,18), new Pair<>(10,22), new Pair<>(14,18), new Pair<>(14,22)));
            doubleWord = new HashSet<>(Arrays.asList(new Pair<>(1,1), new Pair<>(2,2), new Pair<>(3,3), new Pair<>(4,4), new Pair<>(5,5), new Pair<>(6,6), new Pair<>(7,7), new Pair<>(8,8), new Pair<>(9,9), new Pair<>(10,10), new Pair<>(11,11),  new Pair<>(13,13), new Pair<>(14,14), new Pair<>(15,15), new Pair<>(16,16), new Pair<>(17,17), new Pair<>(18,18), new Pair<>(19,19), new Pair<>(20,20), new Pair<>(21,21), new Pair<>(22,22), new Pair<>(23,23),
                            new Pair<> (1,23) ,new Pair<> (2,22) ,new Pair<> (3,21) ,new Pair<> (4,20) ,new Pair<> (5,19) ,new Pair<> (6,18) ,new Pair<> (7,17) ,new Pair<> (8,16) ,new Pair<> (9,15) ,
                            new Pair<> (15,9) ,new Pair<> (16,8) ,new Pair<> (17,7) ,new Pair<> (18,6) ,new Pair<> (19,5) ,new Pair<> (20,4) ,new Pair<> (21,3) ,new Pair<> (22,2) ,new Pair<> (23,1)));

            tripleWord = new HashSet<>(Arrays.asList(new Pair<>(0,0), new Pair<>(0,12), new Pair<>(0,24), new Pair<>(12,0), new Pair<>(12,24), new Pair<>(24,0), new Pair<>(24,12), new Pair<>(24,24)));
        }

        for (int i = 0; i <= size; i++) {
            for (int j = 0; j <= size; j++) {
                Pair<Integer,Integer> pos = new Pair<>(i, j);
                if (doubleLetter.contains(pos)) {
                    board[i][j] = new Box.DoubleLetter(i, j);
                }
                else if (tripleLetter.contains(pos)) {
                    board[i][j] = new Box.TripleLetter(i, j);
                }
                else if (doubleWord.contains(pos)) {
                    board[i][j] = new Box.DoubleWord(i, j);
                }
                else if (tripleWord.contains(pos)) {
                    board[i][j] = new Box.TripleWord(i, j);
                }
                else {
                    board[i][j] = new Box(i, j);
                }
            }
        }
    }

    /**
     * Consultora del parametro size
     * Pre: true
     * Post: se devuelve el parametro size
     * @return el tamaño del tablero
     */

    public int getSize() {
        return this.size;
    }

    /**
     * Imprime los crosschecks del tablero, esta función es auxiliar, no es necesaria para el funcionamiento del juego.
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
     * @param characters conjunto de caracteres que se van a utilizar para computar los crosschecks
     * @param dawg diccionario que se va a utilizar para comprobar si las palabras son válidas
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
     * devuelve true si la casilla está vacía, false en caso contrario
     * @param row fila de la casilla
     * @param column columna de la casilla
     * @return true si la casilla está vacía, false en caso contrario
     * @throws IllegalArgumentException si las coordenadas están fuera de los límites del tablero
     * @throws IllegalArgumentException si la casilla no existe en el tablero
     */

    public boolean isEmpty(int row, int column) {
        if (row < 0 || row >= size || column < 0 || column >= size) {
            throw new IllegalArgumentException("Coordenadas fuera de los límites del tablero.");
        }
        if (this.board[row][column] == null) {
            throw new IllegalArgumentException("La casilla no existe en el tablero.");
        }

        // Verifica si la casilla está vacía
        return this.board[row][column].isEmpty();
    }

    /**
     * Verifica si una casilla es una casilla ancla
     * Pre: ya existe un tablero
     * devuelve true si la casilla es una casilla ancla, false en caso contrario
     * @param row fila de la casilla
     * @param column columna de la casilla
     * @return true si la casilla es una casilla ancla, false en caso contrario
     * @throws IllegalArgumentException si las coordenadas están fuera de los límites del tablero
     * @throws IllegalArgumentException si la casilla no existe en el tablero
     */
    public boolean isAnchor(int row, int column) {
        if (row < 0 || row >= size || column < 0 || column >= size) {
            throw new IllegalArgumentException("Coordenadas fuera de los límites del tablero.");
        }
        if (this.board[row][column] == null) {
            throw new IllegalArgumentException("La casilla no existe en el tablero.");
        }
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
     * @return el tablero
     */
    public Box[][] getBoard() {
        return this.board;
    }

    /**
     * Modificadora del tablero
     * Pre: ya existe un tablero
     * Post: se modifica el tablero
     * @param board el nuevo tablero
     */
    public void setBoard(Box[][] board) {
        this.board = board;
    }

    /**
     * Modificadora del parametro size
     * Pre: ya existe un size
     * Post: se modifica el tamaño del tablero
     * @param size el nuevo tamaño del tablero
     * @throws IllegalArgumentException si el tamaño no es 7, 15 o 25
     */
    public void setSize(int size) {
        if (size != 7 && size != 15 && size != 25) {
            throw new IllegalArgumentException("El tamaño del tablero debe ser 7, 15 o 25.");  
        }
        this.size = size;
    }


    public Pair<Set<Pair<Integer, Integer>>, Set<Pair<String, Integer>>> BoardInfo()
    {
        Set<Pair<Integer, Integer>> positions = new HashSet<>();
        Set<Pair<String, Integer>> letters = new HashSet<>();
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (this.board[i][j].getSymbol() != null) {
                    positions.add(new Pair<>(i, j));
                    letters.add(new Pair<>(this.board[i][j].getSymbol(), this.board[i][j].getValue()));
                }
            }
        }
        return new Pair<>(positions, letters);
    }


    /**
     * Verifica si una posición del tablero tiene una ficha
     * Pre: ya existe un tablero
     * Post: devuelve true si la casilla tiene una ficha, false en caso contrario
     * @param row fila de la casilla
     * @param column columna de la casilla
     * @return true si la casilla tiene una ficha, false en caso contrario
     * @throws IllegalArgumentException si las coordenadas están fuera de los límites del tablero
     * @throws IllegalArgumentException si la casilla no existe en el tablero
     * @throws IllegalStateException si el tablero no ha sido inicializado
    */
    public boolean hasLetter(int row, int column) {
        if (this.board == null) {
            throw new IllegalStateException("El tablero no ha sido inicializado.");
        }
        if (this.board[row][column] == null) {
            throw new IllegalArgumentException("La casilla no existe en el tablero.");
        }
        if( row < 0 || row >= size || column < 0 || column >= size) {
            throw new IllegalArgumentException("Coordenadas fuera de los límites del tablero.");
        }

        return this.board[row][column].getSymbol() != null;
    }

    /**
     * Coloca una letra en el tablero
     * Pre: ya existe un tablero
     * Post: se coloca la letra en la casilla correspondiente y
     * devuelve el valor de la letra multiplicado por el multiplicador de letra de la casilla
     * @param row fila de la casilla
     * @param column columna de la casilla
     * @param letter letra a colocar
     * @param value valor de la letra a colocar
     * @return el valor de la letra multiplicado por el multiplicador de letra de la casilla
     * @throws IllegalArgumentException si las coordenadas están fuera de los límites del tablero
     * @throws IllegalArgumentException si la casilla no existe en el tablero
     * @throws IllegalStateException si el tablero no ha sido inicializado
     * @throws IllegalArgumentException si la letra es nula o vacía
     */
    public int placeLetter(int row, int column, String letter, int value) {
        if (this.board == null) {
            throw new IllegalStateException("El tablero no ha sido inicializado.");
        }
        if (this.board[row][column] == null) {
            throw new IllegalArgumentException("La casilla no existe en el tablero.");
        }
        if (letter == null || letter.isEmpty()) {
            throw new IllegalArgumentException("La letra no puede ser nula o vacía.");
        }
        if (row < 0 || row >= size || column < 0 || column >= size) {
            throw new IllegalArgumentException("Coordenadas fuera de los límites del tablero.");
        }

        // Si la casilla es válida, coloca la letra y devuelve su valor
        if (this.board[row][column].isEmpty()) {
            isEmpty = false;
            int multiplier = 1;
            Box box = this.board[row][column];
            if (box instanceof Box.DoubleLetter) multiplier = 2;
            else if (box instanceof Box.TripleLetter) multiplier = 3;
            System.out.println("Placing letter: " + letter + " at position: [" + column + "," + row + "] with value: " + value + " and multiplier: " + multiplier);
            this.board[row][column].setLetter(letter, value);

            return value*multiplier;
        }
        return 0;
    }

    /**
     * Verifica si el tablero está vacío
     * Pre: ya existe un tablero
     * Post: devuelve true si el tablero está vacío, false en caso contrario
     * @return true si el tablero está vacío, false en caso contrario
     * @throws IllegalStateException si el tablero no ha sido inicializado
     */
    public boolean isEmpty() {
        if (this.board == null) {
            throw new IllegalStateException("El tablero no ha sido inicializado.");
        }
        return isEmpty;
    }

    /**
     * Destructora de casilla
     * Pre: ya existe un tablero
     * Post: se elimina la letra de la casilla correspondiente
     * @param row fila de la casilla
     * @param column columna de la casilla
     * @throws IllegalStateException si el tablero no ha sido inicializado
     * @throws IllegalArgumentException si las coordenadas están fuera de los límites del tablero
     * @throws IllegalArgumentException si la casilla no existe en el tablero
     */

    public void removeLetter(int row, int column) {
        if (this.board == null) {
            throw new IllegalStateException("El tablero no ha sido inicializado.");
        }
        if (this.board[row][column] == null) {
            throw new IllegalArgumentException("La casilla no existe en el tablero.");
        }
        if (row < 0 || row >= size || column < 0 || column >= size) {
            throw new IllegalArgumentException("Coordenadas fuera de los límites del tablero.");
        }
        // Si la casilla es válida, elimina la letra

        this.board[row][column].setLetter(null, 0);
        ///////////////////printBoard();
    }

    /**
     * Imprime el tablero en la consola, es una función auxiliar para visualizar el tablero
     * y comprobar que las letras se colocan correctamente, no se usará en la version final del juego.
     * Pre: ya existe un tablero
     * Post: se imprime el tablero en la consola
     */

    public void printBoard() {
        System.out.print("     ");
        for (int j = 0; j < this.size; j++) {
            if (j < 10) System.out.print(" ");
            System.out.print(j + "      ");
        }
        System.out.println();
    
        for (int i = 0; i < this.size; i++) {
            System.out.print(i + " "); 
            if (i < 10) System.out.print(" "); 
            for (int j = 0; j < this.size; j++) {
                System.out.print(this.board[i][j].toString() + "  ");
            }
            System.out.println();
            System.out.println();
        }
    }

    /**
     * Retorna la casilla correspondiente a las coordenadas de entrada
     * Pre: ya existe un tablero
     * Post: devuelve la casilla correspondiente a los valores de entrada, null si no existe
     * @param row fila de la casilla
     * @param column columna de la casilla
     * @return la casilla correspondiente a los valores de entrada, null si no existe
     * @throws IllegalArgumentException si las coordenadas están fuera de los límites del tablero
     * @throws IllegalArgumentException si la casilla no existe en el tablero
     * @throws IllegalStateException si el tablero no ha sido inicializado
     * @see Box
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
     * Post: devuelve la letra de la casilla correspondiente a los valores de entrada
     * @param row fila de la casilla
     * @param column columna de la casilla
     * @return la letra de la casilla correspondiente a los valores de entrada, null si no hay letra
     * @throws IllegalStateException si el tablero no ha sido inicializado
     * @throws IllegalArgumentException si las coordenadas están fuera de los límites del tablero
     * @throws IllegalArgumentException si la casilla no existe en el tablero
     */

    public String getLetter(int row, int column) { 
        if (this.board == null) {
            throw new IllegalStateException("El tablero no ha sido inicializado.");
        }
        if (row < 0 || row >= size || column < 0 || column >= size) {
            throw new IllegalArgumentException("Coordenadas fuera de los límites del tablero.");
        }
        if (this.board[row][column] == null) {
            throw new IllegalArgumentException("La casilla no existe en el tablero.");
        }
        // Si las coordenadas son válidas, devuelve la letra de la casilla correspondiente
        return board[row][column].getSymbol();
    }

    /**
     * Retorna el valor del multiplicador de palabra dada una casilla
     * Pre: ya existe un tablero
     * Post: devuelve el valor del multiplicador de palabra de la casilla correspondiente a los valores de entrada
     *         1 si no es una una casilla de multiplicador de palabra
     * @param row fila de la casilla
     * @param column columna de la casilla
     * @return el valor del multiplicador de palabra de la casilla correspondiente a los valores de entrada
     * @throws IllegalArgumentException si las coordenadas están fuera de los límites del tablero
     * @throws IllegalArgumentException si la casilla no existe en el tablero
     * @throws IllegalStateException si el tablero no ha sido inicializado
     */

    public int getWordBonus(int row, int column) {
        if (this.board == null) {
            throw new IllegalStateException("El tablero no ha sido inicializado.");
        }
        if (this.board[row][column] == null) {
            throw new IllegalArgumentException("La casilla no existe en el tablero.");
        }
        if (row < 0 || row >= size || column < 0 || column >= size) {
            throw new IllegalArgumentException("Coordenadas fuera de los límites del tablero.");
        }

        if (row >= 0 && row < size && column >= 0 && column < size) {
            Box box = this.board[row][column];
            if (box instanceof Box.DoubleWord) return 2;
            else if (box instanceof Box.TripleWord) return 3;
        }
        return 1;
    }
}