package DomainClasses;
import java.util.*;
import java.io.*;

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
        private Map<String, Node> children; // Mapa identificador del nodo actual y puntero que apunta a los siguientes nodos
        private boolean isFinalNode; // Indica si el nodo es final

        @Override
        public boolean equals(Object obj)
        {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Node other = (Node) obj;
            return isFinalNode == other.isFinalNode && Objects.equals(children, other.children);
        }

        @Override
        public int hashCode()
        {
            return Objects.hash(isFinalNode, children);
        }



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

        /*public void print(String prefix) {
            for (Map.Entry<String, Node> entry : children.entrySet()) {
                System.out.println(prefix + " └─[" + entry.getKey() + "]");
                System.out.println();
                entry.getValue().print(prefix + "   ");
            }
        }*/

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
        public Map<String, Node> getchildren()
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
    private Map<Node,Node> nodeRegistry;
    /**
     * Constructor de la clase Dawg.
     * @param language Idioma del DAWG a crear
     * pre: language es el idioma del DAWG que se va a crear
     * post: crea un nuevo DAWG con el idioma especificado y un nodo raíz vacío
     */
    public Dawg(String language) throws IOException
    {
        this.language = language; // Inicializamos el idioma
        root = new Node(); // Inicializamos el nodo raíz
        this.nodeRegistry = new HashMap<>(); // Inicializamos el conjunto de nodos
        this.addWords();
    }


    public void numberNodes()
    {
        System.out.println("Numero de nodos: " + nodeRegistry.size());
        System.out.println("Estructura del DAWG:");
        //root.print("");
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

    public Node getRoot()
    {
        return root;
    }

    /**
     * Comprueba si el siguiente carácter es un carácter especial.
     * @param word Palabra a comprobar.
     * @param index Índice del carácter a comprobar.
     * pre: word es la palabra a comprobar y index es el índice del carácter a comprobar
     * post: devuelve el carácter especial si existe, null en caso contrario
     * @return El carácter especial si existe, null en caso contrario
     */
    public String getSpecialCharacter(String word, int index) {
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

    public int getWordLength(String word)
    {
        if(this.language.equals("en"))  return word.length();
        else
        {
            int i = 0;
            int j = 0;
            while(i < word.length())
            {
                String letter = getSpecialCharacter(word, i);
                if(letter == null)  letter = String.valueOf(word.charAt(i));
                i += letter.length();
                j++;
            }
            return j;
        }
    }

    private void addWords() throws IOException
    {
        // Añadir palabras al DAWG
        String filePath = "data/dictionaries/" + language + ".txt";
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String word;
        String oldword = "";
        while ((word = reader.readLine()) != null) {
            word = word.replace("\r","").toUpperCase();
            if (!word.isEmpty()) {
                addWord(word,oldword);
                oldword = word;
            }
        }
        replace_or_register(root,oldword,0);
    }


    /**
     * Añade una palabra al DAWG.
     * @param word Palabra a añadir al DAWG.
     * pre: word es la palabra a añadir al DAWG
     * post: añade la palabra al DAWG
     */
    private void addWord(String word,String oldword)
    {
        //System.out.println("Añadiendo palabra" + i + ": " + word);
        String commonPrefix = commonPrefix(word);
        Node lastNode = get_lastNode(root, commonPrefix);
        String currentSufix = word.substring(commonPrefix.length());
        String partToFix = oldword.substring(commonPrefix.length());
        //System.out.println("Sufijo: " + currentSufix);
        //System.out.println("Funciona aqui");
        //System.out.println("Soy palabra " + i + ": " + word);
        if(lastNode.getchildren().size() > 0)
        {
            replace_or_register(lastNode,partToFix,0);
        }
        //String lastword = get_last_word(lastNode);
        /*System.out.println("Palabra que deberia registrar: " + oldword);
        System.out.println("Palabra que estoy registrando: " + commonPrefix + lastword);
        System.out.println("Es correcto: " + (oldword.equals(commonPrefix + lastword)));
        System.out.println();*/
        add_sufix(lastNode,currentSufix);
    }


    private void add_sufix(Node node,String word)
    {
        int i = 0;
        while(i < word.length())
        {
            String letter = getSpecialCharacter(word, i);
            if(letter == null)  letter = String.valueOf(word.charAt(i));
            Node nextNode = new Node();
            node.addNextNode(letter, nextNode);
            node = nextNode;
            i+=letter.length();
        }
        node.setState(true); // Marca el nodo como final
    }

    public void replace_or_register(Node node,String partToFix, int i)
    {
        //String last = last_child(node,partToFix);
        //System.out.println("Ultimo hijo: " + last);
        //Node last_child = node.children.get(last);
        String CharacterToFix = getSpecialCharacter(partToFix, i);
        if(CharacterToFix == null)  CharacterToFix = String.valueOf(partToFix.charAt(i));
        Node last_child = node.children.get(CharacterToFix);
        if(last_child.getchildren().size() > 0)
        {
            replace_or_register(last_child,partToFix, i + CharacterToFix.length());
        }
        Node existingNode;
        if((existingNode = nodeRegistry.get(last_child)) != null)
        {
            node.children.put(CharacterToFix, existingNode);
        }
        else    nodeRegistry.put(last_child,last_child);
    }

    /*private String last_child(Node node,String word)
    {
        String heaviestKey = null;

        for (String key : node.getchildren().keySet())
        {
            //if(word.equals("COL")) System.out.println("Key: " + key);
            if (heaviestKey == null || key.compareTo(heaviestKey) > 0)
            {
                //if(word.equals("COL")) System.out.println("Evolution of heaviestKey:" + heaviestKey + " to " + key);
                heaviestKey = key;
            }
        }
        return heaviestKey; // Retorna el nodo hijo más pesado
    }*/

    /*private String get_last_word(Node node)
    {
        String palabra = "";
        String suma = "";
        while((suma = last_child(node,"a"))!=null)
        {
            palabra+=suma;
            node = node.children.get(suma);
        }
        return palabra;
    }*/


    public Node get_lastNode(Node node, String word)
    {
        Node lastNode = node;
        int i = 0;
        while( i < word.length())
        {
            String letter = getSpecialCharacter(word, i);
            if(letter == null)    letter = String.valueOf(word.charAt(i)); 
            Node nextNode = lastNode.children.get(letter);
            lastNode = nextNode;
            i+=letter.length();
        }
        return lastNode;
    }

    public String commonPrefix(String word)
    {
        String commonPrefix = "";
        Node node = root;
        //System.out.println("Palabra: " + word);
        /*for(char c: word.toCharArray())
        {
            //System.out.println("Comprobando: " + c);
            if(node.children.containsKey(String.valueOf(c)))
            {
                commonPrefix += c;
                node = node.children.get(String.valueOf(c));
            }
            else break;
        }*/
        //El problema pasa porque el DAWG no tiene en cuenta los caracteres especiales, entonces si tenemos colzo, como y col·lo, como cierra colzo, entonces cuando llega col·lo(Ya que supuestamente no es de prefijo col sino col·l, pero para java si que lo es) vuelve a intentar cambiar y cerrarlo y causa un error, ya que intenta cambiar un nodo ya registrado
        int i = 0;
        while(i < word.length())
        {
            String letter = getSpecialCharacter(word, i);
            if(letter == null)  letter = String.valueOf(word.charAt(i));
            if(node.children.containsKey(letter))
            {
                commonPrefix += letter;
                node = node.children.get(letter);
                i+=letter.length();
            }
            else break;
        }
        //System.out.println("commonPrefix: " + commonPrefix);
        return commonPrefix;
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
        Node node = root;
        boolean exists = false;
        // Comprobamos si la palabra existe en el DAWG
        int i = 0;
        while(i < word.length())
        {
            String letter = getSpecialCharacter(word, i);
            if(letter == null)  letter = String.valueOf(word.charAt(i));
            //System.out.println("Intentando llegar a: " + letter);
            if((node = node.children.get(letter)) != null)
            {
                //System.out.println("Llegue a: " + letter);
                //if(node.isFinal())  System.out.println("Es un nodo final");
                //System.out.println("La i vale: " + i);
                if(node.isFinal() && i == word.length() - letter.length())
                {
                    exists = true; // La palabra existe
                }
            }
            else
            {
                exists = false; // La palabra no existe
                break;
            }
            i+=letter.length();
        }
        return exists;
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
        String specialChar;
        removeWordRec(node.children.get(String.valueOf(word.charAt(index))), word, index + 1);
        if((specialChar = getSpecialCharacter(word, index)) != null)    removeWordRec(node.children.get(specialChar), word, index + 2);
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

