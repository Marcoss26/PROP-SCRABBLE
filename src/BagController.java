import java.util.*;

public class BagController {

    private Map<String, Bag> bags = new HashMap<>(); // Bolsas del sistema, relacionadas con la partida a la que pertencen con el string de la partida

    public BagController() {
        this.bags = new HashMap<>();
    }

    public int getTotalLetters(String matchId){

        return bags.get(matchId).getNumLetters();
        
    }
    
    public Bag getBag(String matchId) {
        return bags.get(matchId);
    }

    public void removeBag(String matchId) {
        bags.remove(matchId);
    }

    public boolean bagExists(String matchId) {
        return bags.containsKey(matchId);
    }

    public void clearBags() {
        bags.clear();
    }

    public boolean bagIsEmpty(String matchId) {
        return bags.get(matchId).isEmpty();
    }

    //Pre: matchId es el id de la partida a la que pertenece la bolsa
    //letters es el mapa de letras y su cantidad
    //Post: crea una bolsa y la asocia a la partida con el id matchId
    //Post: la bolsa se crea con las letras y cantidades que se le pasaron como parámetro
    public void createBag(String matchId, List<Letter> letters, String language) {
        Bag bag = new Bag(letters); 
        bags.put(matchId, bag); 
    }

    public Letter extractLetter(String matchId, String playerId) {

        Bag bag = bags.get(matchId); 
        return bag.extractLetter(playerId); 
       
    }

    public void addLetter(String matchId, Letter letter) {
        Bag bag = bags.get(matchId); 
        bag.addLetter(letter); 
    }

    //función para cambiar las 7 letras del rack cuando el jugador lo pida
    //Pre: matchId es el id de la partida a la que pertenece la bolsa
    //letters es el conjunto de letras que el jugador quiere cambiar
    //Post: devuelve un conjunto de letras que el jugador ha cambiado por las letras que ha devuelto
    //Post: el jugador ha devuelto las letras que ha cambiado a la bolsa   
    public Set<Letter> changeLetters(String matchId, Set<Letter> letters, String playerId) {
        Bag bag = bags.get(matchId); 
        bag.addSetOfLetters(letters); 
        return bag.extractSetOfLetters(playerId); // Devolvemos el conjunto de letras que se van a devolver al jugador
    }
}
