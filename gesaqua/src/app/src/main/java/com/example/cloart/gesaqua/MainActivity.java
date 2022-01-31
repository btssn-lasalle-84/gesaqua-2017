package com.example.cloart.gesaqua;

import android.bluetooth.BluetoothAdapter; //Bluetooth
import android.bluetooth.BluetoothDevice;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
//import java.util.logging.Handler;
import android.os.Message;
import android.support.annotation.IntDef;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import java.lang.StringBuffer;
import java.util.concurrent.TimeUnit;

import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.CompoundButton;
import android.app.AlertDialog;
import android.content.DialogInterface;

import android.content.Intent;

/**
 * @class MainActivity
 * @brief Activité principale de l'application (Thread UI)
 */
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Aquarium aquarium;
    private static final int MON_AQUARIUM = 1;
    boolean aquariumConnecte = false;
    // Les données des capteurs
    private float temperature;
    private float pHEau;
    private float niveauEau;
    // Les seuils
    private double niveauMin;
    private double niveauMax;
    private double tempMin;
    private double tempMax;
    private double phMin;
    private double phMax;
    //Les consignes
    private double consTemperature;
    private double consPh;
    private double consNivEau;
    // Les différents types de capteurs
    private static final int CAPTEUR_TEMPERATURE = 1;
    private static final int CAPTEUR_PH = 2;
    private static final int CAPTEUR_NIVEAU_EAU = 3;
    Appareils appareils;
    // Les différents types d'appareils
    // par id
    private static final int APPAREIL_CHAUFFAGE = 1;
    private static final int APPAREIL_ECLAIRAGE = 2;
    private static final int APPAREIL_NOURRITURE = 3;
    private static final int APPAREIL_ENGRAIS = 4;
    private static final int APPAREIL_OXYGENATION = 5;
    private static final int APPAREIL_FILTRATION = 6;
    private static final int APPAREIL_VENTILATION = 7;
    // par nom
    private static final String NOM_APPAREIL_CHAUFFAGE = "chauffage";
    private static final String NOM_APPAREIL_ECLAIRAGE = "eclairage";
    private static final String NOM_APPAREIL_NOURRITURE = "nourriture";
    private static final String NOM_APPAREIL_ENGRAIS = "engrais";
    private static final String NOM_APPAREIL_OXYGENATION = "oxygenation";
    private static final String NOM_APPAREIL_FILTRATION = "filtration";
    private static final String NOM_APPAREIL_VENTILATION = "ventilation";
    // Les états des appareils
    private int etatChauffage = 0;
    private int etatEclairage = 0;
    private int etatNourriture = 0;
    private int etatEngrais = 0;
    private int etatOxygenation = 0;
    private int etatFiltration = 0;
    private int etatVentilation = 0;
    private int etatChauffagePrec = 0;
    private int etatEclairagePrec = 0;
    private int etatNourriturePrec = 0;
    private int etatEngraisPrec = 0;
    private int etatOxygenationPrec = 0;
    private int etatFiltrationPrec = 0;
    private int etatVentilationPrec = 0;
    //Etat Alarmes:
    boolean alarmePh = false;
    boolean alarmeNivEau = false;
    boolean alarmeTemp = false;
    Alarme alarme;
    // Protocole :
    // Type de trame
    private static final String TRAME_APPAREIL = "e";
    private static final String TRAME_DONNEES = "d";
    private static final String TRAME_REGLAGE = "r";
    private static final String TRAME_SEUILS = "s";
    private static final String TRAME_ALARME = "a";
    // Structure
    private static final String TRAME_DEBUT = "$";
    private static final String TRAME_DELIMITEUR = ";";
    private static final String TRAME_CHECKSUM = "*";
    private static final String TRAME_FIN = "\r";
    // Identifiants des Intent
    final int ID_Intent_Alarmes = 2;
    final int ID_Intent_Parametres = 1;
    final int ID_DISCOVERABLE_INTENT = 0;
    // Le message qui contient la trame reçue
    String message;
    // Pour la base de données
    private ParametresVitaux parametresVitaux = null;
    private Seuils seuils = null;
    private Consigne consigne = null;
    private Consignes consignes = null;
    private Alarmes alarmes = null;
    // Le bluetooth
    private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
    private PeripheriqueBluetooth peripheriqueBluetooth;
    // Divers
    // pour le changement d'heure
    private int heureInit;
    private String dureeEclairage;
    private String derniereDistributionEngrais;
    private String derniereDistributionNourriture;
    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Log.d("onCreate()", "initialiserActivite()"); // d = debug
        initialiserActivite();

        Log.d("onCreate()", "activerBluetooth()"); // d = debug
        activerBluetooth();

        Log.d("onCreate()", "recupererValeurs()"); // d = debug
        recupererValeurs();

        Log.d("onCreate()", "initialiserModeFonctionnement()"); // d = debug
        initialiserModeFonctionnement();

        Log.d("onCreate()", "initialiserCommandeAppareils()"); // d = debug
        initialiserCommandeAppareils();

        Log.d("onCreate()", "gererConsignes()"); // d = debug
        gererConsignes();

        //Log.d("onCreate()", "afficherDonnees()"); // d = debug
        afficherDonnees();
        afficherConsignes();
    }

    private void gererConsignes() {
        EditText editTextConsNiveauEau = (EditText) findViewById(R.id.editTextConsNiveauEau);
        EditText editTextConsPH = (EditText) findViewById(R.id.editTextConsPH);
        EditText editTextConsTemp = (EditText) findViewById(R.id.editTextConsTemp);

        /*consNivEau = Double.valueOf(((EditText) findViewById(R.id.editTextConsNiveauEau)).getText().toString());
        consPh = Double.valueOf(((EditText) findViewById(R.id.editTextConsPH)).getText().toString());
        consTemperature = Double.valueOf(((EditText) findViewById(R.id.editTextConsTemp)).getText().toString());
        afficherConsignes();
        enregistrerDonnees();*/

        editTextConsNiveauEau.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (!((EditText) findViewById(R.id.editTextConsNiveauEau)).getText().toString().isEmpty()) {
                    if(35 <= Double.valueOf(((EditText) findViewById(R.id.editTextConsNiveauEau)).getText().toString()) & Double.valueOf(((EditText) findViewById(R.id.editTextConsNiveauEau)).getText().toString()) <= 48){
                        consNivEau = Double.valueOf(((EditText) findViewById(R.id.editTextConsNiveauEau)).getText().toString());
                        consigne = new Consigne(consTemperature, consPh, consNivEau);
                        consignes.modifier(consigne);
                        // fabrique la trame de commande et l'envoie à l'aquarium
                        String trame = fabriquerTrameConsignes();
                        if(peripheriqueBluetooth!=null)
                            peripheriqueBluetooth.envoyer(trame);
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        editTextConsPH.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if(!((EditText) findViewById(R.id.editTextConsPH)).getText().toString().isEmpty()) {
                    if(6.5 <= Double.valueOf(((EditText) findViewById(R.id.editTextConsPH)).getText().toString()) & Double.valueOf(((EditText) findViewById(R.id.editTextConsPH)).getText().toString()) <= 8){
                        consPh = Double.valueOf(((EditText) findViewById(R.id.editTextConsPH)).getText().toString());
                        consigne = new Consigne(consTemperature, consPh, consNivEau);
                        consignes.modifier(consigne);
                        // fabrique la trame de commande et l'envoie à l'aquarium
                        String trame = fabriquerTrameConsignes();
                        if(peripheriqueBluetooth!=null)
                            peripheriqueBluetooth.envoyer(trame);
                    }
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        editTextConsTemp.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if(!((EditText) findViewById(R.id.editTextConsTemp)).getText().toString().isEmpty()) {
                    if(23 <= Double.valueOf(((EditText) findViewById(R.id.editTextConsTemp)).getText().toString()) & Double.valueOf(((EditText) findViewById(R.id.editTextConsTemp)).getText().toString())<= 27){
                        //recupere la consigne température
                        consTemperature = Double.valueOf(((EditText) findViewById(R.id.editTextConsTemp)).getText().toString());
                        consigne = new Consigne(consTemperature, consPh, consNivEau);
                        consignes.modifier(consigne);
                        // fabrique la trame de commande et l'envoie à l'aquarium
                        String trame = fabriquerTrameConsignes();
                        if(peripheriqueBluetooth!=null)
                            peripheriqueBluetooth.envoyer(trame);
                    }

                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }

    @Override
    /**
     * @brief
     */
    public void onResume() {
        super.onResume();
        /*  actualise l'affichage lorsqu'on change d'activité */
        Log.d("onResume()", "afficherDonnees()"); // d = debug
        afficherDonnees();
        Log.d("onResume()", "afficherDerniereDistributionNourriture()"); // d = debug
        afficherDerniereDistributionNourriture();
        Log.d("onResume()", "afficherDerniereDistributionEngrais()"); // d = debug
        afficherDerniereDistributionEngrais();
        Log.d("onResume()", "afficherDureeEclairage()"); // d = debug
        afficherDureeEclairage();
    }

    @Override
    /**
     * @brief methode qui permet de deconnecter les periphériques bluetooth lors de la fin de l'application
     */
    protected void onDestroy() {
        /* on déconnecte le bluetooth ce qui arrêtere la réception des trames */
        Log.d("onDestroy()", "deconnecter()"); // d = debug
        if( peripheriqueBluetooth != null)
            peripheriqueBluetooth.deconnecter();
        if (mBluetoothAdapter != null) {
            mBluetoothAdapter.cancelDiscovery();
            unregisterReceiver(mReceiver);
        }
        super.onDestroy();
    }

    /**
     * @brief Gère les communications avec le thread de réception des trames
     */
    final private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == PeripheriqueBluetooth.CODE_CONNEXION) {
                //Toast.makeText(getApplicationContext(), "Connexion Aquarium : ok !", Toast.LENGTH_SHORT).show();
                Log.d("handleMessage()", "Connexion : ok"); // d = debug
                aquariumConnecte = true;
                afficherEtatAquarium();
                activerModeFonctionnement();
                activerCommandesAppareils();
            }
            else if (msg.what == PeripheriqueBluetooth.CODE_DECONNEXION) {
                aquariumConnecte = false;
                afficherEtatAquarium();
                effacerDonnees();
                desactiverModeFonctionnement();
                desactiverCommandesAppareils();
                //Toast.makeText(getApplicationContext(), "Déconnexion Aquarium !", Toast.LENGTH_SHORT).show();
                Log.d("handleMessage()", "Déconnexion : ok"); // d = debug
            }
            else if (msg.what == PeripheriqueBluetooth.CODE_RECEPTION) {
                /* Vérifie si le message n'est pas vide */
                if (msg.obj != null)
                {
                    message = (String) msg.obj;
                    //Log.d("handleMessage()", "Réception : ok"); // d = debug
                    /* extrait les données (eau, seuils, alarmes, ...) de la trame reçue */
                    boolean donneesExtraites = extraireDonnees(message);
                    if(donneesExtraites)
                    {
                        /* provoque l'affichage des données extraites */
                        afficherDonnees();
                        /* provoque l'enregistrement des données extraites */
                        enregistrerDonnees();
                    }
                    /* extrait les états des appareils de la trame reçue */
                    boolean etatsExtraits = extraireEtats(message);
                    if(etatsExtraits)
                    {
                        /* provoque l'affichage des états extraits */
                        afficherEtats();
                        /* provoque l'enregistrement des états extraits */
                        enregistrerEtats();
                    }
                }
            }
            else if (msg.what == PeripheriqueBluetooth.CODE_INACTIF) {
                //Toast.makeText(getApplicationContext(), "Connexion inactive !", Toast.LENGTH_SHORT).show();
                Log.d("handleMessage()", "Connexion inactive !"); // d = debug
                aquariumConnecte = false;
                afficherEtatAquarium();
                effacerDonnees();
                desactiverModeFonctionnement();
                desactiverCommandesAppareils();
            }
            else if (msg.what == PeripheriqueBluetooth.CODE_ACTIF) {
                //Toast.makeText(getApplicationContext(), "Connexion active !", Toast.LENGTH_SHORT).show();
                Log.d("handleMessage()", "Connexion active !"); // d = debug
                aquariumConnecte = true;
                afficherEtatAquarium();
                activerModeFonctionnement();
                activerCommandesAppareils();
            }
            else {
                Log.e("handleMessage()", "Code inconnu !");
            }
        }
    };

    /**
     * @brief Fonction qui permet d'enregistrer les états des appareils
     */
    private void enregistrerEtats()
    {
        if(etatChauffage != etatChauffagePrec)
            modifierEtatAppareil(APPAREIL_CHAUFFAGE, NOM_APPAREIL_CHAUFFAGE, etatChauffage, R.id.imageViewChauffage, R.id.SwitchChauffage);

        if(etatEclairage != etatEclairagePrec)
            modifierEtatAppareil(APPAREIL_ECLAIRAGE, NOM_APPAREIL_ECLAIRAGE, etatEclairage, R.id.imageViewEclairage, R.id.SwitchEclairage);

        if(etatNourriture != etatNourriturePrec)
            modifierEtatAppareil(APPAREIL_NOURRITURE, NOM_APPAREIL_NOURRITURE, etatNourriture, R.id.imageViewNourriture, R.id.SwitchNourriture);

        if(etatEngrais != etatEngraisPrec)
           modifierEtatAppareil(APPAREIL_ENGRAIS, NOM_APPAREIL_ENGRAIS, etatEngrais, R.id.imageViewEngrais, R.id.SwitchEngrais);

        /* modifie l'état de l'appareil */
        if(etatOxygenation != etatOxygenationPrec)
            modifierEtatAppareil(APPAREIL_OXYGENATION, NOM_APPAREIL_OXYGENATION, etatOxygenation, R.id.imageViewOxygenation, R.id.SwitchOxygenation);

        /* modifie l'état de l'appareil */
        if(etatFiltration != etatFiltrationPrec)
            modifierEtatAppareil(APPAREIL_FILTRATION, NOM_APPAREIL_FILTRATION, etatFiltration, R.id.imageViewFiltration, R.id.SwitchFiltration);

        if(etatVentilation != etatVentilationPrec)
            modifierEtatAppareil(APPAREIL_VENTILATION, NOM_APPAREIL_VENTILATION, etatVentilation, R.id.imageViewVentilation, R.id.SwitchVentilation);
    }

    /**
     * @brief Fonction qui permet d'afficher les états des appareils
     */
    private void afficherEtats()
    {
        if(etatChauffage != etatChauffagePrec)
            afficherEtatAppareil(APPAREIL_CHAUFFAGE, NOM_APPAREIL_CHAUFFAGE, etatChauffage, R.id.imageViewChauffage, R.id.SwitchChauffage);
        if(etatEclairage != etatEclairagePrec)
            afficherEtatAppareil(APPAREIL_ECLAIRAGE, NOM_APPAREIL_ECLAIRAGE, etatEclairage, R.id.imageViewEclairage, R.id.SwitchEclairage);
        if(etatNourriture != etatNourriturePrec)
            afficherEtatAppareil(APPAREIL_NOURRITURE, NOM_APPAREIL_NOURRITURE, etatNourriture, R.id.imageViewNourriture, R.id.SwitchNourriture);
        if(etatEngrais != etatEngraisPrec)
            afficherEtatAppareil(APPAREIL_ENGRAIS, NOM_APPAREIL_ENGRAIS, etatEngrais, R.id.imageViewEngrais, R.id.SwitchEngrais);
        if(etatOxygenation != etatOxygenationPrec)
            afficherEtatAppareil(APPAREIL_OXYGENATION, NOM_APPAREIL_OXYGENATION, etatOxygenation, R.id.imageViewOxygenation, R.id.SwitchOxygenation);
        if(etatFiltration != etatFiltrationPrec)
            afficherEtatAppareil(APPAREIL_FILTRATION, NOM_APPAREIL_FILTRATION, etatFiltration, R.id.imageViewFiltration, R.id.SwitchFiltration);
        if(etatVentilation != etatVentilationPrec)
            afficherEtatAppareil(APPAREIL_VENTILATION, NOM_APPAREIL_VENTILATION, etatVentilation, R.id.imageViewVentilation, R.id.SwitchVentilation);

        /* arrêt éclairage ? */
        if(etatEclairagePrec == etatEclairage && etatEclairage == 1)
        {
            calculerDureeEclairage();
            Log.d("enregistrerEtats()", "Durée éclairage : " + dureeEclairage); // d = debug
            afficherDureeEclairage();
        }
        else if(etatEclairage == 0)
        {
            dureeEclairage = "0 minute";
            afficherDureeEclairage();

        }


        if(etatNourriturePrec != etatNourriture && etatNourriture == 1)
        {
            derniereDistributionNourriture = getHorodatage();
            Log.d("enregistrerEtats()", "Dernière distribution nourriture : " + derniereDistributionNourriture); // d = debug
            afficherDerniereDistributionNourriture();
        }


        if(etatEngraisPrec != etatEngrais && etatEngrais == 1)
        {
            derniereDistributionEngrais = getHorodatage();
            Log.d("enregistrerEtats()", "Dernière distribution engrais : " + derniereDistributionEngrais); // d = debug
            afficherDerniereDistributionEngrais();
        }
    }

    /**
     * @brief Fonction qui permet de sauvegarder les données dans la base de données
     */
    private void enregistrerDonnees()
    {
        ParametreVital parametreVital = null;

        /* enregistre l'échantillon dans la base de données */
        parametreVital = new ParametreVital(CAPTEUR_TEMPERATURE, temperature, getHorodatageBD());
        long resultat = parametresVitaux.inserer(parametreVital);
        if (resultat != -1) {
            parametreVital.setIdParametreVital((int) resultat);
            Log.d("enregistrerDonnees()", "Id insertion température : " + resultat); // d = debug
        }

        parametreVital = new ParametreVital(CAPTEUR_NIVEAU_EAU, niveauEau, getHorodatageBD());
        resultat = parametresVitaux.inserer(parametreVital);
        if (resultat != -1) {
            parametreVital.setIdParametreVital((int) resultat);
            Log.d("enregistrerDonnees()", "Id insertion niveau d'eau : " + resultat); // d = debug
        }

        parametreVital = new ParametreVital(CAPTEUR_PH, pHEau, getHorodatageBD());
        resultat = parametresVitaux.inserer(parametreVital);
        if (resultat != -1) {
            parametreVital.setIdParametreVital((int) resultat);
            Log.d("enregistrerDonnees()", "Id insertion pH : " + resultat); // d = debug
        }

        /* Enregistre la valeur */
        seuils.setTemperatureMin(tempMin);
        /* Enregistre la valeur */
        seuils.setTemperatureMax(tempMax);
        /* Enregistre la valeur */
        seuils.setNiveauEauMin(niveauMin);
        /* Enregistre la valeur */
        seuils.setNiveauEauMax(niveauMax);
        /* Enregistre la valeur */
        seuils.setPhMax(phMax);
        /* Enregistre la valeur */
        seuils.setPhMin(phMin);
        consigne = new Consigne(consTemperature,consPh,consNivEau);
        int verif = consignes.modifier(consigne);

        /* purge les valeurs enregistrées si nécessaire (par heure) */
        purgerValeurs();
    }

    /**
     * @brief Purge les valeurs enregistrées (par heure)
     */
    private void purgerValeurs()
    {
        /* gestion de l'heure */
        Calendar calendar = Calendar.getInstance();
        int heureCourante = calendar.get(Calendar.HOUR_OF_DAY);

        /* calculer les valeurs pour la dernière heure */
        if((heureCourante == (heureInit+1)) || (heureInit == 23 && heureCourante == 0))
        {
            calculerValeurs();
            heureInit = heureCourante;
        }
    }

    /**
     * Intialise l'affichage de l'activité
     */
    private void initialiserActivite() {
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /* crée l'aquarium */
        aquarium = new Aquarium(getApplicationContext());
        /* initialise l'IHM par défaut */
        effacerDonnees();
        desactiverModeFonctionnement();
        desactiverCommandesAppareils();
    }

    /**
     * @brief Gère l'activation de l'adaptateur Bluetooth
     */
    protected void activerBluetooth()
    {
        // A-t-on un adaptateur bluetooth ?
        if (mBluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "Bluetooth non présent !", Toast.LENGTH_SHORT).show();
            Log.d("activerBluetooth()", "Bluetooth non présent !"); // d = debug
        } else if (!mBluetoothAdapter.isEnabled()) { // le bluetooth est-il désactivé ?
            Log.d("activerBluetooth()", "Demande activation Bluetooth"); // d = debug
            // Demande de l'activation du bluetooth
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivityForResult(discoverableIntent, ID_DISCOVERABLE_INTENT);
        } else {
            // le bluetooth est déjà activé
            //Toast.makeText(getApplicationContext(), "Bluetooth activé", Toast.LENGTH_SHORT).show();
            Log.d("activerBluetooth()", "Bluetooth activé"); // d = debug
        }

        // Recherche des périphériques
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);
        mBluetoothAdapter.startDiscovery();

        BluetoothDevice myDevice = null;
        peripheriqueBluetooth = null;
        // Pour tous les périphériques bluetooth appairés
        for (BluetoothDevice device : pairedDevices) {
            // le périphérique bluetooth de l'aquarium ?
            if (device.getName().equals("HC-05")) { //Simulateur
                //Toast.makeText(getApplicationContext(), "Périphérique = " + device.getName(), Toast.LENGTH_SHORT).show();
                Log.d("activerBluetooth()", "Nom du Périphérique = " + device.getName()); // d = debug
                myDevice = device;
                peripheriqueBluetooth = new PeripheriqueBluetooth(myDevice, handler);
                break; // on peut sortir
            }
        }

        if (peripheriqueBluetooth != null)
        {
            Log.d("activerBluetooth()", "Demande connexion Bluetooth"); // d = debug
            peripheriqueBluetooth.connecter();
        }
    }

    /**
     * @class BroadcastReceiver
     * @brief permet d'écouter ce qui se passe sur le système et de rendre la tablette visible par les périphériques Bluetooth distants
     */
    private final BroadcastReceiver mReceiver = new BroadcastReceiver()
    {
        public void onReceive(Context context, Intent intent)
        {
            ArrayList mArrayList = new ArrayList();
            String action = intent.getAction();
            // Périphérique bluetooth trouvé ?
            if (BluetoothDevice.ACTION_FOUND.equals(action))
            {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                mArrayList.add(device.getName() + "\n" + device.getAddress());
            }
        }
    };

    protected void recupererValeurs()
    {
        seuils = new Seuils(getApplicationContext());
        parametresVitaux = new ParametresVitaux(getApplicationContext());
        appareils = new Appareils(getApplicationContext());
        consignes = new Consignes(getApplicationContext());
        alarmes = new Alarmes(getApplicationContext());

        Calendar calendar = Calendar.getInstance();
        // pour la gestion des mesures horaires
        heureInit = calendar.get(Calendar.HOUR_OF_DAY);

        /* purge les mesures précédentes (pour les essais) */
        /* @todo peut-être prévoir ces actions à partir du menu */
        //parametresVitaux.purger(CAPTEUR_TEMPERATURE);
        //parametresVitaux.purger(CAPTEUR_PH);
        //parametresVitaux.purger(CAPTEUR_NIVEAU_EAU);

        /* récupération des données initiales de la base de données */

        /* les informations sur l'aquarium */
        afficherCaracteristiquesAquarium();

        /* les seuils */
        seuils.obtenir();
        Log.d("recupererValeurs()", seuils.toString()); // d = debug
        tempMin = seuils.getTemperatureMin();
        tempMax = seuils.getTemperatureMax();
        phMin = seuils.getPhMin();
        phMax = seuils.getPhMax();
        niveauMin = seuils.getNiveauEauMin() ;
        niveauMax = seuils.getNiveauEauMax();

        /* les états des appareils */
        Appareil appareil;
        Switch sw;
        appareil = appareils.getAppareil(NOM_APPAREIL_CHAUFFAGE);
        etatChauffage = appareil.getEtat();
        etatChauffagePrec = etatChauffage;
        afficherEtatAppareil(APPAREIL_CHAUFFAGE, NOM_APPAREIL_CHAUFFAGE, etatChauffage, R.id.imageViewChauffage, R.id.SwitchChauffage);
        sw = (Switch) findViewById(R.id.SwitchChauffage);
        if(etatChauffage == 1)
            sw.setChecked(true);
        else
            sw.setChecked(false);
        appareil = appareils.getAppareil(NOM_APPAREIL_ECLAIRAGE);
        etatEclairage = appareil.getEtat();
        etatEclairagePrec = etatEclairage;
        afficherEtatAppareil(APPAREIL_ECLAIRAGE, NOM_APPAREIL_ECLAIRAGE, etatEclairage, R.id.imageViewEclairage, R.id.SwitchChauffage);
        sw = (Switch) findViewById(R.id.SwitchEclairage);
        if(etatEclairage == 1)
            sw.setChecked(true);
        else
            sw.setChecked(false);
        appareil = appareils.getAppareil(NOM_APPAREIL_NOURRITURE);
        etatNourriture = appareil.getEtat();
        etatNourriturePrec = etatNourriture;
        afficherEtatAppareil(APPAREIL_NOURRITURE, NOM_APPAREIL_NOURRITURE, etatNourriture, R.id.imageViewNourriture, R.id.SwitchChauffage);
        sw = (Switch) findViewById(R.id.SwitchNourriture);
        if(etatNourriture == 1)
            sw.setChecked(true);
        else
            sw.setChecked(false);

        appareil = appareils.getAppareil(NOM_APPAREIL_ENGRAIS);
        etatEngrais = appareil.getEtat();
        etatEngraisPrec = etatEngrais;
        afficherEtatAppareil(APPAREIL_ENGRAIS, NOM_APPAREIL_ENGRAIS, etatEngrais, R.id.imageViewEngrais, R.id.SwitchEngrais);
        sw = (Switch) findViewById(R.id.SwitchEngrais);
        if(etatEngrais == 1)
            sw.setChecked(true);
        else
            sw.setChecked(false);

        appareil = appareils.getAppareil(NOM_APPAREIL_OXYGENATION);
        etatOxygenation = appareil.getEtat();
        etatOxygenationPrec = etatOxygenation;
        afficherEtatAppareil(APPAREIL_OXYGENATION, NOM_APPAREIL_OXYGENATION, etatOxygenation, R.id.imageViewOxygenation, R.id.SwitchOxygenation);
        sw = (Switch) findViewById(R.id.SwitchOxygenation);
        if(etatOxygenation == 1)
            sw.setChecked(true);
        else
            sw.setChecked(false);

        appareil = appareils.getAppareil(NOM_APPAREIL_FILTRATION);
        etatFiltration = appareil.getEtat();
        etatFiltrationPrec = etatFiltration;
        afficherEtatAppareil(APPAREIL_FILTRATION, NOM_APPAREIL_FILTRATION, etatFiltration, R.id.imageViewFiltration, R.id.SwitchFiltration);
        sw = (Switch) findViewById(R.id.SwitchFiltration);
        if(etatFiltration == 1)
            sw.setChecked(true);
        else
            sw.setChecked(false);

        appareil = appareils.getAppareil(NOM_APPAREIL_VENTILATION);
        etatVentilation = appareil.getEtat();
        etatVentilationPrec = etatVentilation;
        afficherEtatAppareil(APPAREIL_VENTILATION, NOM_APPAREIL_VENTILATION, etatVentilation, R.id.imageViewVentilation, R.id.SwitchVentilation);
        sw = (Switch) findViewById(R.id.SwitchVentilation);
        if(etatVentilation == 1)
            sw.setChecked(true);
        else
            sw.setChecked(false);

        /** @todo à récupérer dans la base de données */

        appareil = appareils.getAppareil(NOM_APPAREIL_ECLAIRAGE);
        dureeEclairage = appareil.getHorodatage();

        appareil = appareils.getAppareil(NOM_APPAREIL_ENGRAIS);
        derniereDistributionEngrais = appareil.getHorodatage();

        appareil = appareils.getAppareil(NOM_APPAREIL_NOURRITURE);
        derniereDistributionNourriture = appareil.getHorodatage();

        afficherEtats();

        consigne = consignes.obtenir();
        if(consigne != null) {
            consTemperature = consigne.getTemperature();
            consPh = consigne.getPh();
            consNivEau = consigne.getNiveauEau();
            //Log.d("Consignes", "Temp : " + consTemperature + " pH : " + consPh + " Niveau : " + consNivEau);
        }

        afficherConsignes();

        afficherDonnees();
    }

    /**
     * Gère le mode fonctionnement (auto/manu)
     */
    private void initialiserModeFonctionnement() {
        final Switch modeAuto = (Switch) findViewById(R.id.switchAuto);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        /* on défini le texte et le titre de la boite de dialogue*/
        builder.setMessage("Voulez vous vraiment activer le mode auto ?").setTitle("Attention");
        /* on crée 2 boutons pour l'utilisateur qui seront dans le dialogue d'alerte*/
        builder.setPositiveButton("Confirmer", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                /* Ouvre la view android calendar view pour l'affichage du calendrier*/
                Intent intent = new Intent(MainActivity.this, CalendarViewExample.class);
                startActivity(intent);

                //En mode automatique les appareils ne peuvent plus s'activer manuellement
                desactiverCommandesAppareils();
                /* Affiche un message si le bouton est chécké et si oui est tapé */
                Toast check = Toast.makeText(getApplicationContext(), " Le mode auto est activé !", Toast.LENGTH_SHORT);
                check.show();
            }
        });
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                /* annule le cochage du switch*/
                modeAuto.setChecked(false);
            }
        });
        modeAuto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            /**  permet de faire un bouton switch en créant un CompoundButton et de faire une écoute des signaux de changement.
             *   Tout est inclus dans la fonction setOnCheckedChangeListener.
             */
            public void onCheckedChanged(CompoundButton buttonView, boolean Check) {
                if (Check) /* Vérifie si le switch est coché et demande la confirmation du cochage*/ {
                    AlertDialog alarme = builder.create();
                    alarme.show();
                } else {   /* si il est décoché marque désactivé */
                //On peut activer manuellement les appareils
                    activerCommandesAppareils();
                    Toast check = Toast.makeText(getApplicationContext(), " Le mode auto est désactivé !", Toast.LENGTH_SHORT);
                    check.show();
                }
            }
        });
    }

    /**
     * @brief initialise tout interrupteur
     */
    private void initialiserCommandeAppareils() {
        gererInterrupteurChauffage();

        gererInterrupteurEclairage();

        gererInterrupteurNourriture();

        gererInterrupteurEngrais();

        gererInterrupteurOxygene();

        gererInterrupteurFiltration();

        gererInterrupteurVentilation();
    }

    /**
     * @brief affiche les données de l'aquarium: temperature, pH et niveau d'eau
     */
    private void afficherDonnees() {
        if(aquariumConnecte)
        {
            afficherTemperature();
            afficherPh();
            afficherNiveauEau();
        }
    }
    private void afficherConsignes()
    {
        afficherConsPh();
        afficherConsTemp();
        afficherConsNivEau();
    }

    /**
     * @brief efface les données de l'aquarium: temperature, pH et niveau d'eau
     */
    private void effacerDonnees()
    {
        // Initialise par défaut aucune valeur
        ((TextView) findViewById(R.id.textViewTemperature)).setText("Température : --- °C");
        ((TextView) findViewById(R.id.textViewPH)).setText("pH de l'eau : ---");
        ((TextView) findViewById(R.id.textViewNiveauEau)).setText("Niveau de l'eau : --- cm");
    }

    /**
     * @brief affiche la température
     */
    protected void afficherTemperature()
    {
        //Log.d("afficherTemperature()", "Température : " + temperature);
        ((TextView) findViewById(R.id.textViewTemperature)).setText("Température : " + temperature + " °C");
    }

    /**
     * @brief affiche le pH
     */
    protected void afficherPh()
    {
        ((TextView) findViewById(R.id.textViewPH)).setText("pH de l'eau : " + pHEau);
    }

    /**
     * @brief affiche le niveau d'eau
     */
    protected void afficherNiveauEau()
    {
        ((TextView) findViewById(R.id.textViewNiveauEau)).setText("Niveau de l'eau : " + niveauEau + " cm");
    }

    /**
     * @brief affiche la durée d'éclairage
     */
    protected void afficherDureeEclairage()
    {
        ((TextView) findViewById(R.id.dureeEclairage)).setText("Durée d'éclairage : " + dureeEclairage);
    }

     /**
     * @brief affiche la derniére distribution d'engrais
     */
    protected void afficherDerniereDistributionEngrais()
    {
        ((TextView) findViewById(R.id.derniereDistributionEngrais)).setText("Dernière distribution d'engrais : " + derniereDistributionEngrais);
    }

    /**
     * @brief affiche la derniére distribution de nourriture
     */
    protected void afficherDerniereDistributionNourriture()
    {
        ((TextView) findViewById(R.id.derniereDistributionNourriture)).setText("Dernière distribution de nourriture : " + derniereDistributionNourriture);
    }

    /**
     * @brief affiche l'état de l'aquarium si il est connecté
     */
    protected void afficherEtatAquarium()
    {
        if(aquariumConnecte)
        {
            ImageView img = (ImageView) findViewById(R.id.imageViewAquarium);
            img.setImageResource(R.drawable.on);
        }
        else
        {
            ImageView img = (ImageView) findViewById(R.id.imageViewAquarium);
            img.setImageResource(R.drawable.off);
        }
    }

    /**
     * @brief affiche les caractéristiques de l'aquarium
     */
    protected void afficherCaracteristiquesAquarium()
    {
        aquarium.getAquarium(MON_AQUARIUM);
        String caracteristiques = aquarium.toString();
        ((TextView) findViewById(R.id.textViewInfosAquarium)).setText(caracteristiques);
    }

    /**
     * @brief desactive les boutons du mode automatique/alarme
     */
    private void desactiverModeFonctionnement()
    {
        Switch sw = (Switch) findViewById(R.id.switchAuto);
        sw.setEnabled(false);
        CheckBox cb = (CheckBox) findViewById(R.id.checkBoxAlarmes);
        cb.setEnabled(false);
    }

    /**
     * @brief active les boutons du mode automatique/alarme
     */
    private void activerModeFonctionnement()
    {
        Switch sw = (Switch) findViewById(R.id.switchAuto);
        sw.setEnabled(true);
        CheckBox cb = (CheckBox) findViewById(R.id.checkBoxAlarmes);
        cb.setEnabled(true);
    }

    /**
     * @brief desactive les switchs des appareils pour ne plu pouvoir les modifier
     */
    private void desactiverCommandesAppareils()
    {
        Switch sw = (Switch) findViewById(R.id.SwitchChauffage);
        sw.setEnabled(false);
        sw = (Switch) findViewById(R.id.SwitchEclairage);
        sw.setEnabled(false);
        sw = (Switch) findViewById(R.id.SwitchNourriture);
        sw.setEnabled(false);
        sw = (Switch) findViewById(R.id.SwitchEngrais);
        sw.setEnabled(false);
        sw = (Switch) findViewById(R.id.SwitchOxygenation);
        sw.setEnabled(false);
        sw = (Switch) findViewById(R.id.SwitchFiltration);
        sw.setEnabled(false);
        sw = (Switch) findViewById(R.id.SwitchVentilation);
        sw.setEnabled(false);
    }

    /**
     * @brief active les switchs des appareils pour ne plu pouvoir les modifier
     */
    private void activerCommandesAppareils()
    {
        Switch sw = (Switch) findViewById(R.id.SwitchChauffage);
        sw.setEnabled(true);
        sw = (Switch) findViewById(R.id.SwitchEclairage);
        sw.setEnabled(true);
        sw = (Switch) findViewById(R.id.SwitchNourriture);
        sw.setEnabled(true);
        sw = (Switch) findViewById(R.id.SwitchEngrais);
        sw.setEnabled(true);
        sw = (Switch) findViewById(R.id.SwitchOxygenation);
        sw.setEnabled(true);
        sw = (Switch) findViewById(R.id.SwitchFiltration);
        sw.setEnabled(true);
        sw = (Switch) findViewById(R.id.SwitchVentilation);
        sw.setEnabled(true);
    }

    /**
     * @brief desactive les switchs des appareils pour ne plus pouvoir les modifier
     */
    private void gererInterrupteurChauffage() {
        final Switch chauffage = (Switch) findViewById(R.id.SwitchChauffage);
        chauffage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            /**  permet de faire un bouton switch en créant un CompoundButton et de faire une écoute des signaux de changement.
             *   Tout est inclu dans la fonction setOnCheckedChangeListener.
             */

            public void onCheckedChanged(CompoundButton buttonView, boolean Check) {
                /* Verifie si le bouton est checké ou pas*/
                if (Check) {
                    commanderAppareil(NOM_APPAREIL_CHAUFFAGE, 1);
                } else {
                    commanderAppareil(NOM_APPAREIL_CHAUFFAGE, 0);
                }
            }
        });
    }

    /**
     * @brief permet de changer l'etat du bouton en vérifiant l'état du switch lui correspondant
     */
    private void gererInterrupteurEclairage() {
        final Switch eclairage = (Switch) findViewById(R.id.SwitchEclairage);
        eclairage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            /**  permet de faire un bouton switch en créant un CompoundButton et de faire une écoute des signaux de changement.
             *   Tout est inclu dans la fonction setOnCheckedChangeListener.
             */
            public void onCheckedChanged(CompoundButton buttonView, boolean Check) {
                /* Verifie si le bouton est checké ou pas*/
                if (Check) {
                    commanderAppareil(NOM_APPAREIL_ECLAIRAGE, 1);
                } else {
                    commanderAppareil(NOM_APPAREIL_ECLAIRAGE, 0);
                }
            }
        });
    }

    /**
     * @brief permet de changer l'etat du bouton en vérifiant l'état du switch lui correspondant
     */
    private void gererInterrupteurNourriture() {
        final Switch nourriture = (Switch) findViewById(R.id.SwitchNourriture);
        nourriture.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            /**  permet de faire un bouton switch en créant un CompoundButton et de faire une écoute des signaux de changement.
             *   Tout est inclu dans la fonction setOnCheckedChangeListener.
             */
            public void onCheckedChanged(CompoundButton buttonView, boolean Check) {
                /* Verifie si le bouton est checké ou pas*/
                if (Check) {
                    commanderAppareil(NOM_APPAREIL_NOURRITURE, 1);
                } else {
                    commanderAppareil(NOM_APPAREIL_NOURRITURE, 0);
                }
            }
        });
    }

    /**
     * @brief permet de changer l'etat du bouton en vérifiant l'état du switch lui correspondant
     */
    private void gererInterrupteurEngrais() {
        final Switch engrais = (Switch) findViewById(R.id.SwitchEngrais);
        engrais.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            /**  permet de faire un bouton switch en créant un CompoundButton et de faire une écoute des signaux de changement.
             *   Tout est inclu dans la fonction setOnCheckedChangeListener.
             */

            public void onCheckedChanged(CompoundButton buttonView, boolean Check) {
                if (Check) {
                    commanderAppareil(NOM_APPAREIL_ENGRAIS, 1);
                } else {
                    commanderAppareil(NOM_APPAREIL_ENGRAIS, 0);
                }
            }
        });
    }

    /**
     * @brief permet de changer l'etat du bouton en vérifiant l'état du switch lui correspondant
     */
    private void gererInterrupteurOxygene() {
        final Switch oxygene = (Switch) findViewById(R.id.SwitchOxygenation);
        oxygene.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            /**  permet de faire un bouton switch en créant un CompoundButton et de faire une écoute des signaux de changement.
             *   Tout est inclu dans la fonction setOnCheckedChangeListener.
             */

            public void onCheckedChanged(CompoundButton buttonView, boolean Check) {
                if (Check) {
                    commanderAppareil(NOM_APPAREIL_OXYGENATION, 1);
                } else {
                    commanderAppareil(NOM_APPAREIL_OXYGENATION, 0);
                }
            }
        });
    }

    /**
     * @brief permet de changer l'etat du bouton en vérifiant l'état du switch lui correspondant
     */
    private void gererInterrupteurFiltration() {
        final Switch filtration = (Switch) findViewById(R.id.SwitchFiltration);
        filtration.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            /**  permet de faire un bouton switch en créant un CompoundButton et de faire une écoute des signaux de changement.
             *   Tout est inclu dans la fonction setOnCheckedChangeListener.
             */

            public void onCheckedChanged(CompoundButton buttonView, boolean Check) {
                if (Check) {
                    commanderAppareil(NOM_APPAREIL_FILTRATION, 1);
                } else {
                    commanderAppareil(NOM_APPAREIL_FILTRATION, 0);
                }
            }
        });
    }

    /**
     * @brief permet de changer l'etat du bouton en vérifiant l'état du switch lui correspondant
     */
    private void gererInterrupteurVentilation() {
        final Switch ventilation = (Switch) findViewById(R.id.SwitchVentilation);
        ventilation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            /**  permet de faire un bouton switch en créant un CompoundButton et de faire une écoute des signaux de changement.
             *   Tout est inclu dans la fonction setOnCheckedChangeListener.
             */
            public void onCheckedChanged(CompoundButton buttonView, boolean Check) {
                if (Check) {
                    /* Verifie si le bouton est checké ou pas*/
                    commanderAppareil(NOM_APPAREIL_VENTILATION, 1);
                } else {
                    commanderAppareil(NOM_APPAREIL_VENTILATION, 0);
                }
            }
        });
    }

    /**
     * @brief permet d'envoyer une trame selon l'appareil et d'afficher son activation
     * @param nom correspond au nom de l'appareil
     * @param etat correspond à l'état de l'appareil : activé ou désactivé
     */
    private void commanderAppareil(String nom, int etat) {
        if(!aquariumConnecte)
            return;

        Appareil appareil = appareils.getAppareil(nom);

        // fabrique la trame de commande et l'envoie à l'aquarium
        String trame = fabriquerTrameCommandeAppareils(appareil.getIdAppareil(), etat);

        peripheriqueBluetooth.envoyer(trame);

        if(etat == 1) {
            Toast check = Toast.makeText(getApplicationContext(), nom + " activé", Toast.LENGTH_SHORT);
            check.show();
        }
        else {
            Toast check = Toast.makeText(getApplicationContext(), nom + " désactivé", Toast.LENGTH_SHORT);
            check.show();
        }
    }

    /**
     * @brief fabrique une trame avec les données de l'appareil correspondant et son état
     * @param etat correspond à l'état de l'appareil
     * @param idAppareil correspond à l'indentifiant de l'appareil
     * (Voir protocole de communication avec l'aquarium)
     */
    private String fabriquerTrameCommandeAppareils(int idAppareil, int etat) {

        String trame = TRAME_DEBUT + "e" + TRAME_DELIMITEUR;

        // Type : e (commande des appareils)
        List<Appareil> listeAppareils = appareils.recupererListeAppareils();
        for (int i = 0; i < listeAppareils.size(); i++)
        {
            Appareil appareil = listeAppareils.get(i);
            if((i + 1) == idAppareil)
            {
                if ((i + 1) == listeAppareils.size())
                    trame += etat;
                else
                    trame += (etat + TRAME_DELIMITEUR);
                continue;
            }
            else
            {
                if ((i + 1) == listeAppareils.size())
                    trame += appareil.getEtat();
                else
                    trame += (appareil.getEtat() + TRAME_DELIMITEUR);
            }
        }

        trame = ajouterChecksum(trame);

        trame += TRAME_FIN;

        return trame;
    }

    /**
     * @brief fabrique une trame avec les consignes
     * (Voir protocole de communication avec l'aquarium)
     */
    private String fabriquerTrameConsignes() {
        // Format : $TYPE;TEMPERATURE;NIVEAU;PH*XX\r
        String trame = TRAME_DEBUT + "r" + TRAME_DELIMITEUR;

        // Type : r (réglages des consignes)
        trame += (consTemperature + TRAME_DELIMITEUR);
        trame += (consNivEau + TRAME_DELIMITEUR);
        trame += (consPh);

        trame = ajouterChecksum(trame);

        trame += TRAME_FIN;

        return trame;
    }

    /**
     * @brief Ajoute le checksum calculé à la fin de la trame
     * @author Thierry Vaira <taira@free.fr>
     */
    private String ajouterChecksum(String trame) {
        trame += "*" + calculerChecksum(trame);
        //Log.d("ajouterChecksum()", "Trame : " + trame);

        return trame;
    }

    /**
     * @brief Calcule un checksum sur 8 bits (à partir d'un CRC 16 bits MODBUS)
     * @author Thierry Vaira <taira@free.fr>
     */
    private String calculerChecksum(String trame) {
        int lsb;
        int j,i;
        short checksum = (short) 0xFFFF;

        // CRC 16 bits MODBUS
        for (j = 0; j < trame.length(); j++)
        {
            checksum ^= trame.charAt(j);
            for (i = 0; i < 8; i++)
            {
                lsb = checksum & 0x0001;
                if (lsb == 1)
                {
                    checksum >>>= 1;
                    checksum &= 0x7FFF;
                    checksum ^= 0xA001;
                }
                else
                {
                    checksum >>>= 1;
                    checksum &= 0x7FFF;
                }
            }
        }

        String strChecksum = Integer.toHexString(checksum);
        StringBuffer sbChecksum = new StringBuffer(strChecksum);
        // passe sur 8 bits
        sbChecksum.delete(0, sbChecksum.length()-2);

        return sbChecksum.toString().toUpperCase();
    }

    /**
     * @brief Extrait les données de température, pH et niveau d'eau ( trame '$d') et les seuils (trame '$s')
     * - @todo les consignes, les alarmes
     */
    public boolean extraireDonnees(String message) {
        boolean retour = false;
        String champ;
        StringBuffer sbMessage = new StringBuffer(message);

        /* détection d'erreurs via le checksum */
        if (!verifierTrame(message)) return retour;

        if (message.contains("$") && message.contains("\r")) {
            /* permet de supprimer tous les caractéres avant le premier '$' pour avoir un debut de trame valide*/
            sbMessage.delete(0, message.indexOf("$"));

            //Log.d("extraireDonnees()", "Reçu : " + sbMessage); // d = debug

            /* Vérifie si le message correspond bien a celui des trames */
            while (message.contains("\r") && message.contains("$")) {
                /* Vérifie si la trame contient des données de seuils */
                if (message.charAt(1) == 's') {
                    /* Supprime les caractéres jusqu'au données suivantes */
                    sbMessage.delete(0, sbMessage.indexOf(";") + 1);
                    /* Affecte à la valeur locale de tempMin la valeur contenue dans la trame de seuils */
                    tempMin = Double.parseDouble(sbMessage.substring(0, sbMessage.indexOf(";")));
                    /* Supprime les caractéres jusqu'au données suivantes */
                    sbMessage.delete(0, sbMessage.indexOf(";") + 1);
                    /* Affecte à la valeur locale de tempMax la valeur contenue dans la trame de seuils */
                    tempMax = Double.parseDouble(sbMessage.substring(0, sbMessage.indexOf(";")));
                    /* Supprime les caractéres jusqu'au données suivantes */
                    sbMessage.delete(0, sbMessage.indexOf(";") + 1);
                    /* Affecte à la valeur locale de niveauMin la valeur contenue dans la trame de seuils */
                    niveauMin = Double.parseDouble(sbMessage.substring(0, sbMessage.indexOf(";")));
                    /* Supprime les caractéres jusqu'au données suivantes */
                    sbMessage.delete(0, sbMessage.indexOf(";") + 1);
                    /* Affecte à la valeur locale de niveauMax la valeur contenue dans la trame de seuils */
                    niveauMax = Double.parseDouble(sbMessage.substring(0, sbMessage.indexOf(";")));
                    /* Supprime les caractéres jusqu'au données suivantes */
                    sbMessage.delete(0, sbMessage.indexOf(";") + 1);
                    /* Affecte à la valeur locale de phMin la valeur contenue dans la trame de seuils */
                    phMin = Double.parseDouble(sbMessage.substring(0, sbMessage.indexOf(";")));
                    /* Supprime les caractéres jusqu'au données suivantes */
                    sbMessage.delete(0, sbMessage.indexOf(";") + 1);
                    // checksum présent ?
                    if(message.contains("*"))
                    {
                        /* Affecte à la valeur locale de phMax la valeur contenue dans la trame de seuils */
                        phMax = Double.parseDouble(sbMessage.substring(0, sbMessage.indexOf("*")));
                    }
                    else
                    {
                        /* Affecte à la valeur locale de phMax la valeur contenue dans la trame de seuils */
                        phMax = Double.parseDouble(sbMessage.substring(0, sbMessage.indexOf("\r")));
                    }
                    retour = true;
                }

                /* Vérifie si c'est une trame de données*/
                if (message.charAt(1) == 'd') {
                    /* Supprime les caractéres jusqu'à la premiére valeur pour la mettre dans la donnée temperature*/
                    sbMessage.delete(0, sbMessage.indexOf(";") + 1);
                    champ = sbMessage.substring(0, sbMessage.indexOf(";"));
                    if(!champ.isEmpty())
                        temperature = Float.parseFloat(champ);

                    /* Supprime les caractéres jusqu'aux données concernant le niveau de l'eau pour les affecter a la valeur du niveau d'eau*/
                    sbMessage.delete(0, sbMessage.indexOf(";") + 1);
                    niveauEau = Float.parseFloat(sbMessage.substring(0, sbMessage.indexOf(";")));

                    /* Supprime les caractéres jusqu'aux données concernant le pH pour les affecter a la valeur du pH*/
                    sbMessage.delete(0, sbMessage.indexOf(";") + 1);
                    // checksum présent ?
                    if(message.contains("*"))
                    {
                        pHEau = Float.parseFloat(sbMessage.substring(0, sbMessage.indexOf("*")));
                    }
                    else
                    {
                        pHEau = Float.parseFloat(sbMessage.substring(0, sbMessage.indexOf("\r")));
                    }
                    retour = true;
                }
                /* supprime les délimteur de fin */
                sbMessage.delete(0, sbMessage.indexOf("\r") + 1);
                message = sbMessage.toString();
            }
        }
        return retour;
    }

    private void calculerValeurs()
    {
        Calendar maintenant = Calendar.getInstance();
        Calendar avant = Calendar.getInstance();
        //maintenant.set(Calendar.MINUTE, 0);
        // on ajoute 1 seconde pour éviter d'effacer la valeur enregistrée à l'heure précédente
        avant.roll(Calendar.SECOND, true);
        // on enlève 1 heure pour définir la plage
        avant.roll(Calendar.HOUR_OF_DAY, false);
        // au format SQLite
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String strDateMaintenant = simpleDateFormat.format(maintenant.getTime());
        final String strDateAvant = simpleDateFormat.format(avant.getTime());

        /* calcul de la médiane pour les températures sur la dernière heure */
        int id = CAPTEUR_TEMPERATURE;
        // by tv
        String requeteSQL =
                "SELECT AVG(mesure) FROM (SELECT mesure FROM parametresVitaux " +
                "WHERE (horodatage BETWEEN '" + strDateAvant + "' AND '" + strDateMaintenant + "') AND parametresVitaux.idCapteur = ? ORDER BY mesure" +
                " LIMIT 2 - (SELECT COUNT(*) FROM parametresVitaux WHERE (horodatage BETWEEN '" + strDateAvant + "' AND '" + strDateMaintenant + "') AND parametresVitaux.idCapteur = ?) % 2 " +
                "OFFSET (SELECT (COUNT(*) - 1) / 2 FROM parametresVitaux WHERE (horodatage BETWEEN '" + strDateAvant + "' AND '" + strDateMaintenant + "') AND parametresVitaux.idCapteur = ?))";
        Log.d("calculerValeurs()", "Requête : " + requeteSQL);
        double mediane = parametresVitaux.getValeur(requeteSQL, String.valueOf(id));
        Log.d("calculerValeurs()", "Médiane température : " + mediane);
        /* ou la moyenne ? */
        /*String requeteSQL =
                "SELECT AVG(mesure) FROM parametresVitaux WHERE (horodatage BETWEEN '" + strDateAvant + "' AND '" + strDateMaintenant + "') AND parametresVitaux.idCapteur = ?";*/
        /* suppression des mesures de température sur la dernière heure */
        requeteSQL = "DELETE FROM parametresVitaux WHERE (horodatage BETWEEN '" + strDateAvant + "' AND '" + strDateMaintenant + "') AND parametresVitaux.idCapteur = " + id;
        Log.d("calculerValeurs()", "Requête : " + requeteSQL);
        parametresVitaux.purger(requeteSQL);
        /* enregistrement de la médiane */
        ParametreVital parametreVital = new ParametreVital(CAPTEUR_TEMPERATURE, mediane, getHorodatageBD());
        long resultat = parametresVitaux.inserer(parametreVital);
        if (resultat != -1) {
            parametreVital.setIdParametreVital((int) resultat);
            Log.d("calculerValeurs()", "Id insertion médiane température : " + mediane + " (" + parametreVital.getHorodatage() + ")"); // d = debug
        }

        /** @todo calcul de la médiane (ou moyenne) pour le pH sur la dernière heure */
        /** @todo suppression des mesures de pH sur la dernière heure */
        /** @todo enregistrement de la médiane */

        /** @todo extraction du minimum pour les niveaux d'eau sur la dernière heure */
        /** @todo suppression des mesures de niveau d'eau sur la dernière heure */
        /** @todo enregistrement du minimum */
    }

    /**
     * @brief Extrait les états des appareils (trame de type : '$e')
     * @param message correspond au message à extraire
     */
    public boolean extraireEtats(String message) {
        boolean retour = false;
        StringBuffer sbMessage = new StringBuffer(message);
        int etat;

        if (!verifierTrame(message)) return retour;

        /* présence des délimiteurs ? */
        if (message.contains("$") && message.contains("\r")) {
            /* permet de supprimer tous les caractéres avant le premier '$' pour avoir un debut de trame valide */
            sbMessage.delete(0, message.indexOf("$"));

            /* Vérifie si le format du message correspond bien a celui des trames */
            while (message.contains("\r") && message.contains("$")) {
                /* Vérifie le type de trame */
                if (message.charAt(1) == 'e') {
                    /* Supprime les caractéres jusqu'au données suivantes */
                    sbMessage.delete(0, sbMessage.indexOf(";") + 1);
                    /* extrait l'état */
                    etatChauffagePrec = etatChauffage;
                    etatChauffage = Integer.parseInt(sbMessage.substring(0, sbMessage.indexOf(";")));

                    /* idem pour tous les états suivants */
                    sbMessage.delete(0, sbMessage.indexOf(";") + 1);
                    etatEclairagePrec = etatEclairage;
                    etatEclairage = Integer.parseInt(sbMessage.substring(0, sbMessage.indexOf(";")));

                    sbMessage.delete(0, sbMessage.indexOf(";") + 1);
                    etatNourriturePrec = etatNourriture;
                    etatNourriture = Integer.parseInt(sbMessage.substring(0, sbMessage.indexOf(";")));

                    sbMessage.delete(0, sbMessage.indexOf(";") + 1);
                    etatEngraisPrec = etatEngrais;
                    etatEngrais = Integer.parseInt(sbMessage.substring(0, sbMessage.indexOf(";")));

                    sbMessage.delete(0, sbMessage.indexOf(";") + 1);
                    etatOxygenationPrec = etatOxygenation;
                    etatOxygenation = Integer.parseInt(sbMessage.substring(0, sbMessage.indexOf(";")));

                    sbMessage.delete(0, sbMessage.indexOf(";") + 1);
                    etatFiltrationPrec = etatFiltration;
                    etatFiltration = Integer.parseInt(sbMessage.substring(0, sbMessage.indexOf(";")));

                    /* Supprime les caractéres jusqu'au données suivantes */
                    sbMessage.delete(0, sbMessage.indexOf(";") + 1);
                    etatVentilationPrec = etatVentilation;
                    // délimiteur checksum présent ?
                    if(message.contains("*"))
                    {
                        etatVentilation = Integer.parseInt(sbMessage.substring(0, sbMessage.indexOf("*")));
                    }
                    else
                    {
                        etatVentilation = Integer.parseInt(sbMessage.substring(0, sbMessage.indexOf("\r")));
                    }
                    retour = true;
                }
                sbMessage.delete(0, sbMessage.indexOf("\r") + 1);
                message = sbMessage.toString();
            }
        }
        return retour;
    }

    /**
     * @brief affiche l'état de l'appareil mis en paramétre en modifiant l'imageView de celui-ci
     * @param idAppareil correspond à l'identifiant de l'appareil
     * @param nomAppareil correspond au nom de l'appareil
     * @param etat correspond à l'état de l'appareil
     * @param idImageView correspond à l'imageView de l'appareil concerné
     * @param idSwitch correspond à l'identifiant du switch concerné
     */
    private void afficherEtatAppareil(int idAppareil, String nomAppareil, int etat, int idImageView, int idSwitch)
    {
        //Appareil appareil = appareils.getAppareil(nomAppareil);
        //Log.d("afficherEtatAppareil()", nomAppareil + " : etat reçu -> " + etat + " - etat BD -> " + appareil.getEtat()); // d = debug

        /* modifie l'image de l'état de l'appareil dans l'IHM */
        ImageView img = (ImageView) findViewById(idImageView);
        Switch sw = (Switch) findViewById(idSwitch);
        if (etat == 1)
        {
            img.setImageResource(R.drawable.on);
            sw.setChecked(true);
        }
        else
        {
            img.setImageResource(R.drawable.off);
            sw.setChecked(false);
        }
    }

    /**
     * @brief modifie l'état de l'appareil mis en paramétre en modifiant l'imageView de celui-ci
     * @param idAppareil correspond à l'identifiant de l'appareil
     * @param nomAppareil correspond au nom de l'appareil
     * @param etat correspond à l'état de l'appareil
     * @param idImageView correspond à l'imageView de l'appareil concerné
     * @param idSwitch correspond à l'identifiant du switch concerné
     */
    private void modifierEtatAppareil(int idAppareil, String nomAppareil, int etat, int idImageView, int idSwitch)
    {
        Appareil appareil = appareils.getAppareil(nomAppareil);
        //Log.d("modifierEtatAppareil()", nomAppareil + " : etat reçu -> " + etat + " - etat BD -> " + appareil.getEtat()); // d = debug

        appareil.setEtat(etat);
        appareil.setHorodatage(getHorodatageBD());
        appareils.modifier(idAppareil, appareil);
    }

    /**
     * @brief Vérifie le checksum reçu avec le checksum calculé
     * @author Thierry Vaira <taira@free.fr>
     * @return booléen
     */
    private boolean verifierTrame(String message)
    {
        /* détection d'erreurs via le checksum */
        if (message.contains("$") && message.contains("\r") && message.contains("*"))
        {
            /* pour le calcule du checksum */
            String trame = message;
            StringBuffer sbMessageC = new StringBuffer(message);
            StringBuffer sbMessageT = new StringBuffer(message);

            /* Supprime les caractéres jusqu'au checksum */
            sbMessageC.delete(0, sbMessageC.indexOf("*") + 1);
            /* Supprime le checksum */
            sbMessageT.delete(sbMessageT.indexOf("*"), sbMessageT.indexOf("\r") + 1);
            String checksumRecu = sbMessageC.substring(sbMessageC.indexOf("*")+1, sbMessageC.indexOf("\r"));
            String checksumCalcule = calculerChecksum(sbMessageT.toString());
            //Log.d("checksum", "Checksum recu : " + checksumRecu + " - Checksum calcule : " + checksumCalcule + " - Vérification : " + checksumRecu.equals(checksumCalcule));
            if (!checksumRecu.equals(checksumCalcule))
            {
                Log.e("checksum", "Erreur checksum ! (" + checksumRecu + " != " + checksumCalcule + ")");
                return false;
            }
        }
        return true;
    }

    /**
     *@brief calcule la durée réelle de l'éclairage en minutes
     */
    private void calculerDureeEclairage()
    {
        Appareil appareil;
        appareil = appareils.getAppareil(NOM_APPAREIL_ECLAIRAGE);
        String duree = appareil.getHorodatage();

        String temps = getHorodatage();
        long heures = Long.parseLong(temps.substring(11,13));
        long minutes = Long.parseLong(temps.substring(14,16));
        long currentTotal = heures * 60 + minutes ;


        long hours = Long.parseLong(duree.substring(11,13));
        long min = Long.parseLong(duree.substring(14,16));
        long total = hours * 60 + min ;

        long calcul = currentTotal - total;

        dureeEclairage = calcul + " minutes";
    }

    private String getHorodatageBD() {
        Calendar calendar = Calendar.getInstance();
        // Format SQLite : "2017-04-28 14:11:52"
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String strDate = simpleDateFormat.format(calendar.getTime());
        return strDate;
    }

    private String getHorodatage() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        final String strDate = simpleDateFormat.format(calendar.getTime());
        return strDate;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* fait enfler le menu et rajoute des objets dedans si il y en a */
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /* menu déroulant qui retourne true ou false selon l'item selectionné*/
        int id = item.getItemId();

        switch (id) {
            case R.id.parametres:
                Intent intent1 = new Intent(MainActivity.this, Parametres.class);
                String Tmin = "Tmin";
                String Tmax = "Tmax";
                String Phmin = "Phmin";
                String Phmax = "Phmax";
                String NiveauMin = "Niveaumin";
                String NiveauMax = "Niveaumax";

                /* met une valeur qui sera envoyée à l'intent1 lors de sa création nommée Tmin */
                intent1.putExtra(Tmin, tempMin);
                /* met une valeur qui sera envoyée à l'intent1 lors de sa création nommée Tmax */
                intent1.putExtra(Tmax, tempMax);
                /* met une valeur qui sera envoyée à l'intent1 lors de sa création nommée Phmin */
                intent1.putExtra(Phmin, phMin);
                /* met une valeur qui sera envoyée à l'intent1 lors de sa création nommée Phmax */
                intent1.putExtra(Phmax, phMax);
                /* met une valeur qui sera envoyée à l'intent1 lors de sa création nommée niveauMin */
                intent1.putExtra(NiveauMin, niveauMin);
                /* met une valeur qui sera envoyée à l'intent1 lors de sa création nommée Tmax */
                intent1.putExtra(NiveauMax, niveauMax);
                /* démarre une activité intent1 qui attend un retour avec comme valeur retour*/
                startActivityForResult(intent1, ID_Intent_Parametres);
                return true;
            /*case R.id.quitter:
                //Pour fermer l'application il suffit de faire finish()
                finish();
                return true;*/
        }

        return super.onOptionsItemSelected(item);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        Toast toast = Toast.makeText(getApplicationContext(), "" + parent.getItemAtPosition(pos), Toast.LENGTH_SHORT);
        toast.show();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    /**
     * Gestion de l'activation des alarmes
     */
    public void onCheckboxClicked(View view) {
        /* récupére la checkBox de content_main.xml*/
        final CheckBox checkBox = (CheckBox) findViewById(R.id.checkBoxAlarmes);
        /* permet de verifier si la CheckBox est checkée*/
        boolean checked = ((CheckBox) view).isChecked();
        if (checked) {
            /* crée un dialogue d'alerte */
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            /* on défini le texte et le titre de la boite de dialogue */
            builder.setMessage("Voulez vous vraiment activer les alarmes ?").setTitle("Attention");
            /* on crée 2 boutons pour l'utilisateur qui seront dans le dialogue d'alerte */
            builder.setPositiveButton("Confirmer", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent alarmesIntent = new Intent(MainActivity.this, AlarmesIHM.class);
                /* met une valeur qui sera envoyée à alarmesIntent lors de sa création nommée Tmin */
                    alarmesIntent.putExtra("Temp", alarmeTemp);
                /* met une valeur qui sera envoyée à alarmesIntent lors de sa création nommée Tmax */
                    alarmesIntent.putExtra("NivEau", alarmeNivEau);
                /* met une valeur qui sera envoyée à alarmesIntent lors de sa création nommée Phmin */
                    alarmesIntent.putExtra("Ph", alarmePh);
                    startActivityForResult(alarmesIntent, ID_Intent_Alarmes);
                }
            });
            builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int id) {
                    /* annule le cochage de la checkBox */
                    checkBox.setChecked(false);

                }
            });

            AlertDialog alarme = builder.create();
            alarme.show();
        } else {   /* Affiche un autre message si le bouton est déchecké */
            Toast check = Toast.makeText(getApplicationContext(), " Les alarmes sont désactivées !", Toast.LENGTH_SHORT);
            check.show();
        }

    }

    @Override

    /**
     * @brief récupére les valeurs de l'activité secondaire pour les mettre dans les variable de l'activité principale
     * @param requestCode correspond au code de l'intent
     * @param resultCode correspond au code résultat attendu retourné par l'intent
     * @param intent correspond à l'intent de retour
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        /* Vérifie si on a bien la réponse qui corresond à l'intent1 */
        if (requestCode == ID_Intent_Parametres) {
            /* Vérifie si c'est bien la réponse attendue*/
            if (resultCode == RESULT_OK) {
                /* Remplace les valeurs de tempMin et tempMax de cette activité par les valeurs saisies dans l'autre activité*/
                tempMin = intent.getDoubleExtra("Tmin", tempMin);
                tempMax = intent.getDoubleExtra("Tmax", tempMax);
                phMin = intent.getDoubleExtra("PhMin", phMin);
                phMax = intent.getDoubleExtra("PhMax", phMax);
                niveauMax = intent.getDoubleExtra("niveauEaumax", niveauMax);
                niveauMin = intent.getDoubleExtra("niveauEaumin", niveauMin);
                enregistrerDonnees();
                String trame = TRAME_DEBUT + TRAME_SEUILS +  TRAME_DELIMITEUR + tempMin + TRAME_DELIMITEUR +tempMax + TRAME_DELIMITEUR + niveauMin + TRAME_DELIMITEUR + niveauMax + TRAME_DELIMITEUR + phMin + TRAME_DELIMITEUR + phMax ;
                trame = ajouterChecksum(trame);
                trame += TRAME_FIN;
                peripheriqueBluetooth.envoyer(trame);
                Toast check = Toast.makeText(getApplicationContext(), "trame:" + trame, Toast.LENGTH_SHORT);
                check.show();
            }
        }
        if (requestCode == ID_Intent_Alarmes) {
            if(resultCode == RESULT_CANCELED)
                ((CheckBox) findViewById(R.id.checkBoxAlarmes)).setChecked(false);
            alarmeTemp = intent.getBooleanExtra("Temp", alarmeTemp);
            alarmePh = intent.getBooleanExtra("Ph", alarmePh);
            alarmeNivEau = intent.getBooleanExtra("NivEau", alarmeNivEau);
            enregistrerDonnees();
            String trame = TRAME_DEBUT + TRAME_ALARME + TRAME_DELIMITEUR + alarmeTemp + TRAME_DELIMITEUR + alarmeNivEau + TRAME_DELIMITEUR + alarmePh;
            trame = ajouterChecksum(trame);
            trame += TRAME_FIN;
            if(peripheriqueBluetooth!=null)
                peripheriqueBluetooth.envoyer(trame);

        }

        if(requestCode == ID_DISCOVERABLE_INTENT)
        {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        /* on défini le texte et le titre de la boite de dialogue*/
            builder.setMessage("Pour que l'appairage se fasse, veuillez redemarrer l'application").setTitle("activation bluethooth");
        /* on crée 2 boutons pour l'utilisateur qui seront dans le dialogue d'alerte*/
            builder.setPositiveButton("Confirmer", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id)
                {
                   finish();
                }
            });
            builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                }
            });
            AlertDialog alarme = builder.create();
            alarme.show();
        }
    }


    public void afficherConsTemp()
    {
        ((EditText) findViewById(R.id.editTextConsTemp)).setText("" + consTemperature );
        Log.d("Consignes", "Temp : " + consTemperature);
    }

    public void afficherConsPh()
    {
        ((EditText) findViewById(R.id.editTextConsPH)).setText("" + consPh );
        Log.d("Consignes", "pH : " + consPh);
    }

    public void afficherConsNivEau()
    {
        ((EditText) findViewById(R.id.editTextConsNiveauEau)).setText("" + consNivEau);
        Log.d("Consignes", "Niveau : " + consNivEau);
    }

    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;

        return true;
    }
}
