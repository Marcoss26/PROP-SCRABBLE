import java.util.*;

public class Dawg
{
    //Atributos del DAWG
    public class Node
    {
        private Map<String, Node> children ; // Mapa identificador del nodo actual y puntero que apunta a los siguientes nodos
        private boolean isFinalNode; // Indica si el nodo es final

        // pre: True
        // post: crea un nuevo nodo con el mapa de hijos vacío y el estado de isFinalNode inicializado a false
        public Node()
        {
            this.children = new HashMap<String, Node>();
            this.isFinalNode = false;
        }

        //pre: True
        //post: devuelve el valor de isFinalNode
        public boolean isFinal()
        {
            return isFinalNode;
        }

        //pre: nextLetter es la siguiente letra que se quiere añadir al DAWG y node es el nodo que se quiere añadir
        //     no existe el nodo con el id nextLetter
        //post: añade el nodo al mapa de hijos del nodo actual
        public void addNextNode(String nextLetter, Node node)
        {

            children.put(nextLetter, node);
        }

        //pre: True
        //post: hace un toggle de isFinalNode
        public void changeState()
        {
            isFinalNode = !isFinalNode;
        }

        //pre: True
        //post: cambia isFinalNode a lo que valga b
        public void setState(boolean b)
        {
            isFinalNode = b;
        }

        public Map<String, Node> getChildren()
        {
            return children;
        }
    }

    //Atributos del DAWG
    private String language; // Idioma del DAWG
    private Node root; // Nodo raíz del DAWG
    private Map<Node, Node> minimizedNodes = new HashMap<>(); // Mapa para nodos únicos

    public Dawg(String language)
    {
        this.language = language; // Inicializamos el idioma
        root = new Node(); // Inicializamos el nodo raíz
    }

    //pre: True
    //post: devuelve el idioma del DAWG
    public String getLanguage()
    {
        return language;
    }

    // pre: word es la palabra a introducir al DAWG
    // post: devuelve true si el siguiente caracter es "l·l" y el idioma es catalán false en caso contrario
    private boolean isSpecialCharacterL_L(String word, int index) {
        return (language.equals("ca") && index + 2 < word.length() && word.charAt(index) == 'l' && word.charAt(index + 1) == '·' && word.charAt(index + 2) == 'l');
    }
    
    // pre: word es la palabra a introducir al DAWG
    // post: devuelve el carácter especial que se encuentra en la palabra en la posicion index o null si no hay ninguno
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

    public void addWord(String word)
    {
        // Añadimos una palabra al DAWG recursivamente
        addWordRec(root, word, 0);
    }

    private void addWordRec(Node node, String word, int index)
    {
        // Caso Base
        if (index == word.length())
        {
            node.changeState(); // Cambiamos el estado del nodo a final
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
    //pre: word es la palabra a buscar en el DAWG
    //post: devuelve true si la palabra existe en el DAWG, false en caso contrario
    public boolean existsWord(String word)
    {
        // Comprobamos si la palabra existe en el DAWG
        return existsWordRec(root, word, 0);
    }

    //pre: node es el nodo actual, word es la palabra a buscar y index es el índice de la letra que se está buscando
    //post: devuelve true si la palabra existe en el DAWG, false en caso contrario
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

    public void removeWord(String word) {

        if (existsWord(word)) {
            removeWordRec(root, word, 0);
        }
    }

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
}