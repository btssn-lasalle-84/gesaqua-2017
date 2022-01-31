package com.example.cloart.gesaqua;

/**
 * @class ParametreVital
 * @brief Représente un tuple dans la table 'parametresVitaux'
 * Created by cloart on 22/03/2017
 */

public class ParametreVital {

    private int idParametreVital;
    private int idCapteur;
    private double mesure;
    private String horodatage;

    /**
     * @brief Constructeur par défaut de la classe ParametreVital
     */
    public ParametreVital() {}

    /**
     * @brief Constructeur public de la classe ParametreVital
     * @param idCapteur : un int qui représente l'identifiant du capteur
     * @param mesure : un double qui représente la valeur de la mesure
     * @param horodatage : un string qui représente la date et l'heure
     */
    public ParametreVital(int idCapteur, double mesure, String horodatage)
    {
        this.idParametreVital = -1;
        this.idCapteur = idCapteur;
        this.mesure = mesure;
        this.horodatage = horodatage;
    }

    /**
     * @brief Accesseur qui permet d'obtenir la valeur de l'identifiant du paramètre vital
     * @return idParametresVitaux : un int qui représente a clé primaire de la table parametresVitaux
     */
    public int getIdParametreVital() {
        return idParametreVital;
    }

    /**
     * @brief Accesseur qui permet de modifier la valeur de l'identifiant du paramètre vital
     * @param idParametreVital: un int qui représente qui est une valeur servant à identifier un paramètre vital
     */
    public void setIdParametreVital(int idParametreVital) {
        this.idParametreVital = idParametreVital;
    }

    /**
     * @brief Accesseur qui permet d'obtenir la valeur de l'identifiant du capteur
     * @return idCapteur : un int qui représente l'identifiant du capteur
     */
    public int getIdCapteur() {
        return idCapteur;
    }

    /**
     * @brief Accesseur qui permet de modifier la valeur de l'identifiant du capteur
     * @param idCapteur :  un int qui représente l'identifiant du capteur
     */
    public void setIdCapteur(int idCapteur) {
        this.idCapteur = idCapteur;
    }

    /**
     * @brief Accesseur qui permet d'obtenir la valeur de la mesure effectuée par le capteur
     * @return un double qui représente la valeur de la mesure effectuée par le capteur
     */
    public double getMesure() {
        return mesure;
    }

    /**
     * @brief Accesseur qui permet de modifier la valeur de a mesure effectuée par le capteur
     * @param mesure : un double qui représente la valeur de la mesure effectuée par le capteur
     */
    public void setMesure(double mesure) {
        this.mesure = mesure;
    }

    /**
     * @brief Accesseur qui permet d'obtenir la date et l'heure auxquelles la mesure a été effectuée
     * @return horodatage : un String qui représente la date et l'heure de la mesure
     */
    public String getHorodatage() {
        return horodatage;
    }

    /**
     * @brief Accesseur qui permet de modifier la date et l'heure auxquelles à laquelle la mesure a été effectuée
     * @param horodatage : un String qui représente la date et l'heure de la mesure
     */
    public void setHorodatage(String horodatage) {
        this.horodatage = horodatage;
    }

    /**
     * @brief Méthode publique toString()
     * @return la liste des attributs de la classe ParametreVital String
     */
    public String toString()
    {
        return "Id du paramètre vital :" + idParametreVital + "\nId du capteur :" + idCapteur + "\nValeur de la mesure : " + mesure + "Date et heure de la mesure" + horodatage;
    }
}
