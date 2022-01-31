package com.example.cloart.gesaqua;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
/**
 * @class Seuils
 * @brief Seuils de sécurité imposés par le système, et non pas par l'utilisateur.
 * @brief Permet d'effectuer des requêtes sur la table seuils de la base de données
 * Created by cloart on 20/04/17.
 */

public class Seuils
{
    private GesAquaBDD gesAquaBDD;
    private SQLiteDatabase bdd;

    private double temperatureMin; // unité : en degrés celsius
    private double temperatureMax; // unité : en degrés celsius
    private double phMin; //pas d'unité
    private double phMax; //pas d'unité
    private double niveauEauMin; // unité : en cm
    private double niveauEauMax; //unité : en cm

    /**
     * @brief Constructeur public de la classe Seuils
     * @param context : classe abstraite qui permet l'accès à des classes et ressources spécifiques à l'application
     */
    public Seuils(Context context)
    {
        gesAquaBDD = new GesAquaBDD(context);
        gesAquaBDD.open();
        bdd = gesAquaBDD.getBDD();
    }

    /**
     * @brief Méthode pour obtenir la valeur du seuil minimal de température du système en °C (degrés celsius)
     * @return un double qui représente le seuil minimal de température du système en °C (degrés celsius)
     */
    public double getTemperatureMin() {

        return temperatureMin;
    }

    /**
     * @brief Méthode qui permet de modifier la valeur du seuil minimal de température du système en °C (degrés celsius)
     * @param temperatureMin : un double qui représente le seuil minimal de température du système en °C (degrés celsius)
     */
    public void setTemperatureMin(double temperatureMin) {
        if(this.temperatureMin != temperatureMin)
        {
            this.temperatureMin = temperatureMin;
            // enregistre la nouvelle valeur dans la table seuils
            int resultat = modifier();
            if (resultat != -1) {
                Log.d("setTemperatureMin()", "Modification seuil température min : " + temperatureMin); // d = debug
            }
        }
    }

    /**
     * @brief Méthode pour obtenir la valeur du seuil maximal de température du système en °C (degrés celsius)
     * @return un double qui représente le seuil maximal de température du système en °C (degrés celsius)
     */
    public double getTemperatureMax() {

        return temperatureMax;
    }

    /**
     * @brief Méthode qui permet de modifier la valeur du seuil maximal de température du système en °C (degrés celsius)
     * @param temperatureMax : un double qui représente le seuil maximal de température du système en °C (degrés celsius)
     */
    public void setTemperatureMax(double temperatureMax) {
        if(this.temperatureMax != temperatureMax)
        {
            this.temperatureMax = temperatureMax;
            // enregistre la nouvelle valeur dans la table seuils
            int resultat = modifier();
            if (resultat != -1) {
                Log.d("setTemperatureMax()", "Modification seuil température max : " + temperatureMax); // d = debug
            }
        }
    }

    /**
     * @brief Méthode pour obtenir la valeur du seuil minimal de pH du système (sans unité) en dessous duquel il ne faut pas descendre
     * @return un double qui représente le seuil minimal de pH du système (sans unité)
     */
    public double getPhMin() {

        return phMin;
    }

    /**
     * @brief Méthode qui permet de modifier la valeur du seuil minimal de pH du système (sans unité) en dessous duquel il ne faut pas descendre
     * @param phMin : un double qui représente le seuil minimal de pH du système (sans unité)
     */
    public void setPhMin(double phMin) {
        if(this.phMin != phMin)
        {
            this.phMin = phMin;
            // enregistre la nouvelle valeur dans la table seuils
            int resultat = modifier();
            if (resultat != -1) {
                Log.d("setPhMin()", "Modification seuil ph min : " + phMin); // d = debug
            }
        }
    }

    /**
     * @brief Méthode pour obtenir la valeur du seuil maximal de pH du système (sans unité) au-dessus duquel il ne faut pas monter
     * @return un double qui représente le seuil maximal du pH du système (sans unité)
     */
    public double getPhMax() {

        return phMax;
    }

    /**
     * @brief Méthode qui permet de modifier la valeur du seuil maximal de pH du système (sans unité) au-dessus duquel il ne faut pas monter
     * @param phMax : un double qui représente le seuil maximal du pH du système (sans unité)
     */
    public void setPhMax(double phMax) {
        if(this.phMax != phMax)
        {
            this.phMax = phMax;
            // enregistre la nouvelle valeur dans la table seuils
            int resultat = modifier();
            if (resultat != -1) {
                Log.d("setPhMax()", "Modification seuil ph max : " + phMax); // d = debug
            }
        }
    }

    /**
     * @brief Méthode qui permet d'obtenir la valeur du  niveau d'eau minimal dans l'aquarium nécessaire au système en centimètres
     * @return un double qui représente le seuil minimal acceptable de l'eau (unité : le centimètre (cm))
     */
    public double getNiveauEauMin() {

        return niveauEauMin;
    }

    /**
     * @brief Méthode qui permet de modifier la valeur du niveau d'eau minimal dans l'aquarium nécessaire au système en centimètres
     * @param niveauEauMin un double qui représente le seuil minimal acceptable de l'eau (unité : le centimètre (cm))
     */
    public void setNiveauEauMin(double niveauEauMin) {
        if(this.niveauEauMin != niveauEauMin)
        {
            this.niveauEauMin = niveauEauMin;
            // enregistre la nouvelle valeur dans la table seuils
            int resultat = modifier();
            if (resultat != -1) {
                Log.d("setNiveauEauMin()", "Modification seuil niveau min : " + niveauEauMin); // d = debug
            }
        }
    }

    /**
     * @brief Méthode qui permet d'obtenir la valeur du  niveau d'eau maximal dans l'aquarium nécessaire au système en centimètres
     * @return un double qui représente le seuil maximal acceptable de l'eau (unité : le centimètre (cm))
     */
    public double getNiveauEauMax() {

        return niveauEauMax;
    }

    /**
     * @brief Méthode qui permet de modifier la valeur du niveau d'eau maximal dans l'aquarium nécessaire au système en centimètres
     * @param niveauEauMax un double qui représente le seuil maximal acceptable de l'eau (unité : le centimètre (cm))
     */
    public void setNiveauEauMax(double niveauEauMax) {
        if(this.niveauEauMax != niveauEauMax)
        {
            this.niveauEauMax = niveauEauMax;
            // enregistre la nouvelle valeur dans la table seuils
            int resultat = modifier();
            if (resultat != -1) {
                Log.d("setNiveauEauMax()", "Modification seuil niveau max : " + niveauEauMax); // d = debug
            }
        }
    }

    /**
     * @brief Méthode publique toString()
     * @return la liste des attributs sous forme de String
     */
    public String toString()
    {
        return "Seuils\nTempérature min : " + temperatureMin + " °C" + "\nTempérature max : " + temperatureMax + " °C" + "\npH min : " + phMin + "\npH max : " + phMax + "\nNiveau d'eau min : "  + niveauEauMin + " cm" + "\nNiveau d'eau max : "  + niveauEauMax + " cm";
    }

    /**
     * @brief Méthode pour obtenir les seuils à partir de la table seuils
     * */
    public void obtenir()
    {
        Cursor cursor = bdd.query("seuils", new String[] {"temperatureMin", "temperatureMax", "phMin", "phMax", "niveauEauMin", "niveauEauMax"}, null, null, null, null, null);

        cursorToSeuil(cursor);
    }

    /**
     * @brief Méthode pour insérer les valeurs dans la table seuils
     * @return un long différent de -1 si l'insertion a réussi
     */
    public long inserer()
    {
        ContentValues values = new ContentValues();

        values.put("temperatureMin", getTemperatureMin());
        values.put("temperatureMax", getTemperatureMax());
        values.put("phMin", getPhMin());
        values.put("phMax", getPhMax());
        values.put("niveauEauMin", getNiveauEauMin());
        values.put("niveauEauMax", getNiveauEauMax());

        return bdd.insert("seuils", null, values);
    }

    /**
     * @brief Méthode pour mettre à jour les valeurs dans la table seuils
     * @return un int
     */
    public int modifier()
    {
        ContentValues values = new ContentValues();

        values.put("temperatureMin", getTemperatureMin());
        values.put("temperatureMax", getTemperatureMax());
        values.put("phMin", getPhMin());
        values.put("phMax", getPhMax());
        values.put("niveauEauMin", getNiveauEauMin());
        values.put("niveauEauMax", getNiveauEauMax());

        return bdd.update("seuils", values, "", null);
    }


    /**
     * @brief Cette méthode permet d'obtenir les seuils à partir d'un cursor d'une requête
     * @param c de type Cursor qui pourra parcourir la table
     */
    private void cursorToSeuil(Cursor c)
    {
        if (c.getCount() == 0)
            return;

        c.moveToFirst();

        setTemperatureMin(c.getDouble(0));
        setTemperatureMax(c.getDouble(1));
        setPhMin(c.getDouble(2));
        setPhMax(c.getDouble(3));
        setNiveauEauMin(c.getDouble(4));
        setNiveauEauMax(c.getDouble(5));

        c.close();
    }
}
