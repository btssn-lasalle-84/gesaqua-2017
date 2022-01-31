package com.example.cloart.gesaqua;

/**
 * @class Consigne
 * @brief Ce sont les paramètres réglables par l'utilisateur depuis l'IHM de l'application
 * Created by iris on 20/04/17.
 */

public class Consigne
{

    private double temperature; //en degrés celsius
    private double ph;
    private double niveauEau; //en cm

    /**
     * @brief Constructeur par défaut de la classe Consigne
     */
    public Consigne() {}

    /**
     * @brief Constructeur public de la classe Consigne
     * @param temperature : un double qui représente la valeur de la température de consigne (unité = °C)
     * @param ph : un double qui représente la valeur du pH de consigne
     * @param niveauEau : un double qui représente le niveau d'eau de consigne (unité = cm)
     */
    public Consigne(double temperature, double ph, double niveauEau)
    {
        this.temperature = temperature;
        this.ph = ph;
        this.niveauEau = niveauEau;
    }

    /**
     * @brief Méthode pour modifier la valeur de la température de la consigne (unité = °C)
     * @param temperature un double qui représente la valeur de la température de la consigne que l'on veut modifier (unité = °C)
     */
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    /**
     * @brief Méthode pour modifier la valeur de la température de la consigne (unité = °C)
     * @return temperature un double qui représente la valeur de la température de la consigne que l'on veut modifier (unité = °C)
     */
    public double getTemperature() {
        return temperature;
    }


    /**
     * @brief Méthode pour obtenir la valeur du pH minimal de la consigne
     * @return un double qui représente la valeur du pH minimal de la consigne
     */
    public double getPh() {
        return ph;
    }

    /**
     * @brief Méthode pour modifier la valeur du pH minimal de la consigne
     * @param ph : un double qui représente la valeur du pH minimal de la consigne que l'on veut modifier
     */
    public void setPh(double ph) {
        this.ph = ph;
    }


    /**
     * @brief Méthode pour obtenir le niveau d'eau minimal de la consigne (unité = cm)
     * @return un double qui représente le niveau d'eau minimal de la consigne (unité = cm)
     */
    public double getNiveauEau() {
        return niveauEau;
    }

    /**
     * @brief Méthode pour modifier le niveau d'eau minimal de la consigne (unité = cm)
     * @param niveauEau  : un double qui représente le niveau d'eau minimal de la consigne que l'on veut modifier (unité = cm)
     */
    public void setNiveauEau(double niveauEau) {
        this.niveauEau = niveauEau;
    }

    /**
     * @brief Méthode publique toString()
     * @return la liste des attributs de la classe Consigne sous forme de String
     */
    public String toString()
    {
        return "Température : " + temperature + " °C" + "\npH : " + ph + "\nNiveau d'eau  : "  + niveauEau + " cm";
    }
}
