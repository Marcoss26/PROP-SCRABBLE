package DomainClasses;
import java.util.*;


public class Ranking {
    //Ranking es una clase Singleton
    private static Ranking instance ;
    private Set<Profile> profiles = new HashSet<>(); //Esto es un conjunto de los perfiles que están actualmente en el ranking, al no admitir repetidos,
                                                     // evitamos errores de posibles perfiles que puedan tener el mismo username.
    private Map<Integer, Set<String> > ranking = new TreeMap<>(Collections.reverseOrder()); //Mapa que contiene un numero de victorias y los perfiles que 
                                                                                            //tienen ese número de victorias, ordenado de mayor a menor.    

    
                                                    
   
    private Ranking() {
        //El constructor es privado ya que al ser singleton no se puede crear una instancia de la clase desde fuera

    }

    //Esta función permite a las demás clases acceder a la instancia de la clase Ranking
    public static Ranking getInstance() {
        if (instance == null) {
            instance = new Ranking();
        }
            return instance;
        }

    public Set<Profile> getProfiles() {
        return profiles;
    }

    public Map<Integer, Set<String>> getRanking() {
        return ranking;
    }

    public void setProfiles(Set<Profile> profiles) {
        this.profiles = profiles;
    }

    public void setRanking(Map<Integer, Set<String>> ranking) {
        this.ranking = ranking;
    }

    //Cuando acabe una partida, se llama a esta función para actualizar el ranking
    public void updateRanking(Profile profile) {
            
            int wins = profile.getWins();

            if(!profiles.contains(profile)) { //Si el perfil no está en el ranking, lo añadimos
                profiles.add(profile);
            }
            else{
                ranking.get(wins-1).remove(profile.getUsername()); //Si el perfil ya está en el ranking, lo eliminamos de la lista de perfiles con el número de victorias anterior
                                                                    //No es necesario comprobar que es su primera victoria porque si lo fuera no estaria en el conjunto de perfiles
            }
            if (ranking.containsKey(wins)) { //Si el número de victorias ya está en el ranking, añadimos el perfil a la lista de perfiles con ese número de victorias
                ranking.get(wins).add(profile.getUsername());
            } else { //Si no está, lo añadimos
                Set<String> actProfiles  = new HashSet<>();
                actProfiles.add(profile.getUsername());
                ranking.put(wins, actProfiles);
            }

    }

    public void displayRanking() {
        //Excpepción si no hay jugadores en el ranking
        if(profiles.isEmpty()) {
            throw new IllegalStateException("There are no players in the ranking");
            
        }
        System.out.println("Players Ranking" + "\n" + "----------------" + "\n");
        System.out.println("#" + "\t" + "PlayersId" + "\t" + "Total Wins" + "\n");
        System.out.println("──────────────────────" + "\n");
        int pos = 1;
        for (Map.Entry<Integer, Set<String>> entry : ranking.entrySet()) {
            int wins = entry.getKey();
            Set<String> players = entry.getValue();
            for (String player : players) {
                System.out.println(pos + "\t" + player + "\t" + wins + "\n");
                pos++;
            }
        }
        System.out.println("──────────────────────" + "\n");
        System.out.println("Total Players: " + profiles.size() + "\n");
        System.out.println("──────────────────────" + "\n");
    }

    private double computeWR(int wins, int gamesPlayed) {
        if (gamesPlayed == 0) {
            return 0; // Así no se puede dividir entre 0, aunque no va a pasar pero por si acaso
        }
        double wr = (double) ((wins/gamesPlayed)*100);
        return Math.round(wr*100.0)/100.0; 
    }

    private String computePD(Map<String, Integer> dictionaryUsage) {
        
        String preferredDictionary = Collections.max(dictionaryUsage.entrySet(), Map.Entry.comparingByValue()).getKey();
        return preferredDictionary;
    }

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
