
import com.fazecast.jSerialComm.SerialPort;
import java.awt.Color;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;

public class Interface extends javax.swing.JFrame implements Observer {

    private boolean buzzer = false;
    private boolean test_off = true;            // le test est arrêté
    private boolean test_on = false;            // le test est en cours
    private boolean test_pause = false;         // le test est en pause
    private boolean arret_valide = false;       // le test est arrêté et la séquence/cycle est terminé
    private boolean auto = true;                // le mode de marche: auto ou manuel

    private boolean[] actifs = {false, false, false};
    private boolean[] erreurs = {false, false, false};
    private long[] totaux = {0, 0, 0};

    private int baudeRate = 9600;
    private int numDatabits = 8;
    private int parity = 0;
    private int stopBits = 1;
    private int newReadTimeout = 1000;
    private int newWriteTimeout = 0;

    private boolean connexionActive = false;    // état de la connexion

    Connecteur connecteur = getConnecteur();
    Controller controller = new Controller();

    private String nomDeFichier = null;
    private File repertoire;

    private List<JLabel> compteurs = new ArrayList<>();
    private List<JLabel> statutsEchs = new ArrayList<>();
    private List<JTextField> setCompteurs = new ArrayList<>();
    private List<JRadioButton> echantillonsActifs = new ArrayList<>();
    private List<JButton> btnSets = new ArrayList<>();
    private List<JButton> btnPauses = new ArrayList<>();
    private List<JButton> btnStops = new ArrayList<>();
    private List<JButton> btnResets = new ArrayList<>();

    private List<String> ordresSETS = new ArrayList<>();
    private List<String> ordresRAZ = new ArrayList<>();
    private List<String> ordresPAUSES = new ArrayList<>();
    private List<String> ordresSTOP = new ArrayList<>();
    private List<String> ordresCadences = new ArrayList<>();

    /*
     * Creates new form Interface
     */
    public Interface() throws FileNotFoundException, IOException {

        initComponents();
        statutRemote.setBackground(Color.red);
        statutRemote.setForeground(Color.red);
        statutRemote.setOpaque(true);

        statutRs232.setBackground(Color.red);
        statutRs232.setForeground(Color.red);
        statutRs232.setOpaque(true);

        statutEch1.setBackground(Color.GRAY);
        statutEch1.setForeground(Color.GRAY);
        statutEch1.setOpaque(true);

        statutEch2.setBackground(Color.GRAY);
        statutEch2.setForeground(Color.GRAY);
        statutEch2.setOpaque(true);

        statutEch3.setBackground(Color.GRAY);
        statutEch3.setForeground(Color.GRAY);
        statutEch3.setOpaque(true);

        voyant.setBackground(Color.RED);
        voyant.setForeground(Color.RED);
        voyant.setOpaque(true);

        startWaiting(true);
        resetStateMachine();

        compteurs.add(compteur1);
        compteurs.add(compteur2);
        compteurs.add(compteur3);

        statutsEchs.add(statutEch1);
        statutsEchs.add(statutEch2);
        statutsEchs.add(statutEch3);

        setCompteurs.add(setCompteur1);
        setCompteurs.add(setCompteur2);
        setCompteurs.add(setCompteur3);

        ordresSETS.add(Constants.SET1);
        ordresSETS.add(Constants.SET2);
        ordresSETS.add(Constants.SET3);

        ordresRAZ.add(Constants.RAZ1);
        ordresRAZ.add(Constants.RAZ2);
        ordresRAZ.add(Constants.RAZ3);

        ordresPAUSES.add(Constants.PAUSE1);
        ordresPAUSES.add(Constants.PAUSE2);
        ordresPAUSES.add(Constants.PAUSE3);

        ordresSTOP.add(Constants.STOP1);
        ordresSTOP.add(Constants.STOP2);
        ordresSTOP.add(Constants.STOP3);

        ordresCadences.add(Constants.CADENCE1);
        ordresCadences.add(Constants.CADENCE2);
        ordresCadences.add(Constants.CADENCE3);

        echantillonsActifs.add(selectEch1);
        echantillonsActifs.add(selectEch2);
        echantillonsActifs.add(selectEch3);

        btnSets.add(set1);
        btnSets.add(set2);
        btnSets.add(set3);

        btnPauses.add(pause1);
        btnPauses.add(pause2);
        btnPauses.add(pause3);

        btnStops.add(arret1);
        btnStops.add(arret2);
        btnStops.add(arret3);

        btnResets.add(reset1);
        btnResets.add(reset2);
        btnResets.add(reset3);

        this.getContentPane().setBackground(new Color(128, 193, 255));

        List<JRadioButtonMenuItem> listePorts = new ArrayList<JRadioButtonMenuItem>();

        List<String> listePortString = connecteur.getListPorts();

        for (String p : listePortString) {

            JRadioButtonMenuItem m = new JRadioButtonMenuItem(p);
            groupPorts.add(m);
            m.addActionListener(new PortSupplier());
            menuPort.add(m);
        }

        Initializer initializer = new Initializer();
        Initialisation initialisation = initializer.getInit();

        List<String> remotes = initialisation.getRemotes();

        for (String r : remotes) {

            JRadioButtonMenuItem m = new JRadioButtonMenuItem(r);
            groupRemotes.add(m);
            m.addActionListener(new RemoteSupplier());
            SelectionRemote.add(m);
        }

        //  this.setDefaultCloseOperation(this.closeWindow());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        groupPorts = new javax.swing.ButtonGroup();
        groupBaud = new javax.swing.ButtonGroup();
        groupBits = new javax.swing.ButtonGroup();
        groupStop = new javax.swing.ButtonGroup();
        groupParity = new javax.swing.ButtonGroup();
        selectionFichier = new javax.swing.JFileChooser();
        groupCadence = new javax.swing.ButtonGroup();
        groupRemotes = new javax.swing.ButtonGroup();
        titre = new javax.swing.JLabel();
        compteur1 = new javax.swing.JLabel();
        selectEch1 = new javax.swing.JRadioButton();
        setCompteur1 = new javax.swing.JTextField();
        set1 = new javax.swing.JButton();
        reset1 = new javax.swing.JButton();
        pause1 = new javax.swing.JButton();
        reset2 = new javax.swing.JButton();
        compteur2 = new javax.swing.JLabel();
        pause2 = new javax.swing.JButton();
        selectEch2 = new javax.swing.JRadioButton();
        setCompteur2 = new javax.swing.JTextField();
        set2 = new javax.swing.JButton();
        reset3 = new javax.swing.JButton();
        compteur3 = new javax.swing.JLabel();
        pause3 = new javax.swing.JButton();
        selectEch3 = new javax.swing.JRadioButton();
        setCompteur3 = new javax.swing.JTextField();
        set3 = new javax.swing.JButton();
        voyant = new javax.swing.JLabel();
        version = new javax.swing.JLabel();
        console = new javax.swing.JTextField();
        statutRs232 = new javax.swing.JLabel();
        statutRemote = new javax.swing.JLabel();
        RS232 = new javax.swing.JLabel();
        Remote = new javax.swing.JLabel();
        arret1 = new javax.swing.JButton();
        arret2 = new javax.swing.JButton();
        arret3 = new javax.swing.JButton();
        start = new javax.swing.JButton();
        stop = new javax.swing.JButton();
        pause = new javax.swing.JButton();
        statutEch1 = new javax.swing.JLabel();
        statutEch2 = new javax.swing.JLabel();
        statutEch3 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        MenuFichier = new javax.swing.JMenu();
        menuNouveau = new javax.swing.JMenuItem();
        menuModifier = new javax.swing.JMenuItem();
        menuSauvegardes = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        menuQuitter = new javax.swing.JMenuItem();
        menuConnexion = new javax.swing.JMenu();
        menuPort = new javax.swing.JMenu();
        menuBaud = new javax.swing.JMenu();
        baud9600 = new javax.swing.JRadioButtonMenuItem();
        baud19200 = new javax.swing.JRadioButtonMenuItem();
        baud38400 = new javax.swing.JRadioButtonMenuItem();
        baud115200 = new javax.swing.JRadioButtonMenuItem();
        menuBits = new javax.swing.JMenu();
        bits6 = new javax.swing.JRadioButtonMenuItem();
        bits7 = new javax.swing.JRadioButtonMenuItem();
        bits8 = new javax.swing.JRadioButtonMenuItem();
        bits9 = new javax.swing.JRadioButtonMenuItem();
        menuStop = new javax.swing.JMenu();
        stop1 = new javax.swing.JRadioButtonMenuItem();
        stop2 = new javax.swing.JRadioButtonMenuItem();
        menuParity = new javax.swing.JMenu();
        parityNone = new javax.swing.JRadioButtonMenuItem();
        parityOdd = new javax.swing.JRadioButtonMenuItem();
        parityEven = new javax.swing.JRadioButtonMenuItem();
        btnConnexion = new javax.swing.JMenuItem();
        btnDeconnexion = new javax.swing.JMenuItem();
        menuRemote = new javax.swing.JMenu();
        SelectionRemote = new javax.swing.JMenu();
        changeRemote = new javax.swing.JMenu();
        addRemote = new javax.swing.JMenu();
        deleteRemote = new javax.swing.JMenu();
        connectRemote = new javax.swing.JMenu();
        disconnectRemote = new javax.swing.JMenu();
        menuConfig = new javax.swing.JMenu();
        cadence = new javax.swing.JMenu();
        cad_2_min = new javax.swing.JRadioButtonMenuItem();
        cad_1_par_2mins = new javax.swing.JRadioButtonMenuItem();
        cad_1_par_5mins = new javax.swing.JRadioButtonMenuItem();
        menuAuto = new javax.swing.JMenuItem();
        menuManuel = new javax.swing.JMenuItem();

        selectionFichier.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Test d'endurance DX200I");
        setBackground(new java.awt.Color(153, 153, 255));

        titre.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        titre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titre.setText("TEST DX200I");
        titre.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        compteur1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        compteur1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        compteur1.setText("0");

        selectEch1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        selectEch1.setText("Echantillon 1");

        setCompteur1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        set1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        set1.setForeground(new java.awt.Color(255, 51, 0));
        set1.setText("Set");
        set1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                set1ActionPerformed(evt);
            }
        });

        reset1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        reset1.setForeground(new java.awt.Color(255, 51, 0));
        reset1.setText("Reset");
        reset1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reset1ActionPerformed(evt);
            }
        });

        pause1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        pause1.setForeground(new java.awt.Color(255, 102, 51));
        pause1.setText("Pause");
        pause1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pause1ActionPerformed(evt);
            }
        });

        reset2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        reset2.setForeground(new java.awt.Color(255, 51, 0));
        reset2.setText("Reset");
        reset2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reset2ActionPerformed(evt);
            }
        });

        compteur2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        compteur2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        compteur2.setText("0");

        pause2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        pause2.setForeground(new java.awt.Color(255, 102, 51));
        pause2.setText("Pause");
        pause2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pause2ActionPerformed(evt);
            }
        });

        selectEch2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        selectEch2.setText("Echantillon 2");

        setCompteur2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        set2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        set2.setForeground(new java.awt.Color(255, 51, 0));
        set2.setText("Set");
        set2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                set2ActionPerformed(evt);
            }
        });

        reset3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        reset3.setForeground(new java.awt.Color(255, 51, 0));
        reset3.setText("Reset");
        reset3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reset3ActionPerformed(evt);
            }
        });

        compteur3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        compteur3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        compteur3.setText("0");

        pause3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        pause3.setForeground(new java.awt.Color(255, 102, 51));
        pause3.setText("Pause");
        pause3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pause3ActionPerformed(evt);
            }
        });

        selectEch3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        selectEch3.setText("Echantillon 3");

        setCompteur3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        set3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        set3.setForeground(new java.awt.Color(255, 51, 0));
        set3.setText("Set");
        set3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                set3ActionPerformed(evt);
            }
        });

        voyant.setBackground(new java.awt.Color(255, 51, 0));
        voyant.setForeground(new java.awt.Color(255, 0, 0));
        voyant.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        voyant.setText("Voyant");

        version.setText("V1.0");

        console.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        console.setForeground(new java.awt.Color(255, 0, 0));
        console.setText("En attente de connexion!");

        statutRs232.setBackground(new java.awt.Color(0, 153, 0));
        statutRs232.setForeground(new java.awt.Color(0, 153, 51));
        statutRs232.setText("Statut");

        statutRemote.setBackground(new java.awt.Color(255, 51, 0));
        statutRemote.setForeground(new java.awt.Color(255, 51, 0));
        statutRemote.setText("Statut");

        RS232.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        RS232.setText("RS232");

        Remote.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        Remote.setText("REMOTE");

        arret1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        arret1.setForeground(new java.awt.Color(255, 0, 0));
        arret1.setText("STOP");
        arret1.setToolTipText("");
        arret1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                arret1ActionPerformed(evt);
            }
        });

        arret2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        arret2.setForeground(new java.awt.Color(255, 0, 0));
        arret2.setText("STOP");
        arret2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                arret2ActionPerformed(evt);
            }
        });

        arret3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        arret3.setForeground(new java.awt.Color(255, 0, 0));
        arret3.setText("STOP");
        arret3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                arret3ActionPerformed(evt);
            }
        });

        start.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        start.setForeground(new java.awt.Color(0, 102, 0));
        start.setText("START");
        start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startActionPerformed(evt);
            }
        });

        stop.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        stop.setForeground(new java.awt.Color(255, 51, 0));
        stop.setText("STOP");
        stop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopActionPerformed(evt);
            }
        });

        pause.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        pause.setForeground(new java.awt.Color(255, 102, 0));
        pause.setText("PAUSE");
        pause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pauseActionPerformed(evt);
            }
        });

        statutEch1.setBackground(new java.awt.Color(0, 153, 0));
        statutEch1.setForeground(new java.awt.Color(0, 153, 51));
        statutEch1.setText("0000");

        statutEch2.setBackground(new java.awt.Color(0, 153, 0));
        statutEch2.setForeground(new java.awt.Color(0, 153, 51));
        statutEch2.setText("0000");

        statutEch3.setBackground(new java.awt.Color(0, 153, 0));
        statutEch3.setForeground(new java.awt.Color(0, 153, 51));
        statutEch3.setText("0000");

        MenuFichier.setText("Fichier");
        MenuFichier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuFichierActionPerformed(evt);
            }
        });

        menuNouveau.setText("Nouveau");
        menuNouveau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuNouveauActionPerformed(evt);
            }
        });
        MenuFichier.add(menuNouveau);

        menuModifier.setLabel("Modifier");
        MenuFichier.add(menuModifier);

        menuSauvegardes.setLabel("Sauvegardes");
        menuSauvegardes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSauvegardesActionPerformed(evt);
            }
        });
        MenuFichier.add(menuSauvegardes);
        MenuFichier.add(jSeparator1);

        menuQuitter.setText("Quitter");
        menuQuitter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuQuitterActionPerformed(evt);
            }
        });
        MenuFichier.add(menuQuitter);

        jMenuBar1.add(MenuFichier);

        menuConnexion.setText("Connexion");
        menuConnexion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuConnexionActionPerformed(evt);
            }
        });

        menuPort.setText("Port");
        menuConnexion.add(menuPort);

        menuBaud.setText("Baud");

        groupBaud.add(baud9600);
        baud9600.setSelected(true);
        baud9600.setText("9600");
        baud9600.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                baud9600StateChanged(evt);
            }
        });
        menuBaud.add(baud9600);

        groupBaud.add(baud19200);
        baud19200.setText("19200");
        baud19200.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                baud19200StateChanged(evt);
            }
        });
        menuBaud.add(baud19200);

        groupBaud.add(baud38400);
        baud38400.setText("38400");
        baud38400.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                baud38400StateChanged(evt);
            }
        });
        menuBaud.add(baud38400);

        groupBaud.add(baud115200);
        baud115200.setText("115200");
        baud115200.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                baud115200StateChanged(evt);
            }
        });
        menuBaud.add(baud115200);

        menuConnexion.add(menuBaud);

        menuBits.setText("Bits");

        groupBits.add(bits6);
        bits6.setText("6");
        bits6.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                bits6StateChanged(evt);
            }
        });
        menuBits.add(bits6);

        groupBits.add(bits7);
        bits7.setText("7");
        bits7.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                bits7StateChanged(evt);
            }
        });
        menuBits.add(bits7);

        groupBits.add(bits8);
        bits8.setSelected(true);
        bits8.setText("8");
        bits8.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                bits8StateChanged(evt);
            }
        });
        menuBits.add(bits8);

        groupBits.add(bits9);
        bits9.setText("9");
        bits9.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                bits9StateChanged(evt);
            }
        });
        menuBits.add(bits9);

        menuConnexion.add(menuBits);

        menuStop.setText("Stop");

        groupStop.add(stop1);
        stop1.setSelected(true);
        stop1.setText("1");
        stop1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                stop1StateChanged(evt);
            }
        });
        menuStop.add(stop1);

        groupStop.add(stop2);
        stop2.setText("2");
        stop2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                stop2StateChanged(evt);
            }
        });
        menuStop.add(stop2);

        menuConnexion.add(menuStop);

        menuParity.setText("Parité");

        groupParity.add(parityNone);
        parityNone.setSelected(true);
        parityNone.setText("None");
        parityNone.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                parityNoneStateChanged(evt);
            }
        });
        menuParity.add(parityNone);

        groupParity.add(parityOdd);
        parityOdd.setText("Paire");
        parityOdd.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                parityOddStateChanged(evt);
            }
        });
        menuParity.add(parityOdd);

        groupParity.add(parityEven);
        parityEven.setText("Impaire");
        parityEven.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                parityEvenStateChanged(evt);
            }
        });
        menuParity.add(parityEven);

        menuConnexion.add(menuParity);

        btnConnexion.setText("Connexion");
        btnConnexion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConnexionActionPerformed(evt);
            }
        });
        menuConnexion.add(btnConnexion);

        btnDeconnexion.setText("Déconnexion");
        btnDeconnexion.setEnabled(false);
        btnDeconnexion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeconnexionActionPerformed(evt);
            }
        });
        menuConnexion.add(btnDeconnexion);

        jMenuBar1.add(menuConnexion);

        menuRemote.setText("Remote");

        SelectionRemote.setText("Choisir");
        menuRemote.add(SelectionRemote);

        changeRemote.setText("Changer");
        menuRemote.add(changeRemote);

        addRemote.setText("Ajouter");
        menuRemote.add(addRemote);

        deleteRemote.setText("Supprimer");
        menuRemote.add(deleteRemote);

        connectRemote.setText("Connexion");
        menuRemote.add(connectRemote);

        disconnectRemote.setText("Déconnecter");
        menuRemote.add(disconnectRemote);

        jMenuBar1.add(menuRemote);

        menuConfig.setText("Configuration");
        menuConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuConfigActionPerformed(evt);
            }
        });

        cadence.setText("Cadence");

        groupCadence.add(cad_2_min);
        cad_2_min.setSelected(true);
        cad_2_min.setText("2x1min");
        cad_2_min.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cad_2_minActionPerformed(evt);
            }
        });
        cadence.add(cad_2_min);

        groupCadence.add(cad_1_par_2mins);
        cad_1_par_2mins.setText("1x2mins");
        cad_1_par_2mins.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cad_1_par_2minsActionPerformed(evt);
            }
        });
        cadence.add(cad_1_par_2mins);

        groupCadence.add(cad_1_par_5mins);
        cad_1_par_5mins.setText("1x5mins");
        cad_1_par_5mins.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cad_1_par_5minsActionPerformed(evt);
            }
        });
        cadence.add(cad_1_par_5mins);

        menuConfig.add(cadence);

        menuAuto.setText("Auto");
        menuAuto.setEnabled(false);
        menuAuto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAutoActionPerformed(evt);
            }
        });
        menuConfig.add(menuAuto);

        menuManuel.setText("Manuel");
        menuManuel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuManuelActionPerformed(evt);
            }
        });
        menuConfig.add(menuManuel);

        jMenuBar1.add(menuConfig);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(statutRs232, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(RS232))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(statutRemote)
                                .addGap(18, 18, 18)
                                .addComponent(Remote)))
                        .addGap(283, 283, 283)
                        .addComponent(titre, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(594, 594, 594)
                        .addComponent(voyant, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(447, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(stop)
                                .addGap(27, 27, 27)
                                .addComponent(start)
                                .addGap(18, 18, 18)
                                .addComponent(pause)
                                .addGap(188, 188, 188))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(statutEch1, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(statutEch2, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(statutEch3, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(selectEch1)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(compteur1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(50, 50, 50)
                                            .addComponent(setCompteur1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(set1)
                                            .addGap(34, 34, 34)
                                            .addComponent(reset1)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(pause1))
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(selectEch3)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addComponent(compteur3, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(selectEch2)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addComponent(compteur2, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGap(50, 50, 50)
                                            .addComponent(setCompteur2, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(set2)
                                            .addGap(34, 34, 34)
                                            .addComponent(reset2)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(pause2)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(setCompteur3, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(set3)
                                        .addGap(34, 34, 34)
                                        .addComponent(reset3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(pause3)))))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(arret1)
                            .addComponent(arret2)
                            .addComponent(arret3))
                        .addGap(162, 162, 162))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(version)
                        .addGap(36, 36, 36))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(console, javax.swing.GroupLayout.PREFERRED_SIZE, 874, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(205, 205, 205))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(statutRs232)
                            .addComponent(RS232))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(statutRemote)
                            .addComponent(Remote)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(titre)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(selectEch1)
                            .addComponent(compteur1)
                            .addComponent(setCompteur1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(set1)
                            .addComponent(reset1)
                            .addComponent(pause1)
                            .addComponent(arret1)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(statutEch1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)))
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectEch2)
                    .addComponent(compteur2)
                    .addComponent(setCompteur2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(set2)
                    .addComponent(reset2)
                    .addComponent(pause2)
                    .addComponent(arret2)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(statutEch2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectEch3)
                    .addComponent(compteur3)
                    .addComponent(setCompteur3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(set3)
                    .addComponent(reset3)
                    .addComponent(pause3)
                    .addComponent(arret3)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(statutEch3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(voyant, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(start)
                    .addComponent(stop)
                    .addComponent(pause))
                .addGap(33, 33, 33)
                .addComponent(console, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(version)
                .addGap(16, 16, 16))
        );

        arret1.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuNouveauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuNouveauActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuNouveauActionPerformed

    private void arret1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_arret1ActionPerformed

        envoyerOrdreStop(1);
    }//GEN-LAST:event_arret1ActionPerformed

    private void btnConnexionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConnexionActionPerformed

        int i = connecteur.makeConnection(Connecteur.portName, baudeRate, numDatabits, parity, stopBits);
        if (i == 99) {

            console.setForeground(Color.BLUE);
            console.setText("Connexion réussie");
            setStatusRS232(true);
            btnConnexion.setEnabled(false);
            btnDeconnexion.setEnabled(true);
            connexionActive = true;

        } else {

            console.setForeground(Color.red);
            console.setText("Tentative de connexion échouée");
            setStatusRS232(false);

        }


    }//GEN-LAST:event_btnConnexionActionPerformed

    private void btnDeconnexionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeconnexionActionPerformed

        int i = connecteur.disconnect();
        if (i == 0) {
            console.setForeground(Color.BLUE);
            console.setText("Déconnexion réussie");
            setStatusRS232(false);
            btnConnexion.setEnabled(true);
            btnDeconnexion.setEnabled(false);
            connexionActive = false;

        }
    }//GEN-LAST:event_btnDeconnexionActionPerformed

    private void baud9600StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_baud9600StateChanged

        if (baud9600.isSelected()) {

            baudeRate = 9600;
        }
    }//GEN-LAST:event_baud9600StateChanged

    private void baud19200StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_baud19200StateChanged

        if (baud19200.isSelected()) {

            baudeRate = 19200;
        }
    }//GEN-LAST:event_baud19200StateChanged

    private void baud38400StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_baud38400StateChanged

        if (baud38400.isSelected()) {

            baudeRate = 38400;
        }
    }//GEN-LAST:event_baud38400StateChanged

    private void baud115200StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_baud115200StateChanged

        if (baud115200.isSelected()) {

            baudeRate = 115200;
        }
    }//GEN-LAST:event_baud115200StateChanged

    private void bits6StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_bits6StateChanged

        if (bits6.isSelected()) {

            numDatabits = 6;
        }
    }//GEN-LAST:event_bits6StateChanged

    private void bits7StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_bits7StateChanged

        if (bits7.isSelected()) {

            numDatabits = 7;
        }
    }//GEN-LAST:event_bits7StateChanged

    private void bits8StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_bits8StateChanged

        if (bits8.isSelected()) {

            numDatabits = 8;
        }
    }//GEN-LAST:event_bits8StateChanged

    private void bits9StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_bits9StateChanged

        if (bits9.isSelected()) {

            numDatabits = 9;
        }
    }//GEN-LAST:event_bits9StateChanged

    private void stop1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_stop1StateChanged

        if (stop1.isSelected()) {

            stopBits = 1;
        }
    }//GEN-LAST:event_stop1StateChanged

    private void stop2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_stop2StateChanged

        if (stop2.isSelected()) {

            stopBits = 2;
        }
    }//GEN-LAST:event_stop2StateChanged

    private void parityNoneStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_parityNoneStateChanged

        if (parityNone.isSelected()) {

            parity = 0;
        }
    }//GEN-LAST:event_parityNoneStateChanged

    private void parityOddStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_parityOddStateChanged

        if (parityOdd.isSelected()) {

            parity = 1;
        }
    }//GEN-LAST:event_parityOddStateChanged

    private void parityEvenStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_parityEvenStateChanged

        if (parityEven.isSelected()) {

            stopBits = 2;
        }
    }//GEN-LAST:event_parityEvenStateChanged

    private void menuConnexionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuConnexionActionPerformed


    }//GEN-LAST:event_menuConnexionActionPerformed

    private void stopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopActionPerformed

        if (auto) {

            stopRequested();
            connecteur.envoyerData(Constants.ORDRE_ARRET);

        } else {
        }


    }//GEN-LAST:event_stopActionPerformed

    private void startActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startActionPerformed

        if (!connexionActive) {

            montrerError("Vous devez activer la connexion série!", "Défaut de connexion");
            return;
        }

        if (auto) {

            int i = envoyerConfiguration();
            console.setForeground(Color.RED);
            console.setText("En attente de démarrage!");
            if (i == -1) {
                return;
            } else {
            }

        }


    }//GEN-LAST:event_startActionPerformed

    private void pauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pauseActionPerformed

        if (auto) {
            pauseRequested();
            connecteur.envoyerData(Constants.ORDRE_PAUSE);
        } else {

        }

    }//GEN-LAST:event_pauseActionPerformed

    private void menuManuelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuManuelActionPerformed

        auto = false;
        start.setEnabled(false);
        start.setForeground(Color.GRAY);
        stop.setText("Effacer");
        pause.setText("Envoyer");
        pause.setForeground(new Color(0, 102, 0));
        stop.setEnabled(true);
        stop.setForeground(Color.RED);
        pause.setEnabled(true);
        menuAuto.setEnabled(true);
        menuManuel.setEnabled(false);
        for (int i = 0; i < 3; i++) {

            btnStops.get(i).setText("Activer");
        }

        for (int i = 0; i < 3; i++) {

            btnSets.get(i).setEnabled(false);
        }

        for (int i = 0; i < 3; i++) {

            btnPauses.get(i).setEnabled(false);
        }

        for (int i = 0; i < 3; i++) {

            btnResets.get(i).setEnabled(false);
        }

        console.setText("");
    }//GEN-LAST:event_menuManuelActionPerformed

    private void menuConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuConfigActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuConfigActionPerformed

    private void menuAutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAutoActionPerformed

        auto = true;
        stop.setText("STOP");
        pause.setText("PAUSE");
        menuAuto.setEnabled(false);
        menuManuel.setEnabled(true);
        for (int i = 0; i < 3; i++) {

            btnStops.get(i).setText("STOP");
        }

        for (int i = 0; i < 3; i++) {

            btnSets.get(i).setEnabled(false);
        }

        for (int i = 0; i < 3; i++) {

            btnPauses.get(i).setEnabled(false);
        }

        for (int i = 0; i < 3; i++) {

            btnResets.get(i).setEnabled(false);
        }
        startWaiting(true);

    }//GEN-LAST:event_menuAutoActionPerformed

    private void menuSauvegardesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSauvegardesActionPerformed

        nomDeFichier = JOptionPane.showInputDialog("Entrez un nom pour le fichier de sauvegarde!");
        if (nomDeFichier == null || nomDeFichier.equals("")) {
            montrerError("Vous devez indiquer un nom de fichier valide!", "Nom de fichier incorrect");
            return;
        }
        int showOpenDialog = selectionFichier.showOpenDialog(this);
        repertoire = selectionFichier.getSelectedFile();
        nomDeFichier = repertoire + "\\" + nomDeFichier + ".csv";
        console.setForeground(Color.red);
        console.setText("Les résultats seront sauvegardés à l'emplacement: " + nomDeFichier);
        int i = controller.creationFichier(nomDeFichier);
        if (i == -1) {

            montrerError("Erreur à la création du fichier de sauvegarde!", "Echec création fichier");
        }
    }//GEN-LAST:event_menuSauvegardesActionPerformed

    private void MenuFichierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuFichierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MenuFichierActionPerformed

    private void set1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_set1ActionPerformed

        envoyerInitCompteur(1);

    }//GEN-LAST:event_set1ActionPerformed

    private void set2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_set2ActionPerformed

        envoyerInitCompteur(2);
    }//GEN-LAST:event_set2ActionPerformed

    private void set3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_set3ActionPerformed
        envoyerInitCompteur(3);
    }//GEN-LAST:event_set3ActionPerformed

    private void reset1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reset1ActionPerformed

        envoyerResetCompteur(1);
    }//GEN-LAST:event_reset1ActionPerformed

    private void reset2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reset2ActionPerformed

        envoyerResetCompteur(2);
    }//GEN-LAST:event_reset2ActionPerformed

    private void reset3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reset3ActionPerformed

        envoyerResetCompteur(3);
    }//GEN-LAST:event_reset3ActionPerformed

    private void pause1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pause1ActionPerformed

        envoyerOrdrePause(1);

    }//GEN-LAST:event_pause1ActionPerformed

    private void pause2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pause2ActionPerformed

        envoyerOrdrePause(2);

    }//GEN-LAST:event_pause2ActionPerformed

    private void pause3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pause3ActionPerformed

        envoyerOrdrePause(3);
    }//GEN-LAST:event_pause3ActionPerformed

    private void arret2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_arret2ActionPerformed

        envoyerOrdreStop(2);
    }//GEN-LAST:event_arret2ActionPerformed

    private void arret3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_arret3ActionPerformed

        envoyerOrdreStop(3);
    }//GEN-LAST:event_arret3ActionPerformed

    private void cad_2_minActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cad_2_minActionPerformed

        envoyerOdreCadence(1);
    }//GEN-LAST:event_cad_2_minActionPerformed

    private void cad_1_par_2minsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cad_1_par_2minsActionPerformed

        envoyerOdreCadence(2);
    }//GEN-LAST:event_cad_1_par_2minsActionPerformed

    private void cad_1_par_5minsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cad_1_par_5minsActionPerformed
        envoyerOdreCadence(3);
    }//GEN-LAST:event_cad_1_par_5minsActionPerformed

    private void menuQuitterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuQuitterActionPerformed

        int result = JOptionPane.showConfirmDialog(this, "Voulez-vous arrêter le test?", "Demande de fermeture",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (result == JOptionPane.YES_OPTION) {

            System.out.println("Fermeture du programme");
            fermeture();

        } else if (result == JOptionPane.NO_OPTION) {
            return;
        }


    }//GEN-LAST:event_menuQuitterActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Interface().setVisible(true);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu MenuFichier;
    private javax.swing.JLabel RS232;
    private javax.swing.JLabel Remote;
    private javax.swing.JMenu SelectionRemote;
    private javax.swing.JMenu addRemote;
    private javax.swing.JButton arret1;
    private javax.swing.JButton arret2;
    private javax.swing.JButton arret3;
    private javax.swing.JRadioButtonMenuItem baud115200;
    private javax.swing.JRadioButtonMenuItem baud19200;
    private javax.swing.JRadioButtonMenuItem baud38400;
    private javax.swing.JRadioButtonMenuItem baud9600;
    private javax.swing.JRadioButtonMenuItem bits6;
    private javax.swing.JRadioButtonMenuItem bits7;
    private javax.swing.JRadioButtonMenuItem bits8;
    private javax.swing.JRadioButtonMenuItem bits9;
    private javax.swing.JMenuItem btnConnexion;
    private javax.swing.JMenuItem btnDeconnexion;
    private javax.swing.JRadioButtonMenuItem cad_1_par_2mins;
    private javax.swing.JRadioButtonMenuItem cad_1_par_5mins;
    private javax.swing.JRadioButtonMenuItem cad_2_min;
    private javax.swing.JMenu cadence;
    private javax.swing.JMenu changeRemote;
    private javax.swing.JLabel compteur1;
    private javax.swing.JLabel compteur2;
    private javax.swing.JLabel compteur3;
    private javax.swing.JMenu connectRemote;
    private javax.swing.JTextField console;
    private javax.swing.JMenu deleteRemote;
    private javax.swing.JMenu disconnectRemote;
    private javax.swing.ButtonGroup groupBaud;
    private javax.swing.ButtonGroup groupBits;
    private javax.swing.ButtonGroup groupCadence;
    private javax.swing.ButtonGroup groupParity;
    private javax.swing.ButtonGroup groupPorts;
    private javax.swing.ButtonGroup groupRemotes;
    private javax.swing.ButtonGroup groupStop;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JMenuItem menuAuto;
    private javax.swing.JMenu menuBaud;
    private javax.swing.JMenu menuBits;
    private javax.swing.JMenu menuConfig;
    private javax.swing.JMenu menuConnexion;
    private javax.swing.JMenuItem menuManuel;
    private javax.swing.JMenuItem menuModifier;
    private javax.swing.JMenuItem menuNouveau;
    private javax.swing.JMenu menuParity;
    private javax.swing.JMenu menuPort;
    private javax.swing.JMenuItem menuQuitter;
    private javax.swing.JMenu menuRemote;
    private javax.swing.JMenuItem menuSauvegardes;
    private javax.swing.JMenu menuStop;
    private javax.swing.JRadioButtonMenuItem parityEven;
    private javax.swing.JRadioButtonMenuItem parityNone;
    private javax.swing.JRadioButtonMenuItem parityOdd;
    private javax.swing.JButton pause;
    private javax.swing.JButton pause1;
    private javax.swing.JButton pause2;
    private javax.swing.JButton pause3;
    private javax.swing.JButton reset1;
    private javax.swing.JButton reset2;
    private javax.swing.JButton reset3;
    private javax.swing.JRadioButton selectEch1;
    private javax.swing.JRadioButton selectEch2;
    private javax.swing.JRadioButton selectEch3;
    private javax.swing.JFileChooser selectionFichier;
    private javax.swing.JButton set1;
    private javax.swing.JButton set2;
    private javax.swing.JButton set3;
    private javax.swing.JTextField setCompteur1;
    private javax.swing.JTextField setCompteur2;
    private javax.swing.JTextField setCompteur3;
    private javax.swing.JButton start;
    private javax.swing.JLabel statutEch1;
    private javax.swing.JLabel statutEch2;
    private javax.swing.JLabel statutEch3;
    private javax.swing.JLabel statutRemote;
    private javax.swing.JLabel statutRs232;
    private javax.swing.JButton stop;
    private javax.swing.JRadioButtonMenuItem stop1;
    private javax.swing.JRadioButtonMenuItem stop2;
    private javax.swing.JLabel titre;
    private javax.swing.JLabel version;
    private javax.swing.JLabel voyant;
    // End of variables declaration//GEN-END:variables

    private void setStatusRS232(boolean statut) {

        if (statut) {

            statutRs232.setForeground(Color.GREEN);
            statutRs232.setBackground(Color.GREEN);
        } else {
            statutRs232.setForeground(Color.RED);
            statutRs232.setBackground(Color.RED);
        }

    }

    public void montrerError(String message, String titre) {
        JOptionPane.showMessageDialog(this, message, titre, JOptionPane.ERROR_MESSAGE);
    }

    private Connecteur getConnecteur() {

        if (this.connecteur == null) {
            this.connecteur = new Connecteur();
            this.connecteur.addObserver(this);
        }
        return this.connecteur;

    }

    @Override
    public void update(Observable o, Object arg) {

        String inputLine = (String) arg;
        console.setText(inputLine);
        Rapport rapport = controller.parser(inputLine);
        traiterRapport(rapport);                    // Analyse du rapport pour mise à jour de l'interface
        console.setForeground(rapport.color);
        console.setText(rapport.getLog());

    }

    private void traiterRapport(Rapport rapport) {  // Gestion des affichages en fonction des résultats remontés par Arduino

        Color color = null;
        for (int i = 0; i < Constants.NBRE_ECHANTILLONS; i++) {

            boolean erreur = rapport.getErreurs()[i];
            boolean actif = rapport.getActifs()[i];
            boolean pause = rapport.getPauses()[i];
            boolean arret = rapport.getArrets()[i];

            String total = Long.toString(rapport.getTotaux()[i]);

            if (erreur) {

                color = Color.RED;

            } else {

                color = Color.BLUE;
            }

            if (!actif) {

                color = Color.GRAY;
            }

            if (pause) {

                color = Color.ORANGE;
            }

            if (arret) {

                color = Color.YELLOW;
            }

            JLabel lab1 = compteurs.get(i);
            lab1.setForeground(color);
            lab1.setText(total);

            JLabel lab2 = statutsEchs.get(i);
            lab2.setForeground(color);
            lab2.setBackground(color);

        }

        if (rapport.acquittement) {

            startRequested();
            connecteur.envoyerData(Constants.ORDRE_MARCHE);
        }

        if (rapport.fermeture) {

            System.exit(0);
        }

    }

    private void startWaiting(boolean activation) {

        if (activation) {

            start.setEnabled(true);
            start.setForeground(new Color(0, 102, 0));
            pause.setEnabled(false);
            pause.setForeground(Color.GRAY);
            stop.setEnabled(false);
            stop.setForeground(Color.GRAY);

        } else {

            start.setEnabled(false);
            start.setForeground(Color.GRAY);
            pause.setEnabled(true);
            pause.setForeground(Color.ORANGE);
            stop.setEnabled(true);
            stop.setForeground(Color.RED);

        }

    }

    private void startRequested() {

        test_off = false;
        test_on = true;
        test_pause = false;
        voyant.setForeground(Color.GREEN);
        voyant.setBackground(Color.GREEN);
        pause.setEnabled(true);
        pause.setForeground(new Color(255, 102, 0));
        start.setEnabled(false);
        start.setForeground(Color.GRAY);
        stop.setEnabled(true);
        stop.setForeground(Color.RED);
    }

    private void pauseRequested() {

        if (auto) {
            test_off = false;
            test_on = true;
            test_pause = true;
            voyant.setForeground(Color.ORANGE);
            voyant.setBackground(Color.ORANGE);
            pause.setEnabled(false);
            pause.setForeground(Color.GRAY);
            start.setEnabled(true);
            start.setForeground(new Color(0, 102, 0));
            stop.setEnabled(true);
            stop.setForeground(Color.RED);

        } else {

        }

    }

    private void stopRequested() {

        if (auto) {

            test_off = true;
            test_on = false;
            test_pause = false;
            voyant.setForeground(Color.RED);
            voyant.setBackground(Color.RED);
            pause.setEnabled(false);
            pause.setForeground(Color.GRAY);
            start.setEnabled(true);
            start.setForeground(new Color(0, 102, 0));
            stop.setEnabled(false);
            stop.setForeground(Color.GRAY);

        } else {

        }

    }

    private void resetStateMachine() {

        test_off = true;
        test_on = false;
        test_pause = false;
        arret_valide = false;

    }

    private void activationVoyant(boolean activation) {

        if (auto) {

            if (activation) {

                voyant.setForeground(Color.GREEN);
                voyant.setBackground(Color.GREEN);

            } else {

                voyant.setForeground(Color.RED);
                voyant.setBackground(Color.RED);
            }

        } else {

        }

    }

    public boolean[] getActifs() {
        return actifs;
    }

    public void setActifs(boolean[] actifs) {
        this.actifs = actifs;
    }

    public long[] getTotaux() {
        return totaux;
    }

    public void setTotaux(long[] totaux) {
        this.totaux = totaux;
    }

    public boolean[] getErreurs() {
        return erreurs;
    }

    public void setErreurs(boolean[] erreurs) {
        this.erreurs = erreurs;
    }

    public void setTotal1(long total1) {
        this.totaux[0] = total1;
    }

    public void setTotal2(long total2) {
        this.totaux[1] = total2;
    }

    public void setTotal3(long total3) {
        this.totaux[2] = total3;
    }

    public long getTotal1() {
        return totaux[0];
    }

    public long getTotal2() {
        return totaux[1];
    }

    public long getTotal3() {
        return totaux[2];
    }

    public boolean getActif1() {
        return actifs[0];
    }

    public boolean getActif2() {
        return actifs[1];
    }

    public boolean getActif3() {
        return actifs[2];
    }

    public void setActif1(boolean actifs1) {
        this.actifs[0] = actifs1;
    }

    public void setActif2(boolean actifs2) {
        this.actifs[1] = actifs2;
    }

    public void setActif3(boolean actifs3) {
        this.actifs[2] = actifs3;
    }

    public boolean getErreur1() {
        return erreurs[0];
    }

    public boolean getErreur2() {
        return erreurs[1];
    }

    public boolean getErreur3() {
        return erreurs[2];
    }

    public void setErreur1(boolean erreur1) {
        this.erreurs[0] = erreur1;
    }

    public void setErreur2(boolean erreur2) {
        this.erreurs[1] = erreur2;
    }

    public void setErreur3(boolean erreur3) {
        this.erreurs[2] = erreur3;
    }

    private void envoyerInitCompteur(int i) {

        try {
            String compteur = setCompteurs.get(i - 1).getText();
            try {
                Long.parseLong(compteur);
                String ordre = ordresSETS.get(i - 1) + ":" + compteur;
                System.out.println("Ordre:" + ordre);
                connecteur.envoyerData(ordre);

            } catch (Exception e) {

                montrerError("Vous devez indiquez une valeur numérique!", "Erreur de format");
            }

        } catch (Exception e) {

            montrerError("Vous devez determiner une valeur", "Défaut de valeur");
        }

    }

    private void envoyerResetCompteur(int i) {

        String ordre = ordresRAZ.get(i - 1);
        System.out.println("Ordre:" + ordre);
        connecteur.envoyerData(ordre);
    }

    private void envoyerOrdrePause(int i) {

        String ordre = ordresPAUSES.get(i - 1);
        System.out.println("Ordre:" + ordre);
        connecteur.envoyerData(ordre);
    }

    private void envoyerOrdreStop(int i) {

        String ordre = ordresSTOP.get(i - 1);
        System.out.println("Ordre:" + ordre);
        connecteur.envoyerData(ordre);
    }

    private void envoyerOdreCadence(int i) {

        String ordre = ordresCadences.get(i - 1);
        System.out.println("Ordre:" + ordre);
        connecteur.envoyerData(ordre);

    }

    private int envoyerConfiguration() {

        String ordre = Constants.CONFIG;
        String s;

        for (int i = 0; i < 3; i++) {

            actifs[i] = echantillonsActifs.get(i).isSelected();

            s = actifs[i] ? ":1" : ":0";
            ordre = ordre + s;

        }
        if (ordre.equals("W:CONFIG:0:0:0")) {

            montrerError("Vous devez sélectionner les échantillons actifs", "Défaut de configuration");
            return -1;
        }

        String cadence = null;
        if (cad_1_par_2mins.isSelected()) {
            cadence = ":2";
        }

        if (cad_1_par_5mins.isSelected()) {
            cadence = ":3";
        }

        if (cad_2_min.isSelected()) {
            cadence = ":1";
        }

        ordre = ordre + cadence;

        String mode = null;

        if (menuManuel.isSelected()) {

            mode = ":0";

        } else {
            mode = ":1";
        }

        ordre = ordre + mode;

        System.out.println("Config: " + ordre);
        connecteur.envoyerData(ordre);
        return 0;

    }

    private void fermeture() {

        if (connexionActive) {

            connecteur.envoyerData(Constants.FERMETURE);

        } else {

            System.exit(0);
        }

    }

    private int closeWindow() {

        System.out.println("Fermeture programme");
        // montrerError("Utiliser le menu Fichier pour fermer!", "Fermeture programme");
        return 0;
    }

}
