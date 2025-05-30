package DomainLayer.DomainClasses;
import java.util.*;
import java.io.*;

/**
 * Dawg.java
 * @author: Iván Alcubierre
 * Esta clase representa un DAWG (Directed Acyclic Word Graph).
 * Se encarga de gestionar la creación, eliminación y acceso a las palabras del DAWG.
 */
public class Dawg
{

    /**
     * clase Node
     * @author: Iván Alcubierre
     * Esta clase representa un nodo del DAWG.
     * Se encarga de gestionar la creación, eliminación y acceso a los nodos del DAWG.
     */
    public class Node
    {   

        /**
         * Atributos de la clase Node.
         * @param children Mapa que contiene los hijos del nodo actual, donde la clave es el identificador del nodo y el valor es el puntero al siguiente nodo.
         * @param isFinalNode Indica si el nodo es final (es decir, si representa una palabra completa en el DAWG).
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
         */
        public boolean isFinal()
        {
            return isFinalNode;
        }

        /**
         * Añade un nuevo nodo al mapa de hijos del nodo actual.
         * pre: nextLetter es la siguiente letra que se quiere añadir al DAWG y node es el nodo que se quiere añadir, no existe el nodo con el id nextLetter
         * post: añade el nodo al mapa de hijos del nodo actual
         * @param nextLetter La letra que identifica al siguiente nodo.
         * @param node El nodo que se quiere añadir al mapa de hijos del nodo actual.
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
         * @return Mapa que contiene los hijos del nodo actual, donde la clave es el identificador del nodo y el valor es el puntero al siguiente nodo.
         */
        public Map<String, Node> getchildren()
        {
            return children;
        }
    }

    /**
     * Atributos de la clase Dawg.
     * @param language Idioma del DAWG.
     * @param root Nodo raíz del DAWG.
     * @param nodeRegistry Conjunto de nodos del DAWG, para evitar duplicados.
     */
    private String language;            
    private Node root;                  
    private Map<Node,Node> nodeRegistry;

    /**
     * Constructor de la clase Dawg.
     * pre: language es el idioma del DAWG que se va a crear
     * post: crea un nuevo DAWG con el idioma especificado y un nodo raíz vacío
     * @param language Idioma del DAWG (por ejemplo, "en" para inglés, "es" para español, "ca" para catalán).
     * @throws IOException Si ocurre un error al leer el fichero de palabras del idioma especificado.
     */
    public Dawg(String language) throws IOException
    {
        this.language = language; // Inicializamos el idioma
        root = new Node(); // Inicializamos el nodo raíz
        this.nodeRegistry = new HashMap<>(); // Inicializamos el conjunto de nodos
        this.addWords();
        
    }

    /**
     * Función auxiliar que imprime el número de nodos del DAWG y su estructura.
     * pre: True
     * post: imprime el número de nodos del DAWG y su estructura
     */
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

    /**
     * Devuelve el nodo raíz del DAWG.
     * pre: True
     * post: devuelve el nodo raíz del DAWG
     * @return El nodo raíz del DAWG.
     */
    public Node getRoot()
    {
        return root;
    }

    /**
     * Comprueba si el siguiente carácter es un carácter especial.
     * pre: word es la palabra a comprobar y index es el índice del carácter a comprobar
     * post: devuelve el carácter especial si existe, null en caso contrario
     * @param word La palabra a comprobar.
     * @param index El índice del carácter a comprobar.
     * @return El carácter especial si existe, null en caso contrario.
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

    /**
     * Devuelve la longitud de una palabra en el DAWG.
     * pre: word es la palabra a comprobar
     * post: devuelve la longitud de la palabra en el DAWG
     * @param word La palabra a comprobar.
     * @return La longitud de la palabra en el DAWG.
     */
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

    /**
     * Añade las palabras del fichero al DAWG.
     * pre: True
     * post: añade las palabras del fichero al DAWG
     * @param language Idioma del DAWG (por ejemplo, "en" para inglés, "es" para español, "ca" para catalán).
     * @throws IOException Si ocurre un error al leer el fichero de palabras del idioma especificado.
     */
    private void addWords() throws IOException
    {   
        // Añadir palabras al DAWG
        System.out.println("he llegado a addWords");
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
        System.out.println("words added to the DAWG");
    }

    /**
     * Añade una palabra al DAWG.
     * pre: word es la palabra a añadir al DAWG
     * post: añade la palabra al DAWG
     * @param word La palabra a añadir al DAWG.
     * @param oldword La palabra anterior, para evitar duplicados.
     */
    private void addWord(String word,String oldword)
    {
        //System.out.println("Añadiendo palabra"  + ": " + word);
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

    /**
     * Añade un sufijo a un nodo del DAWG.
     * pre: node es el nodo al que se le quiere añadir el sufijo y word es el sufijo a añadir
     * post: añade el sufijo al nodo del DAWG
     * @param node El nodo al que se le quiere añadir el sufijo.
     * @param word El sufijo a añadir al nodo del DAWG.
     */
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

    /**
     * Reemplaza o registra un nodo en el DAWG.
     * pre: node es el nodo al que se le quiere añadir el sufijo y partToFix es la parte de la palabra que se quiere arreglar
     * post: reemplaza o registra el nodo en el DAWG
     * @param node El nodo al que se le quiere añadir el sufijo.
     * @param partToFix La parte de la palabra que se quiere arreglar.
     * @param i El índice de la letra que se está arreglando.
     */
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

    /**
     * Devuelve el último hijo de un nodo en el DAWG.
     * pre: node es el nodo al que se le quiere obtener el último hijo y word es la palabra a buscar
     * post: devuelve el último hijo del nodo en el DAWG
     * @param node El nodo al que se le quiere obtener el último hijo.
     * @param word La palabra a buscar.
     * @return El último hijo del nodo en el DAWG.
     */
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

    /**
     * Devuelve la ultima palabra de un nodo en el DAWG.
     * pre: node es el nodo al que se le quiere obtener la última palabra y word es la palabra a buscar
     * post: devuelve la última palabra del nodo en el DAWG
     * @param node El nodo al que se le quiere obtener la última palabra.
     * @param word La palabra a buscar.
     * @return La última palabra del nodo en el DAWG.
     */
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

    /**
     * Devuelve el último nodo de una palabra en el DAWG.
     * pre: node es el nodo raíz del DAWG y word es la palabra a buscar
     * post: devuelve el último nodo de la palabra en el DAWG
     * @param node El nodo raíz del DAWG.
     * @param word La palabra a buscar.
     * @return El último nodo de la palabra en el DAWG.
     */
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

    /**
     * Devuelve el prefijo común de una palabra en el DAWG.
     * pre: word es la palabra a buscar
     * post: devuelve el prefijo común de la palabra en el DAWG
     * @param word La palabra a buscar.
     * @return El prefijo común de la palabra en el DAWG.
     */
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
     * pre: word es la palabra a comprobar
     * post: devuelve true si la palabra existe en el DAWG, false en caso contrario
     * @param word La palabra a comprobar.
     * @return true si la palabra existe en el DAWG, false en caso contrario.
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
     * pre: word es la palabra a eliminar del DAWG
     * post: elimina la palabra del DAWG
     * @param word La palabra a eliminar del DAWG.
     */
    public void removeWord(String word) {

        if (existsWord(word)) {
            removeWordRec(root, word, 0);
        }
    }

    /**
     * Elimina una palabra del DAWG de forma recursiva.
     * pre: node es el nodo actual, word es la palabra a eliminar y index es el índice de la letra que se está eliminando
     * post: elimina la palabra del DAWG
     * @param node El nodo actual del DAWG.
     * @param word La palabra a eliminar del DAWG.
     * @param index El índice de la letra que se está eliminando en la palabra.
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

    /**
     * Comprueba si un prefijo existe en el DAWG.
     * pre: prefix es el prefijo a comprobar
     * post: devuelve true si el prefijo existe en el DAWG, false en caso contrario
     * @param prefix El prefijo a comprobar.
     * @return true si el prefijo existe en el DAWG, false en caso contrario.
     */
    public boolean isPrefix(String prefix) {
        return isPrefixRec(root, prefix, 0);
    }

    /**
     * Comprueba si un prefijo existe en el DAWG de forma recursiva.
     * pre: node es el nodo actual, prefix es el prefijo a comprobar y index es el índice de la letra que se está comprobando
     * post: devuelve true si el prefijo existe en el DAWG, false en caso contrario
     * @param node El nodo actual del DAWG.
     * @param prefix El prefijo a comprobar.
     * @param index El índice de la letra que se está comprobando en el prefijo.
     * @return true si el prefijo existe en el DAWG, false en caso contrario.
     */
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

