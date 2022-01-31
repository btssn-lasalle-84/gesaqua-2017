package com.example.cloart.gesaqua;

/**
 * @class Mode
 * @brief Représente un tuple dans la table 'modes'
 * Created by cloart on 21/04/17.
 */

public class Mode
{
 //   private int idMode;
    private String dateDebut;
    private String dateFin;
    private String heureDebut;
    private String heureFin;
    private int mode;

    /**
     * @brief Constructeur par défaut de la classe Mode
     */
    public Mode() {}

    /**
     * @brief Constructeur public de la classe Mode
     * @param dateDebut un String qui correspond à la date de début du cycle automatique (format "YYYY-MM-DD")
     * @param dateFin un String qui correspond à la date de fin du cycle automatique (format "YYYY-MM-DD")
     * @param heureDebut un String qui correspond à l'heure de début du cycle automatique (format "HH:MM:SS")
     * @param heureFin un String qui correspond à l'heure de fin du cycle automatique (format "HH:MM:SS")
     * @param mode un int qui correspond au mode utilisé ( MANUEL = 0 et AUTOMATIQUE = 1 )
     */
    public Mode(String dateDebut, String dateFin, String heureDebut, String heureFin, int mode)
    {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.mode = mode;
    }

    /**
     * @brief Méthode qui permet d'obtenir la valeur de l'identifiant du mode
     * @return un int qui représente la valeur de l'identifiant du mode
     */
   /* public int getIdMode() {
        return idMode;
    }*/

    /**
     * @brief Méthode qui permet de modifier la valeur de l'identifiant du mode
     * @param idMode un int qui représente la valeur de l'identifiant du mode
     */
  /*  public void setIdMode(int idMode) {
        this.idMode = idMode;
    }*/

    /**
     * @brief Méthode qui permet d'obtenir la date de début du cycle automatique (format "YYYY-MM-DD")
     * @return un String qui correspond à la date de début du cycle automatique (format "YYYY-MM-DD")
     */
    public String getDateDebut() {
        return dateDebut;
    }

    /**
     * @brief Méthode qui permet de modifier la date de début du cycle automatique (format "YYYY-MM-DD")
     * @param dateDebut un String qui correspond à la date de début du cycle automatique (format "YYYY-MM-DD")
     */
    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    /**
     * @brief Méthode qui permet d'obtenir la date de fin du cycle automatique (format "YYYY-MM-DD")
     * @return un String qui correspond à la date de fin du cycle automatique (format "YYYY-MM-DD")
     */
    public String getDateFin() {
        return dateFin;
    }

    /**
     * @brief Méthode qui permet de modifier la date de fin du cycle automatique (format "YYYY-MM-DD")
     * @param dateFin un String qui correspond à la date de fin du cycle automatique (format "YYYY-MM-DD")
     */
    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    /**
     * @brief Méthode qui permet d'obtenir l'heure de début du cycle automatique (format "HH:MM:SS")
     * @return un String qui correspond à l'heure de début du cycle automatique (format "HH:MM:SS")
     */
    public String getHeureDebut() {
        return heureDebut;
    }

    /**
     * @brief Méthode qui permet de modifier l'heure de début du cycle automatique (format "HH:MM:SS")
     * @param heureDebut un String qui correspond à l'heure de début du cycle automatique (format "HH:MM:SS")
     */
    public void setHeureDebut(String heureDebut) {
        this.heureDebut = heureDebut;
    }

    /**
     * @brief Méthode qui permet d'obtenir l'heure de fin du cycle automatique (format "HH:MM:SS")
     * @return un String qui correspond à l'heure de fin du cycle automatique (format "HH:MM:SS")
     */
    public String getHeureFin() {
        return heureFin;
    }

    /**
     * @brief Méthode qui permet de modifier l'heure de fin du cycle automatique (format "HH:MM:SS")
     * @param heureFin un String qui correspond à l'heure de fin du cycle automatique (format "HH:MM:SS")
     */
    public void setHeureFin(String heureFin) {
        this.heureFin = heureFin;
    }

    /**
     * @brief Méthode qui permet d'obtenir le mode ( MANUEL = 0 et AUTOMATIQUE = 1 )
     * @return un int qui correspond au mode utilisé ( MANUEL = 0 et AUTOMATIQUE = 1 )
     */
    public int getMode() {
        return mode;
    }

    /**
     * @brief Méthode qui permet de modifier le mode ( MANUEL = 0 et AUTOMATIQUE = 1 )
     * @param mode un int qui correspond au mode utilisé ( MANUEL = 0 et AUTOMATIQUE = 1 )
     */
    public void setMode(int mode) {
        this.mode = mode;
    }

}
