package com.example.cloart.gesaqua;

/**
 * @class Capteur
 * @brief Représente un tuple dans la table 'capteurs'
 * Created by cloart on 22/03/17.
 */

public class Capteur {

    private int idCapteurs;
    private int typeCapteurs;
    private String nom;
    private String unite;

    /**
     * @brief Constructeur par défaut de la classe Capteur
     */
    public Capteur() {}

    /**
     * @brief Constructeur public de la classe Capteur
     * @param typeCapteurs : un int qui sert à identifier le type de capteur
     * @param nom : un String qui représente le nom du capteur
     * @param unite : un String qui représente l'unité des mesures du capteur (exemple : "°C "pour la température)
     * */
    public Capteur(int typeCapteurs, String nom, String unite)
    {
        this.typeCapteurs = typeCapteurs;
        this.nom = nom;
        this.unite = unite;
    }

    /**
     * @brief Accesseur qui permet d'obtenir la valeur de idCapteurs
     * @return idCapteurs, un int qui représente l'identifiant du capteur dans la table de la base de données
     */
    public int getIdCapteurs() {
        return idCapteurs;
    }

    /**
     * @brief Accesseur qui permet de modifier la valeur de l'identifiant du capteur
     * @param idCapteurs : un int qui représente l'identifiant du capteur
     */
    public void setIdCapteurs(int idCapteurs) {
        this.idCapteurs = idCapteurs;
    }

    /**
     * @brief Accesseur get qui permet d'obtenir la valeur d'un int qui représente le type de capteur utilisé
     * @return typeCapteurs, un int qui représente le type de capteur utilisé
     */
    public int getTypeCapteurs() {
        return typeCapteurs;
    }

    /**
     * @brief Accesseur qui permet de modifier le type de capteur utilisé
     * @param typeCapteurs : un int qui représente le type de capteur utilisé
     */
    public void setTypeCapteurs(int typeCapteurs) {
        this.typeCapteurs = typeCapteurs;
    }

    /**
     * @brief Accesseur qui permet d'obtenir la valeur d'un String qui représente le nom du capteur
     * @return nom : un String qui représente le nom du capteur
     */
    public String getNom() {
        return nom;
    }

    /**
     * @brief Accesseur qui permet de modifier le nom de capteur utilisé
     * @param nom  : un String qui représente le nouveau nom du capteur
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @brief Accesseur qui permet d'obtenir la valeur d'un String qui représente l'unité de mesure du capteur (exemple : °C, °F pour la température
     * @return unite, un String qui représente l'unité de mesure du capteur (exemple : °C, °F pour la température
     */
    public String getUnite() {
        return unite;
    }

    /**
     * @brief Accesseur qui permet de modifier l'unité de capteur utilisé dans la base de données
     * @param unite : un String qui représente la nouvelle unité du capteur
     */
    public void setUnite(String unite) {
        this.unite = unite;
    }

    /**
     * @brief Méthode publique toString()
     * @return la liste des attributs de la classe Capteur sous forme de String
     */
    public String toString()
    {
        return "Id du capteur :" + idCapteurs + "\nType de capteur :" + typeCapteurs + "\nNom du capteur : " + nom + "Unité du capteur" + unite;
    }


}
