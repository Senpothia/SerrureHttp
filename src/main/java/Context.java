/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Michel
 */
public class Context {

    private boolean connexionRS232Active = false;    // état de la connexion RS-232
    private boolean connexionRemoteActive = false;   // Connexion au serveur distant
    private boolean withoutRemote = false;
    private Login login;
    private int cadence;

    public Context() {
        
    }

    public boolean isConnexionRS232Active() {
        return connexionRS232Active;
    }

    public void setConnexionRS232Active(boolean connexionRS232Active) {
        this.connexionRS232Active = connexionRS232Active;
    }

    public boolean isConnexionRemoteActive() {
        return connexionRemoteActive;
    }

    public void setConnexionRemoteActive(boolean connexionRemoteActive) {
        this.connexionRemoteActive = connexionRemoteActive;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public boolean isWithoutRemote() {
        return withoutRemote;
    }

    public void setWithoutRemote(boolean withoutRemote) {
        this.withoutRemote = withoutRemote;
    }

    public int getCadence() {
        return cadence;
    }

    public void setCadence(int cadence) {
        this.cadence = cadence;
    }
    
    

}
