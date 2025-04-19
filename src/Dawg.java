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
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Node other = (Node) obj;
            return isFinalNode == other.isFinalNode && children.equals(other.children);
        }

        @Override
        public int hashCode() {
            return Objects.hash(children, isFinalNode);
        }
    }

    //Atributos del DAWG
    private String language; // Idioma del DAWG
    private Node root; // Nodo raíz del DAWG

    public Dawg(String language)
    {
        this.language = language; // Inicializamos el idioma
        root = new Node(false); // Inicializamos el nodo raíz
    }

    //pre: True
    //post: devuelve el idioma del DAWG
    public String getLanguage()
    {
        return language;
    }

    // pre: word es la palabra a introducir al DAWG
    // post: devuelve el carácter especial que se encuentra en la palabra en la posicion index o null si no hay ninguno
    private String getSpecialCharacter(String word, int index) {
        if (index >= word.length() - 1) return null;

        if (language.equals("es")) {
            if (index + 1 < word.length()) {
                if (word.charAt(index) == 'c' && word.charAt(index + 1) == 'h') return "ch";
                if (word.charAt(index) == 'l' && word.charAt(index + 1) == 'l') return "ll";
                if (word.charAt(index) == 'r' && word.charAt(index + 1) == 'r') return "rr";
            }
        } else if (language.equals("ca")) {
            if (index + 1 < word.length()) {
                if (word.charAt(index) == 'n' && word.charAt(index + 1) == 'y') return "ny";
            }
            if (index + 2 < word.length()) {
                if (word.charAt(index) == 'l' && word.charAt(index + 1) == '·' && word.charAt(index + 2) == 'l') return "l·l";
            }
        }

        return null; // No es un carácter especial
    }

    //pre: word es la palabra a introducir al DAWG
    //post: añade la palabra al DAWG, si ya existe se queda igual
    public void addWord(String word) {
        root = addWordRec(root, word, 0);
    }

    private Node addWordRec(Node node, String word, int index) {

        // Caso Base
        if (index == word.length()) {
            node.setFinal(true);
            return minimize(node);
        }

        // Caso Recursivo
        String specialChar = getSpecialCharacter(word, index);
        if (specialChar != null) {
            // Camino con carácter especial
            Node specialNode = node.getChildren().get(specialChar);
            if (specialNode == null) {
                specialNode = new Node();
                node.getChildren().put(specialChar, specialNode);
            }
            specialNode = addWordRec(specialNode, word, index + specialChar.length());
            node.getChildren().put(specialChar, specialNode);

            // Camino con letras separadas
            if( ! (language == "ca" && specialChar.charAt(index) == 'l')) {
                for (int i = 0; i < specialChar.length(); i++) {
                    String letter = String.valueOf(specialChar.charAt(i));
                    Node child = node.getChildren().get(letter);
                    if (child == null) {
                        child = new Node();
                        node.getChildren().put(letter, child);
                    }
                    node = child;
                }
                node = addWordRec(node, word, index + specialChar.length());
            }
        } else {
            // Camino sin carácter especial
            String letter = String.valueOf(word.charAt(index));
            Node child = node.getChildren().get(letter);
            if (child == null) {
                child = new Node();
                node.getChildren().put(letter, child);
            }
            child = addWordRec(child, word, index + 1);
            node.getChildren().put(letter, child);
        }

        return minimize(node);
    }

    //pre: node es el nodo a minimizar
    //post: devuelve el nodo minimizado o reutiliza el nodo si ya existe
    private Node minimize(Node node) {
        if (minimizedNodes.containsKey(node)) {
            return minimizedNodes.get(node); // Reutiliza el nodo existente
        } else {
            minimizedNodes.put(node, node); // Agrega el nodo al mapa
            return node;
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
        String letter = String.valueOf(word.charAt(index));
        Node nextNode = node.children.get(letter);

        if (nextNode == null)
        {
            return false;
        }

        return existsWordRec(nextNode, word, index + 1);
    }
}