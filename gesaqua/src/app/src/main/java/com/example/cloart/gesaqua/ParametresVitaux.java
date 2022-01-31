package com.example.cloart.gesaqua;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @class ParametresVitaux
 * @brief Fournit des méthodes pour effectuer des requêtes sur la table 'parametresVitaux' de la base de données
 * Created by cloart on 22/03/2017
 */

public class ParametresVitaux
{
    private GesAquaBDD gesAquaBDD;
    private SQLiteDatabase bdd;

    /**
     * @brief Constructeur public de la classe ParametresVitaux
     * @param context un objet de type Context
     */
    public ParametresVitaux(Context context)
    {
        gesAquaBDD = new GesAquaBDD(context);
        gesAquaBDD.open();
        bdd = gesAquaBDD.getBDD();
    }

    /**
     * @brief Méthode pour obtenir la liste des tuples de la table 'parametresVitaux'
     * @return un type List qui contient les tuples de la table 'parametresVitaux'
     */
    public List<ParametreVital> getListe()
    {
        List<ParametreVital> parametresVitaux = new ArrayList<ParametreVital>();

        Cursor cursor = bdd.query("parametresVitaux", new String[] {"idParametresVitaux", "idCapteur", "mesure", "horodatage"}, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            ParametreVital parametreVital = cursorToParametres(cursor, false);
            parametresVitaux.add(parametreVital);
            cursor.moveToNext();
        }

        cursor.close();

        return parametresVitaux;
    }

    /**
     * @brief Permet d'insérer un tuple dans la table 'parametresVitaux'
     * @param parametreVital un objet de type ParametreVital et qui représente un tuple de la table 'parametresVitaux'
     * @return un long différent de -1 si l'insertion a réussi
     */
    public long inserer(ParametreVital parametreVital)
    {
        ContentValues values = new ContentValues();

        //values.put("idParametresVitaux", parametreVital.getIdParametreVital());
        values.put("idCapteur", parametreVital.getIdCapteur());
        values.put("mesure", parametreVital.getMesure());
        values.put("horodatage", parametreVital.getHorodatage());

        DecimalFormat df = new DecimalFormat("0.0");
        Log.d("inserer()", "Mesure : " + df.format(parametreVital.getMesure()) + " (" + parametreVital.getHorodatage() + ")" ); // d = debug

        return bdd.insert("parametresVitaux", null, values);
    }

    /**
     * @brief Permet de mettre à jour un tuple de la table 'parametresVitaux'
     * @param id un int qui représente l'identifiant du tuple
     * @param parametreVital un objet de type ParametreVital qui va remplacer l'ancien
     * @return un int
     */
    public int modifier(int id, ParametreVital parametreVital)
    {
        ContentValues values = new ContentValues();
        values.put("idParametreVital", parametreVital.getIdParametreVital());
        values.put("idCapteur", parametreVital.getIdCapteur());
        values.put("mesure", parametreVital.getMesure());
        values.put("horodatage", parametreVital.getHorodatage());

        return bdd.update("parametreVitaux", values, "idParametreVital = " + id, null);
    }

    /**
     * @brief Permet de supprimer un tuple de la table 'parametresVitaux'
     * @param id un int qui représente l'identifiant du tuple à supprimer
     * @return un int
     */
    public int supprimer(int id)
    {
        return bdd.delete("parametreVitaux", "idParametreVital = " + id, null);
    }

    /*
    public ParametreVital getParametreVital(String nom)
    {
        Cursor c = bdd.rawQuery("SELECT * FROM parametresVitaux WHERE nom = ?", new String[] { nom });

        return cursorToParametres(c, true);
    }*/

    /**
     * @brief Permet d'obtenir une valeur du paramètre vital
     * @param requete un String qui représente la requête SQLite
     * @param id un int qui représente l'identifiant du tuple
     * @return un double qui représente la valeur choisie du paramètre vital en fonction de la requête utilisée
     */
    public double getValeur(String requete, String id)
    {
        Cursor c = bdd.rawQuery(requete, new String[] { id });

        if (c.getCount() != 1)
            return 0.0;

        c.moveToFirst();

        double valeur = c.getDouble(0);

        c.close();

        return valeur;
    }

    /**
     * @brief Permer de purger la table
     * @param requete un String qui représente une requête SQLite
     */
    public void purger(String requete)
    {
        bdd.execSQL(requete);
    }

    /**
     * @brief Permet de purger la table selon un paramètre "id"
     * @param id un int qui représente l'identifiant du tuple dans la table
     */
    public void purger(int id)
    {
        String requeteSQL = "DELETE FROM parametresVitaux WHERE parametresVitaux.idCapteur = " + id;
        bdd.execSQL(requeteSQL);
    }

    /**
     * @brief Cette méthode permet de convertir un cursor en un objet de type ParametreVital
     * @param c un objet de type Cursor qui va parcourir le tuple
     * @param one un booléen
     * @return un objet de type ParametreVital (et qui représente un tuple de la table 'parametresVitaux'
     */
    // Cette méthode permet de convertir un cursor en un objet de type ParametreVital
    private ParametreVital cursorToParametres(Cursor c, boolean one)
    {
        if (c.getCount() == 0)
            return null;

        if(one == true)
            c.moveToFirst();

        ParametreVital parametreVital = new ParametreVital();

        parametreVital.setIdParametreVital(c.getInt(0));
        parametreVital.setIdCapteur(c.getInt(1));
        parametreVital.setMesure(c.getDouble(2));
        parametreVital.setHorodatage(c.getString(3));


        if(one == true)
            c.close();

        return parametreVital;
    }


}