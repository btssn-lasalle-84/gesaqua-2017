package com.example.cloart.gesaqua;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.StringTokenizer;

/**
 * @class CalendarViewExample
 * @brief Activité qui permet d'ouvrir une vue de type calendrier
 */
public class CalendarViewExample extends AppCompatActivity {


    private TimePicker timePickerDebut, timePickerFin;
    private DatePicker datePicker;
    private Calendar calendar;

    //Affichage
    private TextView dateDebut, dateFin;
    private TextView tvDisplayTimeDebut;
    private TextView tvDisplayTimeFin;

   //Boutons
    private Button btnChangeTimeDebut;
    private Button btnChangeTimeFin;


    private int annee, mois, jour;
    private int hourDebut, hourFin;
    private int minuteDebut, minuteFin;

    //Boîtes de dialogue selon leur identifiant
    static final int DATE_DEBUT_ID = 999;
    static final int DATE_FIN_ID = 998;
    static final int TIME_DEBUT_ID = 997;
    static final int TIME_FIN_ID = 996;

    private int MODE_AUTOMATIQUE = 1;
    private Modes modes = null;
    private Mode mode = null;

    //formatage des dates et heures avant enregistrement en base de données
    private String formatDateDebut = "";
    private String formatDateFin = "";
    private String formatHeureDebut = "";
    private String formatHeureFin = "";

    int verif = 0;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mode_auto_view);

        setCurrentTimeOnView();
        addListenerOnButton();

        dateDebut = (TextView) findViewById(R.id.textViewDateDebut);
        initierCalendrier();
        afficherDateDebut(annee, mois + 1,jour);
        formatDateDebut = formaterDate(annee, mois, jour);

        dateFin = (TextView) findViewById(R.id.textViewDateFin);
        initierCalendrier();
        afficherDateFin(annee, mois + 1,jour);
        formatDateFin = formaterDate(annee, mois, jour);


    }

    /**
     * @brief modifie la date pour qu'elle soit dans un format qui convienne pour être enregistré en base de données
     * @param annee un int qui représente un nombre d'années pour une date
     * @param mois un int qui représente un nombre de mois pour une date
     * @param jour un int qui représente un nombre de jours pour une date
     * @return un String qui représente une date formatée comme suit : 'AAAA-MM-JJ'
     */
    public String formaterDate(int annee, int mois, int jour)
    {
        return "'" + String.valueOf(annee) + "-" + String.valueOf(mois + 1) + "-" + String.valueOf(jour) + "'";
    }

    /**
     * @brief modifie l'heure pour qu'elle soit dans un format qui convienne pour être enregistré en base de données
     * @param heures un int qui représente un nombre d'heure
     * @param minutes un int qui représente un nombre de minutes
     * @return un String qui représente un horaire formaté comme suit : 'HH:MM:SS'
     */
    public String formaterHeure(int heures, int minutes)
    {
        return "'" + String.valueOf(pad(heures)) + ":" + String.valueOf(pad(minutes)) + ":" + "00'";
    }
    /**
     * @brief initie un calendrier avec le jour, le mois et l'année en cours par défaut
     */
    public void initierCalendrier()
    {
        calendar = Calendar.getInstance();
        annee = calendar.get(Calendar.YEAR);
        mois = calendar.get(Calendar.MONTH);
        jour = calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * @brief permet de modifier la date de démarrage du mode automatique en passant par une vue
     * @param view un type View
     */
    @SuppressWarnings("deprecation")
    public void setDateDebut(View view) {

        showDialog(DATE_DEBUT_ID);
        Toast.makeText(getApplicationContext(), "debut",
                Toast.LENGTH_SHORT)
                .show();
    }

    /**
     * @brief permet de modifier la date de fin du mode automatique en passant par une vue
     * @param view un type View
     */
    @SuppressWarnings("deprecation")
    public void setDateFin(View view) {
        showDialog(DATE_FIN_ID);
        Toast.makeText(getApplicationContext(), "fin",
                Toast.LENGTH_SHORT)
                .show();
    }

    /**
     * @brief affiche l'heure en cours dans la boîte de dialogue
     */
    // display current time
    public void setCurrentTimeOnView() {

        tvDisplayTimeDebut = (TextView) findViewById(R.id.tvTimeDebut);
        tvDisplayTimeFin = (TextView) findViewById(R.id.tvTimeFin);
        timePickerDebut = (TimePicker) findViewById(R.id.timePickerDebut);
        timePickerFin = (TimePicker) findViewById(R.id.timePickerFin);

        final Calendar cDebut = Calendar.getInstance();
        hourDebut = cDebut.get(Calendar.HOUR_OF_DAY);
        minuteDebut = cDebut.get(Calendar.MINUTE);

        final Calendar cFin = Calendar.getInstance();
        hourFin = cFin.get(Calendar.HOUR_OF_DAY);
        minuteFin = cFin.get(Calendar.MINUTE);

        // set current time into textview
        tvDisplayTimeDebut.setText(
                new StringBuilder().append(pad(hourDebut))
                        .append(":").append(pad(minuteDebut)));

        tvDisplayTimeFin.setText(
                new StringBuilder().append(pad(hourFin))
                        .append(":").append(pad(minuteFin)));

        // set current time into timepicker
        timePickerDebut.setCurrentHour(hourDebut);
        timePickerDebut.setCurrentMinute(minuteDebut);

        timePickerFin.setCurrentHour(hourFin);
        timePickerFin.setCurrentMinute(minuteFin);

        formatHeureDebut = formaterHeure(hourDebut, minuteDebut);
        formatHeureFin = formaterHeure(hourFin, minuteFin);
    }


    /**
     * @brief un signal qui prévient des changements d'heure dans une boite de dialogue
     */
    public void addListenerOnButton() {

        btnChangeTimeDebut = (Button) findViewById(R.id.btnChangeTimeDebut);
        btnChangeTimeFin= (Button) findViewById(R.id.btnChangeTimeFin);

        btnChangeTimeDebut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showDialog(TIME_DEBUT_ID);

            }

        });
        btnChangeTimeFin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showDialog(TIME_FIN_ID);

            }

        });

    }

    /**
     * création de la boite de dialogue pour changer l'heure de début
     */
    private TimePickerDialog.OnTimeSetListener timePickerListenerDebut =
            new TimePickerDialog.OnTimeSetListener()
            {
                public void onTimeSet(TimePicker view, int selectedHour,
                                      int selectedMinute) {
                    hourDebut = selectedHour;
                    minuteDebut = selectedMinute;

                    // set current time into textview
                    tvDisplayTimeDebut.setText(new StringBuilder().append(pad(hourDebut))
                            .append(":").append(pad(minuteDebut)));

                    // set current time into timepicker
                    timePickerDebut.setCurrentHour(hourDebut);
                    timePickerDebut.setCurrentMinute(minuteDebut);

                }
            };

    /**
     * création de la boîte de dialogue pour changer l'heure de fin
     */
    private TimePickerDialog.OnTimeSetListener timePickerListenerFin=
            new TimePickerDialog.OnTimeSetListener()
            {
                public void onTimeSet(TimePicker view, int selectedHour,
                                      int selectedMinute) {
                    hourFin= selectedHour;
                    minuteFin= selectedMinute;

                    // set current time into textview
                    tvDisplayTimeFin.setText(new StringBuilder().append(pad(hourFin))
                            .append(":").append(pad(minuteFin)));

                    // set current time into timepicker
                    timePickerFin.setCurrentHour(hourFin);
                    timePickerFin.setCurrentMinute(minuteFin);

                }
            };

    /**
     * @brief Permet d'afficher un 0 dans un horaire afin d'avoir un formatage correct : exemple : 03:05 au lieu de 3:5
     * @param c un int qui représente un nombre d'heures ou de minutes
     * @return un String qui représente un horaire avec un formatage correct
     */
    private static String pad(int c)
    {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }


    /**
     * @brief les différentes boîtes de dialogues pour changer la date ou l'heure
     * @param id identifiant de la boîte de dialogue
     * @return une boîte de dialogue qui dépend de l'identifiant reçu
     */
    @Override
    protected Dialog onCreateDialog(int id)
    {

        switch (id)
        {
            case DATE_DEBUT_ID:
                return new DatePickerDialog(this,listenerDebut, annee, mois, jour);
            case DATE_FIN_ID:
                return new DatePickerDialog(this,listenerFin, annee, mois, jour);
            case TIME_DEBUT_ID:
                return new TimePickerDialog(this,timePickerListenerDebut, hourDebut, minuteDebut, false);
            case TIME_FIN_ID:
                return new TimePickerDialog(this, timePickerListenerFin, hourFin, minuteFin, false);
            default:
                return null;

        }
    }


    private DatePickerDialog.OnDateSetListener listenerDebut = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker,
                                      int annee, int mois, int jour) {

                    afficherDateDebut(annee, mois + 1, jour);
                }
            };


    private DatePickerDialog.OnDateSetListener listenerFin = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker,
                                      int annee, int mois, int jour) {

                    afficherDateFin(annee, mois + 1, jour);
                }
            };

    /**
     * @brief affiche la date de démarrage du mode automatique
     * @param year un int qui représente le jour de démarrage du mode automatique
     * @param month un int qui représente le jour de démarrage du mode automatique
     * @param day un int qui représente le jour de démarrage du mode automatique
     */
    private void afficherDateDebut(int year, int month, int day) {
        dateDebut.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

    /**
     * @brief affiche la date de fin du mode automatique
     * @param year un int qui représente l'année de fin du mode automatique
     * @param month un int qui représente le mois de fin du mode automatique
     * @param day un int qui représente le jour de fin du mode automatique
     */
    private void afficherDateFin(int year, int month, int day) {
        dateFin.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

    /**
     * @brief permet de revenir à la page principale de l'application
     * @param view un type View
     */
    public void boutonRetour(View view)
    {
        finish();
    }

}

