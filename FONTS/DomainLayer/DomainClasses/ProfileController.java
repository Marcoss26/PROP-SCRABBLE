package DomainLayer.DomainClasses;
import java.util.*;

/**
 * ProfileController es una clase singleton que gestiona los perfiles de usuario
 * Permite añadir, eliminar y recuperar perfiles, así como comprobar si un perfil existe
 * @author Kai Knox
 */

public class ProfileController {
    private Map<String, Profile> profiles = new HashMap<>();
    private static ProfileController c;

    /**
     * Constructora de la clase ProfileController
     * Pre: true
     * Post: Se ha creado una instancia de la clase ProfileController y se ha inicializado el mapa de perfiles
     * Este constructor es privado para forzar el patrón singleton
     */

    private ProfileController() {
        Ranking.getInstance().setProfiles(profiles);
    }

    /**
     * Retorna el singleton de ProfileController
     * Pre: true
     * Post: Se retorna la instancia de ProfileController, si no existe se crea una nueva
     * @return
     */

    public static ProfileController getInstance() {
        if (c == null) c = new ProfileController();
        return c;
    }

    /**
     * Añade un nuevo perfil al mapa de perfiles
     * Pre: El nombre de usuario no debe existir ya en el mapa de perfiles
     * Post: Se crea un nuevo perfil y se añade al mapa de perfiles
     * @throws IllegalArgumentException si el nombre de usuario ya existe
     * @throws IllegalArgumentException si el nombre de usuario es nulo o vacío
     * @param username
     * @param password
     */

    public void addProfile(String username, String password) {
        if (profiles.containsKey(username)) {
            throw new IllegalArgumentException("Profile with this username already exists.");
        }
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty.");
        }
        Profile profile = new Profile(username, password);
        profiles.put(profile.getUsername(), profile);
    }

    /**
     * Añade un perfil existente al mapa de perfiles
     * Pre: El nombre de usuario no debe existir ya en el mapa de perfiles
     * Post: Se añade el perfil al mapa de perfiles
     * @throws IllegalArgumentException si el nombre de usuario ya existe
     * @throws IllegalArgumentException si el nombre de usuario es nulo o vacío
     * @param profile El objeto Profile a añadir
     */
    public void addProfile(Profile profile) {
        if (profiles.containsKey(profile.getUsername())) {
            throw new IllegalArgumentException("Profile with this username already exists.");
        }
        if (profile.getUsername() == null || profile.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty.");
        }
        profiles.put(profile.getUsername(), profile);
    }


    /**
     * Elimina un perfil del mapa de perfiles
     * Pre: El nombre de usuario debe existir en el mapa de perfiles
     * Post: El perfil se elimina del mapa de perfiles
     * @param username El nombre de usuario del perfil a eliminar
     */

    public void removeProfile(String username) {
        profiles.remove(username);
    }

    /**
     * Comprueba si un perfil con el nombre de usuario dado existe
     * Pre: true
     * Post: Se retorna true si el perfil existe, false en caso contrario
     * @param username El nombre de usuario del perfil a comprobar
     * @return true si el perfil existe, false en caso contrario
     */

    public boolean profileExists(String username) {
        return profiles.get(username) != null;
    }

    public boolean authenticateProfile(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Username and password cannot be null or empty.");
        }

        Profile profile = profiles.get(username);
        return profile != null && profile.authenticate(password);
    }

    /**
     * Recupera un perfil por un nombre de usuario y contraseña
     * Pre: El nombre de usuario y la contraseña no deben ser nulos o vacíos
     * Post: Se retorna el perfil si la autenticación es exitosa, null en caso contrario
     * @param username El nombre de usuario del perfil
     * @param password La contraseña del perfil
     * @return El objeto Profile si la autenticación es exitosa, null en caso contrario
     */

    public Profile getProfile(String username) {
        return profiles.get(username);
    }


    /**
     * Recupera todos los perfiles
     * Pre: true
     * Post: Se retorna un mapa con todos los perfiles
     * @return Un mapa con todos los perfiles, donde la clave es el nombre de usuario y el valor es el objeto Profile
     */
    public Map<String, Profile> getProfiles() {
        return profiles;
    }
    
    /**
     * Actualiza la contraseña del perfil de los parametros
     * Pre: El nombre de usuario y la contraseña no deben ser nulos o vacíos
     * Post: Se actualiza el perfil con la contraseña proporcionada
     * @param username El nombre de usuario del perfil a actualizar
     * @param password La nueva contraseña del perfil
     */

    public void updateProfile(String username, String oldPwd, String newPwd, boolean isPublic) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username and password cannot be null or empty.");
        }
        Profile profile = profiles.get(username);
        if (profile != null) {
            if (newPwd != null && !newPwd.isEmpty() && oldPwd != null && !oldPwd.isEmpty()) {
                profile.changePassword(oldPwd, newPwd);
            }
            profile.setVisibility(isPublic);
        } else {
            throw new IllegalArgumentException("Profile with this username does not exist.");
        }
    }
}