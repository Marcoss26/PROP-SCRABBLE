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
    // post: devuelve true si la siguiente letra puede ser un caracter especial, false en caso contrario
    private boolean specialCharacter(String word, int index) 
    {
        if(index == word.length() - 1) return false;

        if(language == "es") {

            if(word.charAt(index) == 'c' && word.charAt(index + 1) == 'h') return true; // Comprobamos si el caracter es una 'ch'
            if(word.charAt(index) == 'l' && word.charAt(index + 1) == 'l') return true; // Comprobamos si el caracter es una 'll'
            if(word.charAt(index) == 'r' && word.charAt(index + 1) == 'r') return true; // Comprobamos si el caracter es una 'rr'
        }

        if(language == "cat") {

            if(word.charAt(index) == 'l' && word.charAt(index + 1) == '·') return true; // Comprobamos si el caracter es una 'l·l'
            if(word.charAt(index) == 'n' && word.charAt(index + 1) == 'y') return true; // Comprobamos si el caracter es una 'ny'
        }

        return false;
    }

    //pre: word es la palabra a introducir al DAWG
    //post: añade la palabra al DAWG, si ya existe se queda igual
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
            node.setState(true); // Cambiamos el estado del nodo a final
            return;
        }

        // Caso Recursivo
        boolean isSpecial = specialCharacter(word, index);
        if(isSpecial && language == "es") {

            String c1 = String.valueOf(word.charAt(index));
            String c2 = String.valueOf(word.charAt(index + 1));

            Node nextNode1 = node.children.get(c1);
            Node nextNode2 = node.children.get(c1 + c2);
            if(nextNode1 == null) {
                nextNode1 = new Node();
                node.addNextNode(c1, nextNode1);
            }
            if(nextNode2 == null) {
                nextNode2 = new Node();
                node.addNextNode(c1 + c2, nextNode2);
            }
            addWordRec(nextNode1, word, index + 1);
            addWordRec(nextNode2, word, index + 2);
        }
        else if(isSpecial && language == "cat") {

            if(word.charAt(index) == 'l') {
                Node nextNode = node.children.get("l·l");
                if(nextNode == null) {
                    nextNode = new Node();
                    node.addNextNode("l·l", nextNode);
                }
                addWordRec(nextNode, word, index + 3);
            }
            else {
                Node nextNode1 = node.children.get("n");
                Node nextNode2 = node.children.get("ny");
                if(nextNode1 == null) {
                    nextNode1 = new Node();
                    node.addNextNode("n", nextNode1);
                }
                if(nextNode2 == null) {
                    nextNode2 = new Node();
                    node.addNextNode("ny", nextNode2);
                }
                addWordRec(nextNode1, word, index + 1);
                addWordRec(nextNode2, word, index + 2);
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
        String letter = String.valueOf(word.charAt(index));
        Node nextNode = node.children.get(letter);

        if (nextNode == null)
        {
            return false;
        }

        return existsWordRec(nextNode, word, index + 1);
    }
}