package com.example.cloart.gesaqua;

import android.util.Log;

/**
 * @class Appareil
 * @brief  Représente un tuple de la table 'appareils'
 * @author Audrey Cloart
 */

public class Appareil
{
    private int idAppareil;
    private String nom;
    private int etat;
    private String horodatage;


    /**
     * @brief Constructeur par défaut de la classe Appareil
     */
    public Appareil() {}

    /**
     * @brief Constructeur public de la classe Appareil
     * @param nomAppareil : String qui représente le nom de l'appareil (chauffage, pomepe, ...)
     * @param etatFonctionnement : booléen qui représente l'état de fonctionnement de l'appareil, égal à true si l'appareil est alimenté et connecté et false sinon
     * @param horodatage : String qui représente la date et l'heure de la mesure
     * **/

    public Appareil(String nomAppareil, int etatFonctionnement, String horodatage)
    {
        this.nom = nomAppareil;
        this.etat = etatFonctionnement;
        this.horodatage = horodatage;
    }

    /**
     * @brief Accesseur get qui permet d'obtenir la valeur de l'identifiant de l'appareil
     * @return un int qui représente l'identifiant de l'appareil
     */
    public int getIdAppareil()
    {
        return idAppareil;
    }

    /**
     * @brief Manipulateur set qui permet de modifier l'identifiant de l'appareil (idAppareil)
     * @param id int qui représente le nouvel identifiant de l'appareil
     */
    public void setId(int id)
    {
        this.idAppareil = id;
    }

    /**
     * @brief Accesseur get qui permet d'obtenir le nom de l'appareil
     * @return un String qui représente le nom de l'appareil
     */
    public String getNom()
    {
        return nom;
    }

    /**
     * @brief Manipulateur set qui permet de modifier le nom de l'appareil
     * @param nomAppareil String qui représente le nouveau nom de l'appareil
     */
    public void setNom(String nomAppareil)
    {
        this.nom = nomAppareil;
    }

    /**
     * @brief Accesseur get qui permet d'obtenir l'état de fonctionnement de l'appareil
     * @brief retourne un int qui représente l'état de fonctionnement de l'appareil
     * @return 1 si l'appareil est en état de marche (alimenté, connecté) et 0 sinon
     */
    public int getEtat()
    {
        return etat;
    }

    /**
     * @brief Manipulateur set qui permet de modifier l'état de l'appareil
     * @param etatFonctionnement int qui représente l'état de fonctionnement de l'appareil : 1 si l'appareil est alimenté et 0 sinon
     */
    public void setEtat(int etatFonctionnement)
    {
        this.etat = etatFonctionnement;
    }

    /**
     * @brief Permet d'obtenir la valeur de la date et l'heure de la mesure
     * @return un String qui représente la date et l'heure de la mesure
     */
    public String getHorodatage()
    {
        return horodatage;
    }

    /**
     * @brief Permet de modifier la date et la valeur de la mesure
     * @param horodatage un string qui représente la date et l'heure de la mesure
     */
    public void setHorodatage(String horodatage)
    {
        this.horodatage = horodatage;
    }

    /**
     * @brief Méthode publique toString()
     * @return la liste des attributs de la classe Appareil sous forme de String
     */
    public String toString()
    {
        return "id de l'appareil : " + idAppareil + "\nNom de l'appareil : " + nom + "\nEtat de Fonctionnement : " + etat + "\nHorodatage : " + horodatage;
    }

}
