package com.example.cloart.gesaqua;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * @class Alarmes
 * @brief Fournit des méthodes pour effectuer des requêtes sur la table 'alarmes' de la base de données
 * Created by iris on 21/04/17.
 */

public class Alarmes
{
    private GesAquaBDD gesAquaBDD;
    private SQLiteDatabase bdd;

    /**
     * @brief Constructeur public de la classe Alarmes
     * @param context de type Context
     */
    public Alarmes(Context context)
    {
        gesAquaBDD = new GesAquaBDD(context);
        gesAquaBDD.open();
        bdd = gesAquaBDD.getBDD();
    }

    /**
     * @brief Permet d'obtenir la liste des tuples de la table 'alarmes'
     * @return un type List<Alarme> qui représente une liste de tuples de la table
     */
    public List<Alarme> getListe()
    {
        List<Alarme> alarmes = new ArrayList<>();
        Cursor cursor = bdd.query("alarmes", new String[] {"idAlarme", "type", "mesure", "horodatage", "seuilMin", "seuilMax", "description"}, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            Alarme alarme = cursorToAlarme(cursor, false);
            alarmes.add(alarme);
            cursor.moveToNext();
        }

        cursor.close();

        return alarmes;

    }

    /**
     * @brief Permet d'insérer un nouveau tuple dans la table 'alarmes'
     * @param alarme un objet de type Alarme
     * @return un long différent de -1 si l'insertion a réussi
     */
    public long inserer(Alarme alarme)
    {
        ContentValues values = new ContentValues();

        values.put("type", alarme.getType());
        values.put("mesure", alarme.getMesure());
        values.put("horodatage", alarme.getHorodatage());
        values.put("seuilMin", alarme.getSeuilMin());
        values.put("seuilMax", alarme.getSeuilMax());
        values.put("description", alarme.getDescription());


        return bdd.insert("alarmes", null, values);
    }

    /**
     * @brief Permet de mettre à jour un tuple de la table
     * @param idAlarme un int qui représente l'identifiant du tuple
     * @param alarme un objet de type Alarme qui va remplacer l'ancien
     * @return un int qui permet de savoir si la suppression a réussi
     */
    public int modifier(int idAlarme, Alarme alarme)
    {
        ContentValues values = new ContentValues();

        values.put("type", alarme.getType());
        values.put("mesure", alarme.getMesure());
        values.put("horodatage", alarme.getHorodatage());
        values.put("seuilMin", alarme.getSeuilMin());
        values.put("seuilMax", alarme.getSeuilMax());
        values.put("description", alarme.getDescription());


        return bdd.update("alarmes", values, "idAlarme = " + idAlarme, null);
    }

    /**
     * @brief Permet de récupérer un tuple la table 'alarmes'
     * @param type un String qui représente le type de l'alarme (pH, température, niveau d'eau)
     * @return un objet de type Alarme
     */
    public Alarme getAlarme(String type)
    {
        Cursor c = bdd.rawQuery("SELECT * FROM alarmes WHERE type = ?", new String[] { type });

        return cursorToAlarme(c, true);
    }

    /**
     * @brief Cette méthode permet de convertir un cursor en un objet de type Alarme
     * @param c un objet de type Cursor
     * @param one un booléen
     * @return un objet de type Alarme
     */
    private Alarme cursorToAlarme(Cursor c, boolean one)
    {
        if (c.getCount() == 0)
            return null;

        if(one == true)
            c.moveToFirst();

        Alarme alarme = new Alarme();

        alarme.setType(c.getString(1));
        alarme.setMesure(c.getDouble(2));
        alarme.setHorodatage(c.getString(3));
        alarme.setSeuilMin(c.getDouble(4));
        alarme.setSeuilMax(c.getDouble(5));
        alarme.setDescription(c.getString(6));


        if(one == true)
            c.close();

        return alarme;
    }

}
