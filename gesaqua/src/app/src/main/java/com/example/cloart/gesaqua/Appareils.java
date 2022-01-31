package com.example.cloart.gesaqua;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * @class Appareils
 * @brief Fournit des méthodes pour effectuer des requêtes sur la table 'appareils' de la base de données
 * Created by cloart on 22/03/17.
 */

public class Appareils {

    private GesAquaBDD gesAquaBDD;
    private SQLiteDatabase bdd;

    /**
     * @brief Constructeur public de la classe Appareils
     * @param context : classe abstraite qui permet l'accès à des classes et ressources spécifiques à l'application
     */
    public Appareils(Context context)
    {
        gesAquaBDD = new GesAquaBDD(context);
        bdd = gesAquaBDD.getBDD();
    }

    /**
     * @brief Méthode pour obtenir la liste des tuples de la table 'appareils'
     * @return une liste d'objets Appareil
     */
    public List<Appareil> recupererListeAppareils()
    {
        Cursor cursor = bdd.query("appareils", new String[] {"idAppareil", "nom", "etat", "horodatage"}, null, null, null, null, null);
        List<Appareil> appareils  = new ArrayList<Appareil>();

        //appareils.clear();
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            Appareil appareil = cursorToAppareil(cursor, false);
            appareils.add(appareil);
            cursor.moveToNext();
        }

        cursor.close();

        return appareils;
    }

    /**
     * @brief Permet d'insérer un nouveau tuple dans la table
     * @param appareil un objet de type Appareil
     * @return un long différent de -1 si l'insertion a réussi
     */
    public long inserer(Appareil appareil)
    {
        ContentValues values = new ContentValues();

        values.put("nom", appareil.getNom());
        values.put("etat", appareil.getEtat());
        values.put("horodatage", appareil.getHorodatage());

        return bdd.insert("appareils", null, values);
    }

    /**
     * @brief Permet de mettre à jour un tuple de la table
     * @param idAppareil un int qui représente l'identifiant du tuple
     * @param appareil un objet de type Appareil qui va remplacer l'ancien
     * @return un int qui permet de savoir si la mise à jour du tuple a réussi
     */
    public int modifier(int idAppareil, Appareil appareil)
    {
        ContentValues values = new ContentValues();
        values.put("nom", appareil.getNom());
        values.put("etat", appareil.getEtat());
        values.put("horodatage", appareil.getHorodatage());

        return bdd.update("appareils", values, "idAppareil = " + idAppareil, null);
    }

    /**
     * @brief Permet de supprimer un tuple de la table 'appareils'
     * @param idAppareil un int qui représente l'identifiant du tuple
     * @return un int qui permet de savoir si la suppression a réussi
     */
    public int supprimer(int idAppareil)
    {
        return bdd.delete("appareils", "idAppareil = " + idAppareil, null);
    }

    /*public Appareil getAppareil(int id)
    {
        for(int i = 0; i < appareils.size(); i++)
        {
            Appareil appareil = appareils.get(i);
            if(appareil.getIdAppareil() == id)
                return appareil;
        }

        return null;
    }*/

    /**
     * @brief Permet de récupérer la valeur des champs d'un tuple de la table 'appareils'
     * @param nom un String qui représente le nom de l'appareil
     * @return un objet de type Appareil
     */
    public Appareil getAppareil(String nom)
    {
        Cursor c = bdd.rawQuery("SELECT * FROM appareils WHERE nom = ?", new String[] { nom });

        return cursorToAppareil(c, true);
    }

    /**
     * @brief Cette méthode permet de convertir un objet de type Cursor en un objet de type Appareil
     * @param c un objet de type Cursor
     * @param one un booléen
     * @return un objet de type Appareil
     */
    // Cette méthode permet de convertir un cursor en un objet de type Appareil
    private Appareil cursorToAppareil(Cursor c, boolean one)
    {
        if (c.getCount() == 0)
            return null;

        if(one == true)
            c.moveToFirst();

        Appareil appareil = new Appareil();

        appareil.setId(c.getInt(0));
        appareil.setNom(c.getString(1));
        appareil.setEtat(c.getInt(2));
        appareil.setHorodatage(c.getString(3));

        if(one == true)
            c.close();

        return appareil;
    }
}
