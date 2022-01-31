package com.example.cloart.gesaqua;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * @class Consignes
 * @brief Sert à effectuer des requêtes sur la table 'consignes' de la base de données
 * Created by cloart on 20/04/17.
 */

public class Consignes
{
    private GesAquaBDD gesAquaBDD;
    private SQLiteDatabase bdd;
    private Consigne consigne = null;

    /**
     * @brief Constructeur public de la classe Consignes
     * @param context : classe abstraite qui permet l'accès à des classes et ressources spécifiques à l'application
     */
    public Consignes(Context context)
    {
        gesAquaBDD = new GesAquaBDD(context);
        gesAquaBDD.open();
        bdd = gesAquaBDD.getBDD();
        consigne = new Consigne();
    }

    public Consigne obtenir()
    {
        Cursor cursor = bdd.query("consignes", new String[] {"temperature", "ph", "niveauEau"}, null, null, null, null, null);

        cursorToConsigne(cursor);

        return consigne;
    }

    /**
     * @brief Méthode pour insérer dse valeurs dans la table consignes
     * @param consigne : un paramètre de type Consigne et qui représente un tuple de la table 'consignes'
     * @return un long différent de -1 si l'insertion a réussi
     */
    public long inserer(Consigne consigne)
    {
        ContentValues values = new ContentValues();

        values.put("temperature", consigne.getTemperature());
        values.put("ph", consigne.getPh());
        values.put("niveauEau", consigne.getNiveauEau());
        return bdd.insert("consignes", null, values);
    }

    /**
     * @brief Méthode pour mettre à jour des données dans la table consignes
     * @param consigne : un tuple de type Consigne qui doit remplacer l'ancien dans la table 'consignes'
     * @return un int
     */
    public int modifier(Consigne consigne)
    {
        ContentValues values = new ContentValues();

        values.put("temperature", consigne.getTemperature());
        values.put("ph", consigne.getPh());
        values.put("niveauEau", consigne.getNiveauEau());

        return bdd.update("consignes", values, "", null);
    }


/*    public Consigne getConsigne(String nom)
    {
        Cursor c = bdd.rawQuery("SELECT * FROM consignes WHERE temperatureMin = ?", new String[] { nom });

        return cursorToConsigne(c, true);
    }*/

    private void cursorToConsigne(Cursor c)
    {
        if (c.getCount() == 0)
            return;

        c.moveToFirst();

        consigne.setTemperature(c.getDouble(0));
        consigne.setPh(c.getDouble(1));
        consigne.setNiveauEau(c.getDouble(2));

        c.close();
    }

}
