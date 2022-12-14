/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Michel
 */
public class Constants {

    public static final int NBRE_ECHANTILLONS = 3;

    public static final String ORDRE = "W:";
    public static final String ORDRE_MARCHE = "W:0";   // ordre de lancement du test
    public static final String ORDRE_ARRET = "W:1";    // ordre d'arrêt du test
    public static final String ORDRE_PAUSE = "W:2";    // ordre de mettre le test en pause

    public static final String RAZ1 = "W:RAZ:1";
    public static final String RAZ2 = "W:RAZ:2";
    public static final String RAZ3 = "W:RAZ:3";

    public static final String STOP1 = "W:STOP:1";
    public static final String STOP2 = "W:STOP:2";
    public static final String STOP3 = "W:STOP:3";

    public static final String PAUSE1 = "W:PAUSE:1";
    public static final String PAUSE2 = "W:PAUSE:2";
    public static final String PAUSE3 = "W:PAUSE:3";

    public static final String SET1 = "W:SET:1";
    public static final String SET2 = "W:SET:2";
    public static final String SET3 = "W:SET:3";

    public static final String ACTIFS_UI = "W:ACTIFS";
    public static final String TOTAL_UI = "W:TOTAL:#0:";

    public static final String FICHIER = "W:FICHIER";
    public static final String CADENCE1 = "W:CADENCE:1";
    public static final String CADENCE2 = "W:CADENCE:2";
    public static final String CADENCE3 = "W:CADENCE:3";
    public static final String CONFIG = "W:CONFIG";
    public static final String FERMETURE = "W:FERMER";
    public static final String ACQ_SEQUENCE = "W:SEQ";
    public static final String ACQ_SIMPLE = "W:ACQ";
    public static final String NEW = "W:NEW";            // Initialisation arduino pour nouvelle scéance de test. Mise à jour des paramètres de test: erreurs, compteurs...

    public static final String TOTAL = "@TOTAL";
    public static final String ACTIFS = "@ACTIFS";
    public static final String ARRETS = "@ARRETS";
    public static final String PAUSES = "@PAUSES";
    public static final String ERREUR = "@ERREURS";
    public static final String SEQUENCE = "@SEQ";
    public static final String ACQ_FERMER = "@FERMER";
    public static final String FIN = "@FIN";
    public static final String ACQUITTEMENT = "@ACQ";  // ex: utilisé pour attester de la reception de configuration
    public static final String ACQ_CAD = "@CAD";


    /*        
               PROTOCOLE RS-232
               public static final String ORDRE = "W:";                 // préfixe d'un ordre envoyer depuis l'interface
    
               public static final String ORDRE_MARCHE = "W:0";   	// ordre de lancement du test
               public static final String ORDRE_ARRET = "W:1";  	// ordre d'arrêt du test
               public static final String ORDRE_PAUSE = "W:2";   	// ordre de mettre le test en pause

               public static final String RAZ1 = "W:RAZ:1";		// remise à 0 du compteur 1
               public static final String RAZ2 = "W:RAZ:2";		// remise à 0 du compteur 2
               public static final String RAZ3 = "W:RAZ:3";		// remise à 0 du compteur 3

               public static final String STOP1 = "W:STOP:1";		// arrêt test sur échantillon 1
               public static final String STOP2 = "W:STOP:2";		// arrêt test sur échantillon 2
               public static final String STOP3 = "W:STOP:3";		// arrêt test sur échantillon 3

               public static final String PAUSE1 = "W:PAUSE:1";         // pause sur échantillon 1
               public static final String PAUSE2 = "W:PAUSE:2";         // pause sur échantillon 2
               public static final String PAUSE3 = "W:PAUSE:3";         // pause sur échantillon 3

               public static final String SET1 = "W:SET:1";		// W:SET:1:1233 fixe la valeur du compteur 1 à 1233
               public static final String SET2 = "W:SET:2";
               public static final String SET3 = "W:SET:3";
    
               public static final String ACQUITTEMENT = "W:ACQ";       // Acquittement des commande
               public static final String FICHIER = "W:FICHIER";        // demande de création du fichier de sauvegarde. W:FICHIER:<nom du fichier>:<repertoire de sauvegarde>
               public static final String CADENCE1 = "W:CADENCE:1";
               public static final String CADENCE2 = "W:CADENCE:2";
               public static final String CADENCE3 = "W:CADENCE:3";
               public static final String CONFIG = "W:CONFIG";          // W:CONFIG:0:0:0:1:2 - actif1:actif2:actif3:cadence:mode
               public static final String FERMETURE = "W:FERMER";       // Ordre d'arrêt du programme arduino

               public static final String TOTAL = "@TOTAL";             // @TOTAL:#0:111:222:333 remonte la valeur des compteurs. 111, valeur compteur 1, ...Transmis en fin de séquence
                                                                        // pour l'enregsitrement
                                                                        // @TOTAL:#1:1233:na:na indique que le total pour l'échantillon 1 est 1233 cycles.
               public static final String ACTIFS = "@ACTIFS";           // @ACTIFS:1:0:1 remonte l'état d'activation des échantillons 1=actif, 0=inactif
               public static final String ARRET = "@ARRETS";            // @ARRETS:1:0:1 notification de l'arrêt de la scéance de test les échantillons
               public static final String PAUSES = "@PAUSES";           // @PAUSES:1:0:1 notification des pause de la scéance de test sur les échantillons
               public static final String ERREUR = "@ERREURS";           // @ERREURS:#0:1:0:1 remonte les cas d'erreur sur les échantillons 1=erreur, 0=aucune erreur
                                                                        // @ERREUR:#1:na:na:na notifie une erreur sur l'échantillon 1
             
    
               public static final String SEQUENCE = "@SEQ";            // notification d'une fin de cycle, demande de sauvegarde du rapport dans fichier csv
               public static final String ACQ_FERMER = "@FERMER";       // Acquittement de l'ordre d'arrêt du programme. Transmis pour autoriser la fermeture de IHM
    
               public static final String ACQUITTEMENT = "@ACQ";       // ex: utilisé pour attester de la reception de configuration
    
    
               1- W: envoi config
               2- @: acquittement config @ACQ
                  W: transfert des compteurs
                  @: acquittement des compteurs
               3- W: ordre de démarrage
               4- @: envoi totaux
               5- W: acquittement   W:ACQ
               6- @: envoi erreur
               7- W: acquittement
               8- @: envoi actifs séquence
               9- W: acquittement   W:ACQ
               10- @: envoi pauses
               11- W: acquittement  W:ACQ
               12- @: envoi arrêts
               13- W: acquittement  W:ACQ
               14- @: enfin fin de séquence
               15- @ acquittement sequence @SEQ
    
               Fermeture
               
               1- W: Ordre de fermeture - ordre d'arrêter le cycle de test: W:FERMER
               2- @: QFERMER acquittement ordre de fermeture
               3- W: procéde aux enregistrements locaux et cloud et ferme l'appli
    
    
               
               
    
     */
}
