package com.example.cloart.gesaqua;

import android.content.Context;
import android.database.sqlite.*;
import android.widget.Filter;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

/**
 * @class GesAquaSQLite
 * @brief classe qui hérite de SQLiteOpenHelper pour définir l’ensemble des tables de la base de données qui seront produites lors de l’instanciation
 * Created by cloart on 22/03/17.
 */

public class GesAquaSQLite extends SQLiteOpenHelper
{
    public static final String  DATABASE_NAME = "gesaqua.db";

    public static final int     DATABASE_VERSION = 1;

    // Pour création des tables
    private static final String CREATE_BDD_APPAREILS =
            "CREATE TABLE appareils (" +
                    "idAppareil INTEGER PRIMARY KEY NOT NULL ," +
                    "nom VARCHAR(45) NULL ," +
                    "etat TINYINT(1) NULL ," +
                    "horodatage DATETIME );"; //DATETIME : format "YYYY-MM-DD HH:MM:SS"


    private static final String CREATE_BDD_CAPTEURS =
            "CREATE TABLE capteurs (" +
                    "idCapteurs INTEGER PRIMARY KEY NOT NULL ," +
                    "typeCapteurs INTEGER NULL ," +
                    "nom VARCHAR(45) NULL ," +
                    "unite VARCHAR(45) NULL ," +
                    "CONSTRAINT fk_capteurs_1 FOREIGN KEY (typeCapteurs ) REFERENCES typeCapteurs (idtypeCapteurs ) );";


    private static final String CREATE_BDD_PARAMETRESVITAUX =
            "CREATE TABLE parametresVitaux (" +
                    "idParametresVitaux INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," +
                    "idCapteur INTEGER NULL ," +
                    "mesure DOUBLE NULL ," +
                    "horodatage DATETIME," + //DATETIME : format "YYYY-MM-DD HH:MM:SS"
                    "CONSTRAINT fk_parametresVitaux_1 FOREIGN KEY (idCapteur ) REFERENCES capteurs (idCapteurs ));";


    private static final String CREATE_BDD_TYPECAPTEURS =
            "CREATE TABLE typeCapteurs (" +
                    "idtypeCapteurs INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," +
                    "typeCapteurs VARCHAR(45) NULL );";


    private static final String CREATE_BDD_CONSIGNES =
            "CREATE TABLE consignes (" +
                    "temperature DOUBLE NULL ," +
                    "ph DOUBLE NULL ," +
                    "niveauEau DOUBLE NULL );" ;

    private static final String CREATE_BDD_SEUILS =
            "CREATE TABLE seuils (" +
                    "temperatureMin DOUBLE NULL ," +
                    "temperatureMax DOUBLE NULL ," +
                    "phMin DOUBLE NULL ," +
                    "phMax DOUBLE NULL ," +
                    "niveauEauMin DOUBLE NULL ," +
                    "niveauEauMax DOUBLE NULL );" ;

    private static final String CREATE_BDD_AQUARIUMS =
            "CREATE TABLE aquariums (" +
                    "idAquarium INTEGER PRIMARY KEY NOT NULL ," +
                    "largeur DOUBLE NULL ," +
                    "longueur DOUBLE NULL ," +
                    "hauteur DOUBLE NULL ," +
                    "volume DOUBLE NULL ," +
                    "dateMiseEnEau DATETIME, "+  //DATETIME : format "YYYY-MM-DD HH:MM:SS"
                    "type VARCHAR(50) NOT NULL);";

    private static final String CREATE_BDD_ALARMES =
            "CREATE TABLE alarmes (" +
                    "idAlarme INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," +
                    "type VARCHAR(50) NOT NULL ," +
                    "mesure DOUBLE NULL ," +
                    "horodatage DATETIME ," + //DATETIME : format "YYYY-MM-DD HH:MM:SS"
                    "seuilMin DOUBLE NOT NULL ," +
                    "seuilMax DOUBLE NOT NULL ," +
                    "description VARCHAR(150) );";


    private static final String CREATE_BDD_MODES =
            "CREATE TABLE modes (" +
                 //   "idMode INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," +
                    "dateDebut DATE ," +
                    "dateFin DATE ," +
                    "heureDebut TIME ," +
                    "heureFin TIME ," +
                    "mode INTEGER NOT NULL );"; //0 pour mode manuel et 1 pour mode automatique


    // Pour insertion des données initiales
    private static final String INSERT_BDD_APPAREILS_1 = "INSERT INTO appareils VALUES(1,'chauffage',0,'2017-03-24 16:31:10');";
    private static final String INSERT_BDD_APPAREILS_2 = "INSERT INTO appareils VALUES(2,'eclairage',0,'2017-03-24 16:31:20');";
    private static final String INSERT_BDD_APPAREILS_3 = "INSERT INTO appareils VALUES(3,'nourriture',0,'2017-03-24 16:31:20');";
    private static final String INSERT_BDD_APPAREILS_4 = "INSERT INTO appareils VALUES(4,'engrais',0,'2017-03-24 16:31:20');";
    private static final String INSERT_BDD_APPAREILS_5 = "INSERT INTO appareils VALUES(5,'oxygenation',0,'2017-03-24 16:31:20');";
    private static final String INSERT_BDD_APPAREILS_6 = "INSERT INTO appareils VALUES(6,'filtration',0,'2017-03-24 16:31:20');";
    private static final String INSERT_BDD_APPAREILS_7 = "INSERT INTO appareils VALUES(7,'ventilation',0,'2017-03-24 16:31:20');";

    private static final String INSERT_BDD_CAPTEURS_1 = "INSERT INTO capteurs VALUES(1,1,'temperature eau','°C');";
    private static final String INSERT_BDD_CAPTEURS_2 = "INSERT INTO capteurs VALUES(2,1,'temperature air','°C');";
    private static final String INSERT_BDD_CAPTEURS_3 = "INSERT INTO capteurs VALUES(3,2,'ph','');";
    private static final String INSERT_BDD_CAPTEURS_4 = "INSERT INTO capteurs VALUES(4,3,'niveau d''eau','cm');";

    private static final String INSERT_BDD_TYPECAPTEURS_1 = "INSERT INTO typeCapteurs VALUES(1,'temperature');";
    private static final String INSERT_BDD_TYPECAPTEURS_2 = "INSERT INTO typeCapteurs VALUES(2,'ph');";
    private static final String INSERT_BDD_TYPECAPTEURS_3 = "INSERT INTO typeCapteurs VALUES(3,'niveau');";

    private static final String INSERT_BDD_CONSIGNES = "INSERT INTO consignes VALUES(24,6.8,34);";

    private static final String INSERT_BDD_SEUILS = "INSERT INTO seuils VALUES(23,27,5.5,9,20,40);"; //valeurs de 20 cm  puis 40 cm pour le niveau d'eau ajoutées de façon arbitraire

    private static final String INSERT_BDD_AQUARIUMS = "INSERT INTO aquariums VALUES(1,40,100,50,200,'2017-04-20 14:20:36','amazonien');";

    // Exemples :
    private static final String INSERT_BDD_ALARMES_1 = "INSERT INTO alarmes VALUES(1,'pH',6.2,'2017-04-21 14:31:36',6.5,8,'pH trop bas')";
    private static final String INSERT_BDD_ALARMES_2 = "INSERT INTO alarmes VALUES(2,'niveau',19.8,'2017-04-21 14:31:36',20,40,'niveau : eau trop basse')";
    private static final String INSERT_BDD_ALARMES_3 = "INSERT INTO alarmes VALUES(3,'temperature',19.3,'2017-04-21 14:31:36',23,27,'température trop basse')";

   /* private static final String INSERT_BDD_MODES_1 = "INSERT INTO modes VALUES(1,'2017-04-21','2017-04-21','14:31:36','18:43:36',0);";
    private static final String INSERT_BDD_MODES_2 = "INSERT INTO modes VALUES(2,'2017-04-21','2017-04-24','14:31:36','18:43:36',1);";*/

    private static final String INSERT_BDD_MODES_1 = "INSERT INTO modes VALUES('2017-04-21','2017-04-21','14:31:36','18:43:36',0);";


    /**
     * @brief Constructeur public de la classe GesAquaSQLite
     * @param context : classe abstraite qui permet l'accès à des classes et ressources spécifiques à l'application
     */
    public GesAquaSQLite(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * @brief Redéfinition de la méthode onCreate()
     * @brief Création d'une base de données qui ne l’est pas encore et insertion des données initiales
     * @param db : une base de données de type SQLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        // on crée la BDD :
        // 1. création des tables
        db.execSQL(CREATE_BDD_APPAREILS);
        db.execSQL(CREATE_BDD_TYPECAPTEURS);
        db.execSQL(CREATE_BDD_CAPTEURS);
        db.execSQL(CREATE_BDD_PARAMETRESVITAUX);
        db.execSQL(CREATE_BDD_CONSIGNES);
        db.execSQL(CREATE_BDD_SEUILS);
        db.execSQL(CREATE_BDD_AQUARIUMS);
        db.execSQL(CREATE_BDD_ALARMES);
        db.execSQL(CREATE_BDD_MODES);

        // 2. insertion des données initiales
        db.execSQL(INSERT_BDD_APPAREILS_1);
        db.execSQL(INSERT_BDD_APPAREILS_2);
        db.execSQL(INSERT_BDD_APPAREILS_3);
        db.execSQL(INSERT_BDD_APPAREILS_4);
        db.execSQL(INSERT_BDD_APPAREILS_5);
        db.execSQL(INSERT_BDD_APPAREILS_6);
        db.execSQL(INSERT_BDD_APPAREILS_7);

        db.execSQL(INSERT_BDD_TYPECAPTEURS_1);
        db.execSQL(INSERT_BDD_TYPECAPTEURS_2);
        db.execSQL(INSERT_BDD_TYPECAPTEURS_3);

        db.execSQL(INSERT_BDD_CAPTEURS_1);
        db.execSQL(INSERT_BDD_CAPTEURS_2);
        db.execSQL(INSERT_BDD_CAPTEURS_3);
        db.execSQL(INSERT_BDD_CAPTEURS_4);

        //db.execSQL(INSERT_BDD_PARAMETRESVITAUX_1);
        //db.execSQL(INSERT_BDD_PARAMETRESVITAUX_2);

        db.execSQL(INSERT_BDD_CONSIGNES);
        db.execSQL(INSERT_BDD_SEUILS);
        db.execSQL(INSERT_BDD_AQUARIUMS);

        db.execSQL(INSERT_BDD_ALARMES_1);
        db.execSQL(INSERT_BDD_ALARMES_2);
        db.execSQL(INSERT_BDD_ALARMES_3);

        db.execSQL(INSERT_BDD_MODES_1);
      //  db.execSQL(INSERT_BDD_MODES_2);

        String path = db.getPath();
        File f = new File(path);
        boolean r = f.setReadable(true, false);
        if(r)
        {
            Log.d("GesAquaSQLite", "onCreate : Ajout droit lecture " + path); // d = debug
        }
        else
        {
            Log.e("GesAquaSQLite", "onCreate : Erreur ajout droit lecture " + path); // e = erreur
        }
        r = f.setWritable(true, false);
        if(r)
        {
            Log.d("BD", "onCreate : Ajout droit écriture " + path); // d = debug
        }
        else
        {
            Log.e("BD", "onCreate : Erreur ajout droit écriture " + path); // e = erreur
        }
        File parentDir = f.getAbsoluteFile().getParentFile();
        r = parentDir.setReadable(true, false);
        if(r)
        {
            Log.d("GesAquaSQLite", "onCreate : Ajout droit lecture " + parentDir.getPath()); // d = debug
        }
        else
        {
            Log.e("GesAquaSQLite", "onCreate : Erreur ajout droit lecture " + parentDir.getPath()); // e = erreur
        }
        r = parentDir.setWritable(true, false);
        if(r)
        {
            Log.d("BD", "onCreate : Ajout droit écriture " + parentDir.getPath()); // d = debug
        }
        else
        {
            Log.e("BD", "onCreate : Erreur ajout droit écriture " + parentDir.getPath()); // e = erreur
        }
    }

    /**
     * @brief Redéfinition de la méthode onUpgrade()
     * @brief  Si la version de la base de données évolue, cette méthode permettra de mettre à jour le schéma de base de données existant ou de supprimer la base de données existante et la recréer par la méthode onCreate()
     * @param db : base de données de type SQLiteDatabase
     * @param oldVersion : in int qui représente l'ancienne version de la base de données
     * @param newVersion : un int qui représente la nouvelle version de la base de données
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // on supprime la table puis on la recrée
        db.execSQL("DROP TABLE appareils;");
        db.execSQL("DROP TABLE capteurs;");
        db.execSQL("DROP TABLE parametresVitaux;");
        db.execSQL("DROP TABLE typeCapteurs;");
        db.execSQL("DROP TABLE consignes;");
        db.execSQL("DROP TABLE seuils;");
        db.execSQL("DROP TABLE aquariums");
        db.execSQL("DROP TABLE alarmes");
        db.execSQL("DROP TABLE modes");
        onCreate(db);
    }

    /**
     * @brief Ajoute les droits en lecture et en écriture à la base de données lors de son ouverture
     * @param db un type SQLiteDatabase qui représente la base de données
     */
    @Override
    public void onOpen(SQLiteDatabase db)
    {
        String path = db.getPath();
        File f = new File(path);
        boolean r = f.setReadable(true, false);
        if(r)
        {
            Log.d("GesAquaSQLite", "onOpen : Ajout droit lecture " + path); // d = debug
        }
        else
        {
            Log.e("GesAquaSQLite", "onOpen : Erreur ajout droit lecture " + path); // e = erreur
        }
        r = f.setWritable(true, false);
        if(r)
        {
            Log.d("BD", "onOpen : Ajout droit écriture " + path); // d = debug
        }
        else
        {
            Log.e("BD", "onOpen : Erreur ajout droit écriture " + path); // e = erreur
        }
        File parentDir = f.getAbsoluteFile().getParentFile();
        r = parentDir.setReadable(true, false);
        if(r)
        {
            Log.d("GesAquaSQLite", "onOpen : Ajout droit lecture " + parentDir.getPath()); // d = debug
        }
        else
        {
            Log.e("GesAquaSQLite", "onOpen : Erreur ajout droit lecture " + parentDir.getPath()); // e = erreur
        }
        r = parentDir.setWritable(true, false);
        if(r)
        {
            Log.d("BD", "onOpen : Ajout droit écriture " + parentDir.getPath()); // d = debug
        }
        else
        {
            Log.e("BD", "onOpen : Erreur ajout droit écriture " + parentDir.getPath()); // e = erreur
        }
    }
}
