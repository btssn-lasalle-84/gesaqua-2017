package com.example.cloart.gesaqua;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @class SQLiteDatabase
 * @brief Classe qui sert à accéder à la base de données de l’application
 * Created by cloart on 22/03/17.
 */

public class GesAquaBDD {

    private SQLiteDatabase bdd = null;
    private GesAquaSQLite gesAquaSQLite = null;

    /**
     * @brief Constructeur public de la classe GesAquaBDD qui permet de créer la base de données
     * @param context : classe abstraite qui permet l'accès à des classes et ressources spécifiques à l'application
     */
    public GesAquaBDD(Context context)
    {
        // on crée la BDD
        gesAquaSQLite = new GesAquaSQLite(context);
    }

    /**
     * @brief Ouverture de la base de données en écriture
     */
    public void open()
    {
        // on ouvre la BDD en écriture
        if (bdd == null)
            bdd = gesAquaSQLite.getWritableDatabase();
    }

    /**
     * @brief Fermeture de la base de données
     */
    public void close()
    {
        // on ferme la BDD
        if (bdd != null)
            if (bdd.isOpen())
                bdd.close();
    }

    /**
     * @brief Méthode qui permet d'accéder à une base de données
     * @return une base de données de type SQLiteDatabase
     */
    public SQLiteDatabase getBDD()
    {
        // pas ouverte ?
        if (bdd == null)
            open();
        // on retourne un accès à la BDD
        return bdd;
    }


}
