package DomainLayer.DomainClasses;
import java.util.*;

/**
 * Dawg.java
 * @author: Ivan Alcubierre
 * Esta clase representa un DAWG (Directed Acyclic Word Graph).
 * Se encarga de gestionar la creación, eliminación y acceso a las palabras del DAWG.
 */
public class Dawg
{
    //Atributos del DAWG

    /**
     * clase Node
     * @author: Ivan Alcubierre
     * Esta clase representa un nodo del DAWG.
     * Se encarga de gestionar la creación, eliminación y acceso a los nodos del DAWG.
     */
    public class Node
    {   
        //Atributos del nodo
        /**
         * @param children Mapa que almacena los nodos hijos del nodo actual.
         * @param isFinalNode Indica si el nodo es final.
         */
        private Map<String, Node> children ; // Mapa identificador del nodo actual y puntero que apunta a los siguientes nodos
        private boolean isFinalNode; // Indica si el nodo es final

        /**
         * Constructor de la clase Node.
         * pre: True
         * post: crea un nuevo nodo con el mapa de hijos vacío y el estado de isFinalNode inicializado a false
         */
        public Node()
        {
            this.children = new HashMap<String, Node>();
            this.isFinalNode = false;
        }

        //pre: True
        //post: devuelve el valor de isFinalNode
        /**
         * Devuelve el estado del nodo.
         * pre: True
         * post: devuelve true si el nodo es final, false en caso contrario
         * @return true si el nodo es final, false en caso contrario
         */
        public boolean isFinal()
        {
            return isFinalNode;
        }

        /**
         * Añade un nuevo nodo al mapa de hijos del nodo actual.
         * @param nextLetter Letra que identifica el siguiente nodo.
         * @param node Nodo que se quiere añadir.
         * pre: nextLetter es la siguiente letra que se quiere añadir al DAWG y node es el nodo que se quiere añadir, no existe el nodo con el id nextLetter
         * post: añade el nodo al mapa de hijos del nodo actual
         */
        public void addNextNode(String nextLetter, Node node)
        {

            children.put(nextLetter, node);
        }

        /**
         * Cambia el estado del nodo.
         * pre: True
         * post: cambia el estado del nodo a su opuesto
         */
        public void changeState()
        {
            isFinalNode = !isFinalNode;
        }

        /**
         * Cambia el estado del nodo.
         * @param b Nuevo estado del nodo.
         * pre: b es el nuevo estado del nodo
         * post: cambia el estado del nodo a b
         */
        public void setState(boolean b)
        {
            isFinalNode = b;
        }

        /**
         * Devuelve el mapa de hijos del nodo actual.
         * pre: True
         * post: devuelve el mapa de hijos del nodo actual
         * @return Mapa de hijos del nodo actual
         */
        public Map<String, Node> getChildren()
        {
            return children;
        }
    }

    //Atributos del DAWG
    /**
     * @param language Idioma del DAWG.
     * @param root Nodo raíz del DAWG.
     */
    private String language; // Idioma del DAWG
    private Node root; // Nodo raíz del DAWG

    /**
     * Constructor de la clase Dawg.
     * @param language Idioma del DAWG a crear
     * pre: language es el idioma del DAWG que se va a crear
     * post: crea un nuevo DAWG con el idioma especificado y un nodo raíz vacío
     */
    public Dawg(String language)
    {
        this.language = language; // Inicializamos el idioma
        root = new Node(); // Inicializamos el nodo raíz
    }

    /**
     * Devuelve el idioma del DAWG.
     * pre: True
     * post: devuelve el idioma del DAWG
     * @return El idioma del DAWG.
     */
    public String getLanguage()
    {
        return language;
    }

    /**
     * Comprueba si el siguiente carácter es "l·l" y el idioma es catalán.
     * @param word Palabra a comprobar.
     * @param index Índice del carácter a comprobar.
     * pre: word es la palabra a comprobar y index es el índice del carácter a comprobar
     * post: devuelve true si el siguiente carácter es "l·l" y el idioma es catalán, false en caso contrario
     * @return true si el siguiente carácter es "l·l" y el idioma es catalán, false en caso contrario
     */
    private boolean isSpecialCharacterL_L(String word, int index) {
        return (language.equals("ca") && index + 2 < word.length() && word.charAt(index) == 'l' && word.charAt(index + 1) == '·' && word.charAt(index + 2) == 'l');
    }

    /**
     * Comprueba si el siguiente carácter es un carácter especial.
     * @param word Palabra a comprobar.
     * @param index Índice del carácter a comprobar.
     * pre: word es la palabra a comprobar y index es el índice del carácter a comprobar
     * post: devuelve el carácter especial si existe, null en caso contrario
     * @return El carácter especial si existe, null en caso contrario
     */
    private String getSpecialCharacter(String word, int index) {
        if (index >= word.length() - 1) return null;

        if (language.equals("es")) {
            if (index + 1 < word.length()) {
                if (word.charAt(index) == 'C' && word.charAt(index + 1) == 'H') return "CH";
                if (word.charAt(index) == 'L' && word.charAt(index + 1) == 'L') return "LL";
                if (word.charAt(index) == 'R' && word.charAt(index + 1) == 'R') return "RR";
            }
        } else if (language.equals("ca")) {
            if (index + 1 < word.length()) {
                if (word.charAt(index) == 'N' && word.charAt(index + 1) == 'Y') return "NY";
            }
            if (index + 2 < word.length()) {
                if (word.charAt(index) == 'L' && word.charAt(index + 1) == '·' && word.charAt(index + 2) == 'L') return "L·L";
            }
        }

        return null; // No es un carácter especial
    }

    /**
     * Añade una palabra al DAWG.
     * @param word Palabra a añadir al DAWG.
     * pre: word es la palabra a añadir al DAWG
     * post: añade la palabra al DAWG
     */
    public void addWord(String word)
    {
        // Añadimos una palabra al DAWG recursivamente
        addWordRec(root, word, 0);
    }

    /**
     * Añade una palabra al DAWG de forma recursiva.
     * @param node Nodo actual del DAWG.
     * @param word Palabra a añadir al DAWG.
     * @param index Índice de la letra que se está añadiendo.
     * pre: node es el nodo actual, word es la palabra a añadir y index es el índice de la letra que se está añadiendo
     * post: añade la palabra al DAWG
     */
    private void addWordRec(Node node, String word, int index)
    {
        // Caso Base
        if (index == word.length())
        {
            node.setState(true); // Cambiamos el estado del nodo a final
            return;
        }

        // Caso Recursivo
        String specialCharacter = getSpecialCharacter(word, index);
        if (specialCharacter != null)
        {
            Node nextNode = node.children.get(specialCharacter);
            if (nextNode == null)
            {
                nextNode = new Node();
                node.addNextNode(specialCharacter, nextNode);
            }
            addWordRec(nextNode, word, index + specialCharacter.length());
            
            if(!isSpecialCharacterL_L(word, index)) {
                String nextLetter = String.valueOf(word.charAt(index));
                Node nextNode2 = node.children.get(nextLetter);
                if (nextNode2 == null)
                {
                    nextNode2 = new Node();
                    node.addNextNode(nextLetter, nextNode2);
                }
                addWordRec(nextNode2, word, index + 1);
            }
        }
        
        else {
            String letter = String.valueOf(word.charAt(index));
            Node nextNode = node.children.get(letter);

            if (nextNode == null)
            {
                nextNode = new Node();
                node.addNextNode(letter, nextNode);
            }

            addWordRec(nextNode, word, index + 1);
        }
    }

    /**
     * Comprueba si una palabra existe en el DAWG.
     * @param word Palabra a comprobar.
     * pre: word es la palabra a comprobar
     * post: devuelve true si la palabra existe en el DAWG, false en caso contrario
     * @return true si la palabra existe en el DAWG, false en caso contrario
     */
    public boolean existsWord(String word)
    {
        // Comprobamos si la palabra existe en el DAWG
        return existsWordRec(root, word, 0);
    }

    /**
     * Comprueba si una palabra existe en el DAWG de forma recursiva.
     * @param node Nodo actual del DAWG.
     * @param word Palabra a comprobar.
     * @param index Índice de la letra que se está comprobando.
     * pre: node es el nodo actual, word es la palabra a buscar y index es el índice de la letra que se está buscando
     * post: devuelve true si la palabra existe en el DAWG, false en caso contrario
     * @return true si la palabra existe en el DAWG, false en caso contrario
     */
    private boolean existsWordRec(Node node, String word, int index)
    {
        // Caso Base
        if (index == word.length())
        {
            return node.isFinal(); // Comprobamos si el nodo es final
        }

        // Caso Recursivo
        String letter;
        if(isSpecialCharacterL_L(word, index)) letter = "L·L";
        else letter = String.valueOf(word.charAt(index));
        Node nextNode = node.children.get(letter);

        if (nextNode == null)
        {
            return false;
        }

        return existsWordRec(nextNode, word, index + letter.length());
    }

    /**
     * Elimina una palabra del DAWG.
     * @param word Palabra a eliminar del DAWG.
     * pre: word es la palabra a eliminar del DAWG
     * post: elimina la palabra del DAWG
     */
    public void removeWord(String word) {

        if (existsWord(word)) {
            removeWordRec(root, word, 0);
        }
    }

    /**
     * Elimina una palabra del DAWG de forma recursiva.
     * @param node Nodo actual del DAWG.
     * @param word Palabra a eliminar del DAWG.
     * @param index Índice de la letra que se está eliminando.
     * pre: node es el nodo actual, word es la palabra a eliminar y index es el índice de la letra que se está eliminando
     * post: elimina la palabra del DAWG
     */
    private void removeWordRec(Node node, String word, int index) {
        // Caso Base
        if (index == word.length()) {
            node.setState(false); // Marca el nodo como no final
            return;
        }

        // Caso Recursivo
        if(isSpecialCharacterL_L(word, index)) {
            removeWordRec(node.children.get("L·L"), word, index + 3);
        }
        else removeWordRec(node.children.get(String.valueOf(word.charAt(index))), word, index + 1);
    }

    public boolean isPrefix(String prefix) {
        return isPrefixRec(root, prefix, 0);
    }

    private boolean isPrefixRec(Node node, String prefix, int index) {
        if (index == prefix.length()) {
            return true; // El prefijo se encuentra en el DAWG
        }

        String letter = String.valueOf(prefix.charAt(index));
        Node nextNode = node.children.get(letter);

        if (nextNode == null) {
            return false; // El prefijo no se encuentra en el DAWG
        }

        return isPrefixRec(nextNode, prefix, index + 1);
    }
}