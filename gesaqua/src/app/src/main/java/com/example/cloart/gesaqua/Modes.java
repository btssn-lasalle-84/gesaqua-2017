package com.example.cloart.gesaqua;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


/**
 * @class Modes
 * @brief Fournit des méthodes pour effectuer des requêtes sur la table 'modes' de la base de données
 * Created by iris on 21/04/17.
 */

public class Modes
{
    private GesAquaBDD gesAquaBDD;
    private SQLiteDatabase bdd;

    /**
     * @brief Constructeur public de la classe Modes
     * @param context un objet de type Context
     */
    public Modes(Context context)
    {
        gesAquaBDD = new GesAquaBDD(context);
        gesAquaBDD.open();
        bdd = gesAquaBDD.getBDD();
    }

    /**
     * @brief Méthode pour obtenir la liste des tuples de la table 'modes'
     * @return un type List qui contient les tuples de la table
     */
    public List<Mode> getListe()
    {
        List<Mode> modes = new ArrayList<>();
        Cursor cursor = bdd.query("modes", new String[] {/*"idMode",*/"dateDebut","dateFin","heureDebut","heureFin","mode"}, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            Mode mode = cursorToMode(cursor, false);
            modes.add(mode);
            cursor.moveToNext();
        }

        cursor.close();

        return modes;

    }

    /**
     * @brief Permet d'insérer un tuple dans la table 'modes'
     * @param mode un objet de type Mode et qui représente un tuple de la table
     * @return un long différent de -1 si l'insertion a réussi
     */
    public long inserer(Mode mode)
    {
        ContentValues values = new ContentValues();

        values.put("dateDebut", mode.getDateDebut());
        values.put("dateFin", mode.getDateFin());
        values.put("heureDebut", mode.getHeureDebut());
        values.put("heureFin", mode.getHeureFin());
        values.put("mode", mode.getMode());

        return bdd.insert("modes", null, values);
    }

    /**
     * @brief Permet de mettre à jour un tuple de la table 'modes'
     //* @param idMode un int qui représente l'identifiant du tuple
     * @param mode un objet de type Mode qui va remplacer l'ancien
     * @return un int
     */
    public int modifier(/*int idMode,*/ Mode mode)
    {
        ContentValues values = new ContentValues();

        values.put("dateDebut", mode.getDateDebut());
        values.put("dateFin", mode.getDateFin());
        values.put("heureDebut", mode.getHeureDebut());
        values.put("heureFin", mode.getHeureFin());
        values.put("mode", mode.getMode());

        return bdd.update("modes", values,"" , null); // "idMode = " + idMode,
    }


    /*public Mode getMode(String nom)
    {
        Cursor c = bdd.rawQuery("SELECT * FROM modes WHERE idMode = ?", new String[] { nom });

        return cursorToMode(c, true);
    }*/

    /**
     * @brief Cette méthode permet de convertir un cursor en un objet de type Mode
     * @param c un objet de type Cursor qui va parcourir le tuple
     * @param one un booléen
     * @return un objet de type Mode (donc un tuple de la table 'modes')
     */
    private Mode cursorToMode(Cursor c, boolean one)
    {
        if (c.getCount() == 0)
            return null;

        if(one == true)
            c.moveToFirst();

        Mode mode = new Mode();

        mode.setDateDebut(c.getString(1));
        mode.setDateFin(c.getString(2));
        mode.setHeureDebut(c.getString(3));
        mode.setHeureFin(c.getString(4));
        mode.setMode(c.getInt(5));

        if(one == true)
            c.close();

        return mode;
    }
}
