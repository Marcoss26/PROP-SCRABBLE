package DomainLayer.DomainClasses;
import java.util.*;


public class Ranking {
    //Ranking es una clase Singleton
    private static Ranking instance ;
    private Map<String, Profile> profiles = new HashMap<>(); //Mapa que contiene los perfiles del sistema, relacionados con el nombre de usuario
    //private Set<Profile> profiles = new HashSet<>(); //Esto es un conjunto de los perfiles que están actualmente en el ranking, al no admitir repetidos,
                                                     // evitamos errores de posibles perfiles que puedan tener el mismo username.
    private Map<Integer, Set<String> > ranking = new TreeMap<>(Collections.reverseOrder()); //Mapa que contiene un numero de victorias y los perfiles que 
                                                                                            //tienen ese número de victorias, ordenado de mayor a menor.    

    
    /**
     * Constructora de la clase Ranking
     * Pre: true
     * Post: se ha creado una instancia de la clase Ranking
     */                                                
   
    private Ranking() {
        //El constructor es privado ya que al ser singleton no se puede crear una instancia de la clase desde fuera

    }

    /**
     * Función que devuelve la instancia de la clase Ranking
     * Pre: true
     * Post: se retorna la instancia de la clase Ranking, si no existe se crea una nueva
     * @return 
     */

    //Esta función permite a las demás clases acceder a la instancia de la clase Ranking
    public static Ranking getInstance() {
        if (instance == null) {
            instance = new Ranking();
        }
            return instance;
        }

    /**
     * Consultora que devuelve los perfiles del sistema
     * Pre: true
     * Post: se retorna el mapa de perfiles
     * @return 
     */

    public Map<String,Profile> getProfiles() {
        return profiles;
    }

    /**
     * Consultora que devuelve el ranking del sistema
     * Pre: true
     * Post: se retorna el mapa de ranking
     * @return 
     */

    public Map<Integer, Set<String>> getRanking() {
        return ranking;
    }

    /**
     * Modificadora que establece los perfiles del sistema
     * Pre: true
     * Post: se establece el mapa de perfiles
     * @param profiles 
     */

    public void setProfiles(Map<String,Profile> profiles) {
        this.profiles = profiles;
    }

    /**
     * Modificadora que establece el ranking del sistema
     * Pre: true
     * Post: se establece el mapa de ranking
     * @param ranking 
     */

    public void setRanking(Map<Integer, Set<String>> ranking) {
        this.ranking = ranking;
    }

    /**
     * Modificadora que actualiza el ranking del sistema
     * Pre: existen un ranking y un perfil como minimo
     * Post: se añade el perfil al mapa de perfiles y se actualiza el ranking
     * @param profile 
     */

    //Cuando acabe una partida, se llama a esta función para actualizar el ranking
    public void updateRanking(Profile profile) {
            
            int wins = profile.getWins();
            //nota: posible cambio a esto, en lugar de tener un conjunto con los perfiles que estan en el ranking, recibir el conjunto de perfiles y aqui hacer get del perfil en concreto que se pasa como parametro y si tiene 0 wins tratarlo igual como si no existiera en el ranking
            /*if(wins == 0) { //Si el perfil no está en el ranking, lo añadimos
                profiles.add(profile);
            }*/
            if(wins > 1){
                ranking.get(wins-1).remove(profile.getUsername()); //Si el perfil ya está en el ranking, lo eliminamos de la lista de perfiles con el número de victorias anterior
                                                                    //No es necesario comprobar que es su primera victoria porque si lo fuera no estaria en el conjunto de perfiles
                if(ranking.get(wins-1).isEmpty()) { //Si el número de victorias anterior está vacío, lo eliminamos del ranking
                    ranking.remove(wins-1);
                }
                
            }
            if (ranking.containsKey(wins)) { //Si el número de victorias ya está en el ranking, añadimos el perfil a la lista de perfiles con ese número de victorias
                ranking.get(wins).add(profile.getUsername());
            } else { //Si no está, lo añadimos
                Set<String> actProfiles  = new HashSet<>();
                actProfiles.add(profile.getUsername());
                ranking.put(wins, actProfiles);
            }

    }

    /**
     * Muestra el ranking de los jugadores
     * Pre: existe un ranking
     * Post: se muestra el ranking de los jugadores por consola
     */

    public void displayRanking() {
        //Excpepción si no hay jugadores en el ranking
        if(ranking.isEmpty()) {
            throw new IllegalStateException("There are no players in the ranking");
            
        }
        System.out.println("Players Ranking" + "\n" + "----------------" );
        System.out.println("#" + "\t" + "PlayersId" + "\t" + "Total Wins" + 
                            "Total Games" + "\t" + "Total Loses" + "\t" + "WinRate"
                            + "\t" + "PPG(Points per Game)" + "\t" + "Preferred Dictionary");
        System.out.println("──────────────────────────────────────────────" );
        int pos = 1;
        for (Map.Entry<Integer, Set<String>> entry : ranking.entrySet()) {
            int wins = entry.getKey();
            Set<String> players = entry.getValue();
            for (String player : players) {
                System.out.print(pos + "\t" + player + "\t" + wins);
                if(profiles.get(player).isPublic()){
                    int gamesPlayed = profiles.get(player).getGamesPlayed();

                    System.out.print("\t" + gamesPlayed + "\t" + (gamesPlayed - wins) + "\t" 
                    + computeWR(wins, gamesPlayed) + "%" + "\t" 
                    + (profiles.get(player).getScore()/gamesPlayed) + "\t" 
                    + computePD(profiles.get(player).getDictionaryUsage()) + "\n");
                } else {
                    System.out.print("\t\t\t\t" + "No more information available, this profile is private" + "\n");
                }
                pos++;
            }
        }
        System.out.println("──────────────────────" + "\n");
        System.out.println("Total Players: " + pos + "\n");
        System.out.println("──────────────────────" + "\n");
    }

    /**
     * Función que calcula el Win Rate de un jugador
     * Pre: existen un número de victorias y un número de partidas jugadas
     * Post: se retorna el Win Rate del jugador
     * @param wins
     * @param gamesPlayed
     * @return 
     */

    private double computeWR(int wins, int gamesPlayed) {
        if (gamesPlayed == 0) {
            return 0; // Así no se puede dividir entre 0, aunque no va a pasar pero por si acaso
        }
        double wr = (double) ((wins/gamesPlayed)*100);
        return Math.round(wr*100.0)/100.0; 
    }

    /**
     * Función que calcula el diccionario preferido de un jugador
     * Pre: existe un mapa del uso de diccionarios de un jugador
     * Post: se retorna el diccionario preferido del jugador
     * @param dictionaryUsage
     * @return 
     */

    private String computePD(Map<String, Integer> dictionaryUsage) {
        
        String preferredDictionary = Collections.max(dictionaryUsage.entrySet(), Map.Entry.comparingByValue()).getKey();
        return preferredDictionary;
    }

    /**
     * Función que muestra la información de un perfil
     * Pre: existe un perfil
     * Post: se muestra la información del perfil por consola
     * @param profile 
     */

    //Esta función ha quedado un poco inservible.
    public void displayProfilesInfo(Profile profile) {
        boolean publicProfile = profile.isPublic();
        System.out.println(profile.getUsername() + "\n" + "───────────────" + "\n");
        if(!publicProfile){
            System.out.println("This profile is private, you are not allowed to see this information" + "\n");
        } 
        else{
            int wins = profile.getWins();
            int gamesPlayed = profile.getGamesPlayed();
            System.out.println("Total Games: " + gamesPlayed + "\n" 
            + "Total Wins: " + wins + "\n" + 
            "Total Loses: " + (gamesPlayed - wins) + "\n" 
            + "Win Rate: " + computeWR(wins, gamesPlayed) + "%" + "\n" 
            + "PPG (Points per Game): " + (profile.getScore()/gamesPlayed) + "\n"
            + "Preferred Dictionary: " + computePD(profile.getDictionaryUsage()) + "\n");

        }
    }
    
}
