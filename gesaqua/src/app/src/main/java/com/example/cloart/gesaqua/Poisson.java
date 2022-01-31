package com.example.cloart.gesaqua;

/**
 * @class Poisson
 * @brief Permet d'instancier des objets de type Poisson
 * Created by cloart on 24/03/17.
 */

public class Poisson
{
    private int idPoisson;
    private String nomScientifique;
    private String nomCommun;
    private String paysOrigine;
    private double tailleAdulte;
    private double temperatureMinSupportee;
    private double temperatureMaxSupportee;
    private double phMinSupporte;
    private double phMaxSupporte;
    private double dureteEauMin;
    private double dureteEauMax;
    private String famille;
    private String zoneDeVie;
    private String pseudo;

    /**
     * @brief Constructeur par défaut de la classe Poisson
     */
    public Poisson() {}

    /**
     * @brief Constructeur public de la classe Poisson
     * @param nomScientifique un String qui correspond au nom scientifique du poisson
     * @param nomCommun un String qui correspond au nom commun du poisson
     * @param paysOrigine un String qui correspond au nom du pays d'origine du poisson
     * @param tailleAdulte un double qui correspond à la taille adulte du poisson (en cm)
     * @param temperatureMinSupportee un double qui correspond à la valeur de la température minimale nécessaire à la survie du poisson (en °C)
     * @param temperatureMaxSupportee un double qui correspond à la valeur de la température maximale nécessaire à la survie du poisson (en °C)
     * @param phMinSupporte un double qui correspond à la valeur du pH minimal nécessaire à la survie du poisson
     * @param phMaxSupporte un double qui correspond à la valeur du pH maximal nécessaire à la survie du poisson
     * @param dureteEauMin un double qui correspond à la valeur de la dureté minimale de l'eau nécessaire à la survie du poisson (unité = "°f)
     * @param dureteEauMax un double qui correspond à la valeur de la dureté maximale de l'eau nécessaire à la survie du poisson (unité = "°f)
     * @param famille un String qui correspond au nom de la famille à laquelle appartient le poisson
     * @param zoneDeVie un String qui correspond au nom de la zone de vie où vit le poisson (exemple : "Asie", "Europe", "Afrique", ...)
     * @param pseudo un String qui contient la valeur des attributs de la classe Poisson
     */
    public Poisson(String nomScientifique, String nomCommun, String paysOrigine, double tailleAdulte,
            double temperatureMinSupportee, double temperatureMaxSupportee, double phMinSupporte,
            double phMaxSupporte, double dureteEauMin, double dureteEauMax, String famille,
            String zoneDeVie, String pseudo)
    {
        this.nomScientifique = nomScientifique;
        this.nomCommun = nomCommun;
        this.paysOrigine = paysOrigine;
        this.tailleAdulte = tailleAdulte;
        this.temperatureMinSupportee = temperatureMinSupportee;
        this.temperatureMaxSupportee = temperatureMaxSupportee;
        this.phMinSupporte = phMinSupporte;
        this.phMaxSupporte = phMaxSupporte;
        this.dureteEauMin = dureteEauMin;
        this.dureteEauMax = dureteEauMax;
        this.famille = famille;
        this.zoneDeVie = zoneDeVie;
        this.pseudo = pseudo;

    }

    /**
     * @brief Méthode qui permet d'obtenir la valeur de l'identifiant du poisson
     * @return un int qui représente la valeur de l'identifiant du poisson
     */
    public int getIdPoisson()
    {
        return idPoisson;
    }

    /**
     * @brief Méthode qui permet de modifier la valeur de l'identifiant du poisson
     * @param idPoisson un int qui représente la valeur de l'identifiant du poisson
     */
    public void setIdPoisson(int idPoisson)
    {
        this.idPoisson = idPoisson;
    }

    /**
     * @brief Méthode qui permet d'obtenir le nom scientifique du poisson
     * @return un String qui correspond au nom scientifique du poisson
     */
    public String getNomScientifique()
    {
        return nomScientifique;
    }

    /**
     * @brief Méthode qui permet de modifier le nom scientifique du poisson
     * @param nomScientifique un String qui correspond au nom scientifique du poisson
     */
    public void setNomScientifique(String nomScientifique)
    {
        this.nomScientifique = nomScientifique;
    }

    /**
     * @brief Méthode qui permet d'obtenir le nom commun du poisson
     * @return un String qui correspond au nom commun du poisson
     */
    public String getNomCommun()
    {
        return  nomCommun;
    }

    /**
     * @brief Méthode qui permet de modifier le nom commun du poisson
     * @param nomCommun un String qui correspond au nom commun du poisson
     */
    public void setNomCommun(String nomCommun)
    {
        this.nomCommun = nomCommun;
    }

    /**
     * @brief Méthode qui permet d'obtenir le nom du pays d'origine du poisson
     * @return un String qui correspond au nom du pays d'origine du poisson
     */
    public String getPaysOrigine()
    {
        return paysOrigine;
    }

    /**
     * @brief Méthode qui permet de modifier le nom du pays d'origine du poisson
     * @param paysOrigine un String qui correspond au nom du pays d'origine du poisson
     */
    public void setPaysOrigine(String paysOrigine)
    {
        this.paysOrigine = paysOrigine;
    }

    /**
     * @brief Méthode qui permet d'obtenir la taille adulte du poisson (en cm)
     * @return un double qui correspond à la taille adulte du poisson (en cm)
     */
    public double getTailleAdulte()
    {
        return tailleAdulte;
    }

    /**
     * @brief Méthode qui permet de modifier la taille adulte du poisson (en cm)
     * @param tailleAdulte un double qui correspond à la taille adulte du poisson (en cm)
     */
    public void setTailleAdulte(double tailleAdulte)
    {
        this.tailleAdulte = tailleAdulte;
    }

    /**
     * @brief Méthode qui permet d'obtenir la valeur de la température minimale nécessaire à la survie du poisson (en °C)
     * @return un double qui correspond à la valeur de la température minimale nécessaire à la survie du poisson (en °C)
     */
    public double getTemperatureMinSupportee()
    {
        return temperatureMinSupportee;
    }

    /**
     * @brief Méthode qui permet de modifier la valeur de la température minimale nécessaire à la survie du poisson (en °C)
     * @param temperatureMinSupportee un double qui correspond à la valeur de la température minimale nécessaire à la survie du poisson (en °C)
     */
    public void setTemperatureMinSupportee(double temperatureMinSupportee)
    {
        this.temperatureMinSupportee = temperatureMinSupportee;
    }

    /**
     * @brief Méthode qui permet d'obtenir la valeur de la température maximale nécessaire à la survie du poisson (en °C)
     * @return un double qui correspond à la valeur de la température maximale nécessaire à la survie du poisson (en °C)
     */
    public double getTemperatureMaxSupportee()
    {
        return temperatureMaxSupportee;
    }

    /**
     * @brief Méthode qui permet de modifier la valeur de la température maximale nécessaire à la survie du poisson (en °C)
     * @param temperatureMaxSupportee un double qui correspond à la valeur de la température maximale nécessaire à la survie du poisson (en °C)
     */
    public void setTemperatureMaxSupportee(double temperatureMaxSupportee)
    {
        this.temperatureMaxSupportee = temperatureMaxSupportee;
    }

    /**
     * @brief Méthode qui permet d'obtenir la valeur du pH minimal nécessaire à la survie du poisson (sans unité)
     * @return un double qui correspond à la valeur du pH minimal nécessaire à la survie du poisson
     */
    public double getPhMinSupporte()
    {
        return phMinSupporte;
    }

    /**
     * @brief Méthode qui permet de modifier la valeur du pH minimal nécessaire à la survie du poisson (sans unité)
     * @param phMinSupporte un double qui correspond à la valeur du pH minimal nécessaire à la survie du poisson
     */
    public void setPhMinSupporte(double phMinSupporte)
    {
        this.phMinSupporte = phMinSupporte;
    }

    /**
     * @brief Méthode qui permet d'obtenir la valeur du pH maximal nécessaire à la survie du poisson (sans unité)
     * @return un double qui correspond à la valeur du pH maximal nécessaire à la survie du poisson
     */
    public double getPhMaxSupporte()
    {
        return phMaxSupporte;
    }

    /**
     * @brief Méthode qui permet de modifier la valeur du pH maximal nécessaire à la survie du poisson (sans unité)
     * @param phMaxSupporte un double qui correspond à la valeur du pH maximal nécessaire à la survie du poisson
     */
    public void setPhMaxSupporte(double phMaxSupporte)
    {
        this.phMaxSupporte = phMaxSupporte;
    }

    /**
     * @brief Méthode qui permet d'obtenir la valeur de la dureté minimale de l'eau nécessaire à la survie du poisson (unité = "°f)
     * @return un double qui correspond à la valeur de la dureté minimale de l'eau nécessaire à la survie du poisson (unité = "°f)
     */
    public double getDureteEauMin()
    {
        return dureteEauMin;
    }

    /**
     * @brief Méthode qui permet de modifier la valeur de la dureté minimale de l'eau nécessaire à la survie du poisson (unité = "°f)
     * @param dureteEauMin un double qui correspond à la valeur de la dureté minimale de l'eau nécessaire à la survie du poisson (unité = "°f)
     */
    public void setDureteEauMin(double dureteEauMin)
    {
        this.dureteEauMin = dureteEauMin;
    }

    /**
     * @brief Méthode qui permet d'obtenir la valeur de la dureté maximale de l'eau nécessaire à la survie du poisson (unité = "°f)
     * @return un double qui correspond à la valeur de la dureté maximale de l'eau nécessaire à la survie du poisson (unité = "°f)
     */
    public double getDureteEauMax()
    {
        return  dureteEauMax;
    }

    /**
     * @brief Méthode qui permet de modifier la valeur de la dureté maximale de l'eau nécessaire à la survie du poisson (unité = "°f)
     * @param dureteEauMax un double qui correspond à la valeur de la dureté maximale de l'eau nécessaire à la survie du poisson (unité = "°f)
     */
    public void setDureteEauMax(double dureteEauMax)
    {
        this.dureteEauMax = dureteEauMax;
    }

    /**
     * @brief Méthode qui permet d'obtenir le nom de la famille à laquelle appartient le poisson
     * @return un String qui correspond au nom de la famille à laquelle appartient le poisson
     */
    public String getFamille()
    {
        return famille;
    }

    /**
     * @brief Méthode qui permet de modifier le nom de la famille à laquelle appartient le poisson
     * @param famille un String qui correspond au nom de la famille à laquelle appartient le poisson
     */
    public void setFamille(String famille)
    {
        this.famille = famille;
    }

    /**
     * @brief Méthode qui permet d'obtenir le nom de la zone de vie où vit le poisson (exemple : "Asie", "Europe", "Afrique", ...)
     * @return un String qui correspond au nom de la zone de vie où vit le poisson (exemple : "Asie", "Europe", "Afrique", ...)
     */
    public String getZoneDeVie()
    {
        return zoneDeVie;
    }

    /**
     * @brief Méthode qui permet de modifier le nom de la zone de vie où vit le poisson (exemple : "Asie", "Europe", "Afrique", ...)
     * @param zoneDeVie un String qui correspond au nom de la zone de vie où vit le poisson (exemple : "Asie", "Europe", "Afrique", ...)
     */
    public void setZoneDeVie(String zoneDeVie)
    {
        this.zoneDeVie = zoneDeVie;
    }

    /**
     * @brief Méthode qui permet d'obtenir le nom du pseudo du poisson
     * @return un String qui correspond au nom du pseudo du poisson
     */
    public String getPseudo()
    {
        return pseudo;
    }

    /**
     * @brief Méthode qui permet de modifier le nom du pseudo du poisson
     * @param pseudo un String qui correspond au nom du pseudo du poisson
     */
    public void setPseudo(String pseudo)
    {
        this.pseudo = pseudo;
    }

    /**
     * @brief Méthode qui permet d'obtenir la valeur des attributs de la classe Poisson
     * @return un String qui contient la valeur des attributs de la classe Poisson
     */
    public String toString()
    {
        return "id du poisson : " + idPoisson + "\nNom scientifique : " + nomScientifique +
                "\nNom Commun : " + nomCommun + "\nPays d'origine : " + paysOrigine +
                "\nTaille Adulte : " + tailleAdulte + "\nTempérature Minimale nécessaire : " + temperatureMinSupportee +
                "\nTempérature Maximale nécessaire : " + temperatureMaxSupportee + "\npH Minimal nécessaire : " + phMinSupporte +
                "\npH Maximal nécessaire : " + phMaxSupporte + "\nDureté de l'eau minimale nécessaire : " + dureteEauMin +
                "\nDureté de l'eau maximale nécessaire : " + dureteEauMax + "\nFamille : " + famille +
                "\nZone de vie : " + zoneDeVie + "\nPseudo : " + pseudo ;
    }
}
