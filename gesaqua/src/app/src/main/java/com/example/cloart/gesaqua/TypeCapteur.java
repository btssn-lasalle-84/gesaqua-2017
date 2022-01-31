package com.example.cloart.gesaqua;

/**
 * @class TypeCapteur
 * @brief Représente un tuple dans la table 'typeCapteurs'
 * Created by cloart on 22/03/17.
 */

public class TypeCapteur
{
    private int idTypeCapteur;
    private int typeCapteur;

    /**
     * @brief Constructeur par défaut de la classe TypeCapteur
     */
    public TypeCapteur() {}

    /**
     * @brief Constructeur public de la classe TypeCapteur
     * @param idTypeCapteur : un int qui représente un identifiant qui correspond à la clé primaire de la table typeCapteurs
     * @param typeCapteur : un int qui représente un identifiant qui correspond au type de capteur
     */
    public TypeCapteur(int idTypeCapteur, int typeCapteur)
    {
        this.idTypeCapteur = idTypeCapteur;
        this.typeCapteur = typeCapteur;
    }

    /**
     * @brief Accesseur qui permet d'obtenir la valeur de l'identifiant qui correspond au type de capteur
     * @return  : un int qui est la valeur de l'identifiant qui correspond au type de capteur
     */
    public int getIdTypeCapteur() {
        return idTypeCapteur;
    }

    /**
     * @brief Manipulateur qui permet de modifier la valeur de l'identifiant qui correspond au type de capteur
     * @param idTypeCapteur : un int qui identifie le type du capteur
     */
    public void setIdTypeCapteur(int idTypeCapteur) {
        this.idTypeCapteur = idTypeCapteur;
    }

    /**
     * @brief Accesseur qui permet d'obtenir le type de capteur
     * @return un int qui représente le type de capteur
     */
    public int getTypeCapteur() {
        return typeCapteur;
    }

    /**
     * @brief Manipulateur qui permet de modifier le type de capteur
     * @param typeCapteur un int qui représente le type de capteur
     */
    public void setTypeCapteur(int typeCapteur) {
        this.typeCapteur = typeCapteur;
    }

    /**
     * @brief Méthode publique toString()
     * @return la liste des attributs de la classe TypeCapteur sous forme de String
     */
    public String toString()
    {
        return "Id du type de capteur" +  idTypeCapteur +"\nType de capteur : " + typeCapteur;
    }
}
