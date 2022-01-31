package com.example.cloart.gesaqua;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
/**
 * @class Aquarium
 * @brief L'objet Aquarium est un tuple de la table 'aquariums'
 * Created by cloart on 16/03/17.
 */

public class Aquarium
{
    private GesAquaBDD gesAquaBDD;
    private SQLiteDatabase bdd;
    private int idAquarium;
    private double largeur; //en cm
    private double longueur; // en cm
    private double hauteur; // en cm
    private double volume; // en litres
    private String dateMiseEnEau;
    private String type; //type : amazonien, ou africain ou asiatique ou européen (environnement de l'aquarium)

    /**
     * @brief Constructeur de la classe Aquarium
     */
    public Aquarium(Context context) {
        gesAquaBDD = new GesAquaBDD(context);
        gesAquaBDD.open();
        bdd = gesAquaBDD.getBDD();
    }

    /**
     * @brief Méthode qui permet d'initialiser un nouvel objet Aquarium
     * @param largeur : un double qui représente la largeur de l'aquarium en cm
     * @param longueur : un double qui représente la longueur de l'aquarium en cm
     * @param hauteur : un double qui représente la hauteur de l'aquarium en cm
     * @param volume : un double qui représente le volume de l'aquarium en litres
     * @param dateMiseEnEau Un String qui représente la date où l'on remplit l'aquarium
     * @param type Un string qui représente le type d'aquarium : amazonien, ou africain ou asiatique ou européen (environnement de l'aquarium)
     */
    public void set(double largeur, double longueur, double hauteur, double volume, String dateMiseEnEau, String type)
    {
        this.largeur = largeur;
        this.longueur = longueur;
        this.hauteur = hauteur;
        this.volume = volume;
        this.dateMiseEnEau = dateMiseEnEau;
        this.type = type;
    }

    /**
     * @brief Méthode pour insérer dse valeurs dans la table aquariums
     * @return un long qui est != -1 si l'insertion a réussi
     */
    public long inserer()
    {
        ContentValues values = new ContentValues();

        values.put("largeur", getLargeur());
        values.put("longueur", getLongueur());
        values.put("hauteur", getHauteur());
        values.put("volume", getVolume());
        values.put("dateMiseEnEau", getDateMiseEnEau());
        values.put("type", getType());

        return bdd.insert("aquariums", null, values);
    }

    /**
     * @brief Méthode pour mettre à jour des données dans la table aquariums
     * @param idAquarium : identifiant du tuple
     * @return un int qui est != -1 si l'insertion a réussi
     */
    public int modifier(int idAquarium)
    {
        ContentValues values = new ContentValues();

        values.put("largeur", getLargeur());
        values.put("longueur", getLongueur());
        values.put("hauteur", getHauteur());
        values.put("volume", getVolume());
        values.put("dateMiseEnEau", getDateMiseEnEau());
        values.put("type", getType());

        return bdd.update("aquariums", values, "idAquarium = " + idAquarium, null);
    }

    /**
     * @brief Méthode pour supprimer un tuple dans la table aquariums
     * @param idAquarium : un int qui correspond à l'identifiant du tuple à supprimer
     * @return un int
     */
    public int supprimer(int idAquarium)
    {
        return bdd.delete("aquariums", "idAquarium = " + idAquarium, null);
    }

    /**
     * @brief Permet de récupérer la valeur d'un tuple de la table en fonction de son identifiant
     * @param id un int qui représente l'identifiant de l'aquarium dans la table 'aquariums'
     */
    public void getAquarium(int id)
    {
        Cursor c = bdd.rawQuery("SELECT * FROM aquariums WHERE idAquarium = ?", new String[] { String.valueOf(id) });

        cursorToAquarium(c, true);
    }

    /**
     * @brief Permet de récupérer la valeur d'un tuple de la table en fonction de son type (amazonien, ou africain ou asiatique ou européen)
     * @param type un String qui représente le type d'environnement de l'aquarium (amazonien, ou africain ou asiatique ou européen)
     */
    public void getAquarium(String type)
    {
        Cursor c = bdd.rawQuery("SELECT * FROM aquariums WHERE type = ?", new String[] {  });

        cursorToAquarium(c, true);
    }

    /**
     * @brief Cette méthode permet de convertir un cursor en un objet de type Aquarium
     * @param c un Cursor qui va se déplacer dans la table 'aquariums'
     * @param one un booléen
     */
    private void cursorToAquarium(Cursor c, boolean one)
    {
        if (c.getCount() == 0)
            return;

        if(one == true)
            c.moveToFirst();

        setIdAquarium(c.getInt(0));
        setLargeur(c.getDouble(1));
        setLongueur(c.getDouble(2));
        setHauteur(c.getDouble(3));
        setVolume(c.getDouble(4));
        setDateMiseEnEau(c.getString(5));
        setType(c.getString(6));

        if(one == true)
            c.close();
    }

    /**
     * @brief Permet l'accès à la valeur de l'identifiant de l'aquarium
     * @return idAquarium un int qui représente la clé primaire de la table aquarium
     */
    public int getIdAquarium()
    {
        return idAquarium;
    }

    /**
     * @brief Permet de modifier l'identifiant de l'aquarium
     * @param idAquarium un int qui représente la clé primaire de la table aquarium
     */
    public void setIdAquarium(int idAquarium)
    {
        this.idAquarium = idAquarium;
    }

    /**
     * @brief Permet d'obtenir la mesure de la largeur de l'aquarium en cm
     * @return un double qui représente la largeur de l'aquarium en cm
     */
    public double getLargeur()
    {
        return largeur;
    }

    /**
     * @brief Permet de modifier la largeur de l'aquarium (en cm)
     * @param largeur un double qui représente la largeur de l'aquarium en cm
     */
    public void setLargeur(double largeur)
    {
        this.largeur = largeur;
    }

    /**
     * @brief Permet d'obtenir la mesure de la longueur de l'aquarium en cm
     * @return un double qui représente la longueur de l'aquarium en cm
     */
    public double getLongueur()
    {
        return  longueur;
    }

    /**
     * @brief Permet de modifier la longueur de l'aquarium (en cm)
     * @param longueur un double qui représente la longueur de l'aquarium en cm
     */
    public void setLongueur(double longueur)
    {
        this.longueur = longueur;
    }

    /**
     * @brief Permet d'obtenir la mesure de la hauteur de l'aquarium en cm
     * @return un double qui représente la hauteur de l'aquarium en cm
     */
    public double getHauteur()
    {
        return hauteur;
    }

    /**
     * @brief Permet de modifier la hauteur de l'aquarium (en cm)
     * @param hauteur un double qui représente la hauteur de l'aquarium en cm
     */
    public void setHauteur(double hauteur)
    {
        this.hauteur = hauteur;
    }

    /**
     * @brief Permet d'obtenir le volume de l'aquarium en litres
     * @return un double qui représente le volume de l'aquarium en litres
     */
    public double getVolume()
    {
        return volume;
    }

    /**
     * @brief Permet de modifier le volume de l'aquarium (en cm)
     * @param volume un double qui représente le volume de l'aquarium en litres
     */
    public void setVolume(double volume)
    {
        this.volume = volume;
    }

    /***
     * @brief Permet d'obtenir la date de mise en eau de l'aquarium
     * @return un String qui représente la date de mise en eau de l'aquarium
     */
    public String getDateMiseEnEau()
    {
        return dateMiseEnEau;
    }

    /**
     * @brief Permet de modifier la date de mise en eau de l'aquarium
     * @param dateMiseEnEau un String qui représente la date de mise en eau de l'aquarium
     */
    public void setDateMiseEnEau(String dateMiseEnEau)
    {
        this.dateMiseEnEau = dateMiseEnEau;
    }

    /**
     * @brief Permet d'obtenir le type d'aquarium ("amazonie", "africain", "asiatique" ou "européen")
     * @return un String qui représente le type d'aquarium ("amazonie", "africain", "asiatique" ou "européen")
     */
    public String getType()
    {
        return type;
    }


    /**
     * @brief Permet de modifier le type d'aquarium ("amazonie", "africain", "asiatique" ou "européen")
     * @param type un String qui représente le type d'aquarium ("amazonie", "africain", "asiatique" ou "européen")
     */
    public void setType(String type)
    {
        this.type = type;
    }

    /**
     * @brief Permet d'obtenir les caractéristiques de l'aquarium
     * @return un String qui représente les caractéristiques de l'aquarium : identifiant, largeur, longueur, hauteur, volume, date de mise en eau et type ("amazonie", "africain", "asiatique" ou "européen")
     */
    public String toString()
    {
        return /*"Id de l'aquarium : " + idAquarium + "\n" + */ "Largeur (cm) : " + largeur + "\nLongueur (cm) : " +  longueur + "\nHauteur (cm) : " + hauteur + "\nVolume (L) : " + volume + "\nDate de mise en eau : " + dateMiseEnEau + "\nType : "  + type;
    }

}
