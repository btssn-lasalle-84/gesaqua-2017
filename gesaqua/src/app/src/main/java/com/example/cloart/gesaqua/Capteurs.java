package com.example.cloart.gesaqua;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
/**
 * @class Capteurs
 * @brief Fournit des méthodes pour effectuer des requêtes sur la table 'capteurs' de la base de données
 * Created by cloart on 22/03/17.
 */

public class Capteurs {

    private GesAquaBDD gesAquaBDD;
    private SQLiteDatabase bdd;

    /**
     * @brief Constructeur public de la classe Capteurs
     * @param context un objet de type Context
     */
    public Capteurs(Context context)
    {
        gesAquaBDD = new GesAquaBDD(context);
        gesAquaBDD.open();
        bdd = gesAquaBDD.getBDD();
    }

    /**
     * @brief Méthode pour obtenir la liste des tuples de la table 'capteurs'
     * @return un type List rempli d'objets Capteur
     */
    public List<Capteur> getListe()
    {
        List<Capteur> capteurs = new ArrayList<Capteur>();

        Cursor cursor = bdd.query("capteurs", new String[] {"idCapteurs", "typeCapteurs", "nom",  "unite"}, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            Capteur capteur = cursorToCapteurs(cursor, false);
            capteurs.add(capteur);
            cursor.moveToNext();
        }

        cursor.close();

        return capteurs;
    }

    /**
     * @brief Permet d'insérer un nouveau tuple dans la table
     * @param capteur un objet de type Capteur
     * @return un long différent de -1 si l'insertion a réussi
     */
    public long inserer(Capteur capteur)
    {
        ContentValues values = new ContentValues();

        values.put("typeCapteurs", capteur.getTypeCapteurs());
        values.put("nom", capteur.getNom());
        values.put("unite", capteur.getUnite());

        return bdd.insert("capteurs", null, values);
    }

    /**
     * @brief Permet de mettre à jour un tuple de la table
     * @param id un int qui représente l'identifiant du tuple
     * @param capteur un objet de type Capteur qui va remplacer l'ancien
     * @return un int
     */
    public int modifier(int id, Capteur capteur)
    {
        ContentValues values = new ContentValues();
        values.put("typeCapteurs", capteur.getTypeCapteurs());
        values.put("nom", capteur.getNom());
        values.put("unite", capteur.getUnite());

        return bdd.update("capteurs", values, "idCapteurs = " + id, null);
    }

    /**
     * @brief Permet de supprimer un tuple de la table
     * @param id un int qui représente l'identifiant du tuple à supprimer
     * @return un int
     */
    public int supprimer(int id)
    {
        return bdd.delete("capteurs", "idCapteurs = " + id, null);
    }

    /**
     * @brief Permet de récupérer la valeur d'un objet de type Capteur
     * @param nom un String qui représente le nom du capteur
     * @return un objet de type Capteur
     */
    public Capteur getCapteur(String nom)
    {
        Cursor c = bdd.rawQuery("SELECT * FROM capteurs WHERE nom = ?", new String[] { nom });

        return cursorToCapteurs(c, true);
    }

    /**
     * @brief Cette méthode permet de convertir un cursor en un objet de type Capteur
     * @param c un objet de type Cursor
     * @param one un booléen
     * @return un objet de type Capteur (un tuple de la table 'capteurs')
     */
    // Cette méthode permet de convertir un cursor en un objet de type Capteur
    private Capteur cursorToCapteurs(Cursor c, boolean one)
    {
        if (c.getCount() == 0)
            return null;

        if(one == true)
            c.moveToFirst();

        Capteur capteur = new Capteur();

        capteur.setIdCapteurs(c.getInt(0));
        capteur.setNom(c.getString(1));
        capteur.setUnite(c.getString(2));

        if(one == true)
            c.close();

        return capteur;
    }

}

