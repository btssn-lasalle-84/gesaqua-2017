package com.example.cloart.gesaqua;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * @class TypeCapteurs
 * @brief Fournit des méthodes pour effectuer des requêtes sur la table 'typeCapteurs' de la base de données
 * Created by cloart on 23/03/17.
 */

public class TypeCapteurs
{
    private GesAquaBDD gesAquaBDD;
    private SQLiteDatabase bdd;

    /**
     * @brief Constructeur public de la classe TypeCapteurs
     * @param context un objet de type Context
     */
    public TypeCapteurs(Context context)
    {
        gesAquaBDD = new GesAquaBDD(context);
        gesAquaBDD.open();
        bdd = gesAquaBDD.getBDD();
    }

    /**
     * @brief Méthode pour obtenir la liste des tuples de la table 'typeCapteurs'
     * @return un type List qui contient les tuples de la table
     */
    public List<TypeCapteur> getListe()
    {
        List<TypeCapteur> typeCapteurs = new ArrayList<TypeCapteur>();

        Cursor cursor = bdd.query("typeCapteurs", new String[] {"idTypeCapteur", "typeCapteur"}, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            TypeCapteur typeCapteur = cursorToTypeCapteur(cursor, false);
            typeCapteurs.add(typeCapteur);
            cursor.moveToNext();
        }

        cursor.close();

        return typeCapteurs;
    }

    /**
     * @brief Permet d'insérer un tuple dans la table 'typeCapteurs'
     * @param typeCapteur un objet de type TypeCapteur et qui représente un tuple de la table 'typeCapteurs'
     * @return un long différent de -1 si l'insertion a réussi
     */
    public long inserer(TypeCapteur typeCapteur)
    {
        ContentValues values = new ContentValues();

        values.put("idTypeCapteur", typeCapteur.getIdTypeCapteur());
        values.put("typeCapteur", typeCapteur.getTypeCapteur());

        return bdd.insert("typeCapteurs", null, values);
    }

    /**
     * @brief Permet de mettre à jour un tuple de la table 'typeCapteurs'
     * @param id un int qui représente l'identifiant du tuple
     * @param typeCapteur un objet de type TypeCapteur qui va remplacer l'ancien
     * @return un int
     */
    public int modifier(int id, TypeCapteur typeCapteur)
    {
        ContentValues values = new ContentValues();
        values.put("idTypeCapteur", typeCapteur.getIdTypeCapteur());
        values.put("typeCapteur", typeCapteur.getTypeCapteur());

        return bdd.update("typeCapteurs", values, "idTypeCapteur = " + id, null);
    }

    /**
     * @brief Permet de supprimer un tuple de la table 'typeCapteurs'
     * @param id un int qui représente l'identifiant du tuple
     * @return un int
     */
    public int supprimer(int id)
    {
        return bdd.delete("typeCapteurs", "idTypeCapteur = " + id, null);
    }

   /*
    public TypeCapteur getTypeCapteur(String nom)
    {
        Cursor c = bdd.rawQuery("SELECT * FROM typeCapteurs WHERE nom = ?", new String[] { nom });

        return cursorToTypeCapteur(c, true);
    }*/

    /**
     * @brief Cette méthode permet de convertir un cursor en un objet de type Typecapteur
     * @param c un objet de type Cursor qui va parcourir le tuple
     * @param one un booléen
     * @return un objet de type Typecapteur (un tuple de la table 'typeCapteurs')
     */
    // Cette méthode permet de convertir un cursor en un objet de type Typecapteur
    private TypeCapteur cursorToTypeCapteur(Cursor c, boolean one)
    {
        if (c.getCount() == 0)
            return null;

        if(one == true)
            c.moveToFirst();

        TypeCapteur typeCapteur = new TypeCapteur();

        typeCapteur.setIdTypeCapteur(c.getInt(0));
        typeCapteur.setTypeCapteur(c.getInt(1));

        if(one == true)
            c.close();

        return typeCapteur;
    }



}
