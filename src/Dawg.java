import java.util.*;

public class Dawg
{
    //Atributos del DAWG
    public class Node
    {
        private Map<String, Node> children ; // Mapa identificador del nodo actual y puntero que apunta a los siguientes nodos
        private boolean isFinalNode; // Indica si el nodo es terminal

        public Node(boolean isFinalNode)
        {
            // Esta constructora necesita como parámetros el id del nodo y un booleano, no sé si son necesarios los dos parámetros.
            this.children = new HashMap<String, Node>();
            this.isFinalNode = isFinalNode;
        }

        public boolean isFinal()
        {
            return isFinalNode;
        }

        public void addNextNode(String nextLetter, Node node)
        {
            // Añadimos un nodo al conjunto de nodos siguientes
            children.put(nextLetter, node);
        } //añdairé remove solo si es necesario

        public void changeState()
        {
            isFinalNode = !isFinalNode;
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
        boolean isSpecial = specialCharacter(word, index);
        if(isSpecial && language == "es") {

            String c1 = String.valueOf(word.charAt(index));
            String c2 = String.valueOf(word.charAt(index + 1));

            Node nextNode1 = node.children.get(c1);
            Node nextNode2 = node.children.get(c1 + c2);
            if(nextNode1 == null) {
                nextNode1 = new Node(false);
                node.addNextNode(c1, nextNode1);
            }
            if(nextNode2 == null) {
                nextNode2 = new Node(false);
                node.addNextNode(c1 + c2, nextNode2);
            }
            addWordRec(nextNode1, word, index + 1);
            addWordRec(nextNode2, word, index + 2);
        }
        else if(isSpecial && language == "cat") {

            if(word.charAt(index) == 'l') {
                Node nextNode = node.children.get("l·l");
                if(nextNode == null) {
                    nextNode = new Node(false);
                    node.addNextNode("l·l", nextNode);
                }
                addWordRec(nextNode, word, index + 3);
            }
            else {
                Node nextNode1 = node.children.get("n");
                Node nextNode2 = node.children.get("ny");
                if(nextNode1 == null) {
                    nextNode1 = new Node(false);
                    node.addNextNode("n", nextNode1);
                }
                if(nextNode2 == null) {
                    nextNode2 = new Node(false);
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
                nextNode = new Node(false);
                node.addNextNode(letter, nextNode);
            }

            addWordRec(nextNode, word, index + 1);
        }
    }
}