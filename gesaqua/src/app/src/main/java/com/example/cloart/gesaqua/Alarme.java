package com.example.cloart.gesaqua;

/**
 * @class Alarme
 * @brief Représente un tuple de la table 'alarmes'
 * @author Audrey Cloart
 */

public class Alarme
{
    private int idAlarme;
    private String type;
    private double mesure;
    private String horodatage;
    private double seuilMin;
    private double seuilMax;
    private String description;


    /**
     * @brief Constructeur par défaut de la classe Alarme
     */
    public Alarme() {}

    /**
     * @brief Constructeur public de la classe Alarme
     * @param type un String qui correspond au type de l'alarme (pH, température, niveau d'eau)
     * @param mesure un double qui correspond à la valeur mesurée lors de l'alarme
     * @param horodatage un String qui correspond à la date et l'heure de l'alarme
     * @param seuilMin un double qui correspond à la valeur minimale du seuil où l'alarme doit se déclencher
     * @param seuilMax un double qui correspond à la valeur maximale du seuil où l'alarme doit se déclencher
     * @param description un String qui correspond à la description de l'alarme
     */
    public Alarme(String type, double mesure, String horodatage, double seuilMin, double seuilMax, String description)
    {
        this.type = type; // pH, température, niveau d'eau
        this.mesure = mesure;
        this.horodatage = horodatage;
        this.seuilMin = seuilMin;
        this.seuilMax = seuilMax;
        this.description = description;
    }

    /**
     * @brief Permet d'obtenir la valeur de l'identifiant de l'alarme
     * @return un int qui représente la valeur de l'identifiant de l'alarme
     */
    public int getIdAlarme() {
        return idAlarme;
    }

    /**
     * @brief Permet de modifier la valeur de l'identifiant de l'alarme
     * @param idAlarme un int qui représente la valeur de l'identifiant de l'alarme
     */
    public void setIdAlarme(int idAlarme) {
        this.idAlarme = idAlarme;
    }

    /**
     * @brief Permet d'obtenir le type d'alarme (pH, température, niveau d'eau)
     * @return un String qui correspond au type de l'alarme (pH, température, niveau d'eau)
     */
    public String getType() {
        return type;
    }

    /**
     * @brief Permet de modifier le type d'alarme (pH, température, niveau d'eau)
     * @param type un String qui correspond au type de l'alarme (pH, température, niveau d'eau)
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @brief Permet d'obtenir la valeur mesurée lors de l'alarme
     * @return un double qui correspond à la valeur mesurée lors de l'alarme
     */
    public double getMesure() {
        return mesure;
    }

    /**
     * @brief Permet de modifier la valeur mesurée lors de l'alarme
     * @param mesure un double qui correspond à la valeur mesurée lors de l'alarme
     */
    public void setMesure(double mesure) {
        this.mesure = mesure;
    }

    /**
     * @brief Permet d'obtenir la date et l'heure de l'alarme (format : "YYYY-MM-DD HH:MM:SS")
     * @return un String qui correspond à la date et l'heure de l'alarme
     */
    public String getHorodatage() {
        return horodatage;
    }

    /**
     * @brief Permet de modifier la date et l'heure de l'alarme format : "YYYY-MM-DD HH:MM:SS")
     * @param horodatage un String qui correspond à la date et l'heure de l'alarme
     */
    public void setHorodatage(String horodatage) {
        this.horodatage = horodatage;
    }

    /**
     * @brief Permet d'obtenir la valeur minimale du seuil où l'alarme doit se déclencher (ce seuil dépend du type d'alarme)
     * @return un double qui correspond à la valeur minimale du seuil où l'alarme doit se déclencher
     */
    public double getSeuilMin() {
        return seuilMin;
    }

    /**
     * @brief Permet de modifier a valeur minimale du seuil où l'alarme doit se déclencher (ce seuil dépend du type d'alarme)
     * @param seuilMin un double qui correspond à la valeur minimale du seuil où l'alarme doit se déclencher
     */
    public void setSeuilMin(double seuilMin) {
        this.seuilMin = seuilMin;
    }

    /**
     * @brief Permet d'obtenir la valeur maximale du seuil où l'alarme doit se déclencher (ce seuil dépend du type d'alarme)
     * @return  un double qui correspond à la valeur maximale du seuil où l'alarme doit se déclencher
     */
    public double getSeuilMax() {
        return seuilMax;
    }

    /**
     * @brief Permet de modifier a valeur maximale du seuil où l'alarme doit se déclencher (ce seuil dépend du type d'alarme)
     * @param seuilMax un double qui correspond à la valeur maximale du seuil où l'alarme doit se déclencher
     */
    public void setSeuilMax(double seuilMax) {
        this.seuilMax = seuilMax;
    }

    /**
     * @brief Permet d'obtenir la description de l'alarme (exemple : "température trop haute")
     * @return un String qui correspond à la description de l'alarme
     */
    public String getDescription() {
        return description;
    }

    /**
     * @brief Permet de modifier la description de l'alarme (exemple : "température trop haute")
     * @param description un String qui correspond à la description de l'alarme
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
