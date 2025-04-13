import java.util.*;

public class Dawg
{
    //Atributos del DAWG
    public class Node
    {
        private String id; // Identificador del nodo
        private Set<Node> nxtNodes ; // Mapa identificador del nodo actual y puntero que apunta a los siguientes nodos
        private boolean isFinalNode; // Indica si el nodo es terminal

        public Node(String id, boolean isFinalNode)
        {
            // Esta constructora necesita como parámetros el id del nodo y un booleano, no sé si son necesarios los dos parámetros.
            this.id = id;
            this.nxtNodes = new HashSet<>();
            this.isFinalNode = isFinalNode;
            
        }

        public String getId()
        {
            return id;
        }

        public void addNextNode(Node node)
        {
            // Añadimos un nodo al conjunto de nodos siguientes
            nxtNodes.add(node);
        } //añdairé remove solo si es necesario

        public void changeState()
        {
            isFinalNode = !isFinalNode;
        }

    }

    private Node root; // Nodo raíz del DAWG
    //... faltan funciones y atributos 

    public Dawg()
    {
        // Create a new dictionary
    }
}