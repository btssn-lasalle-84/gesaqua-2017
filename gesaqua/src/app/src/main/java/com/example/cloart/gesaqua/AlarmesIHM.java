package com.example.cloart.gesaqua;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

/**
 * Created by iris on 02/06/17.
 */

public class AlarmesIHM extends AppCompatActivity {

    boolean alarmePh;
    boolean alarmeNivEau;
    boolean alarmeTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarmes_ihm_main);
        /** crée une instance */
        Intent intent = getIntent();
        alarmePh = intent.getBooleanExtra("Ph",alarmePh);
        alarmeNivEau = intent.getBooleanExtra("NivEau",alarmeNivEau);
        alarmeTemp = intent.getBooleanExtra("Temp",alarmeTemp);
        verifierEtat();
    }

    public void onCheckboxPhClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        if (checked)
            alarmePh = true;
        else
            alarmePh = false;
    }

    public void onCheckboxTempClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        if (checked)
            alarmeTemp = true;
        else
            alarmeTemp = false;
    }

    public void onCheckboxNivEauClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        if (checked)
            alarmeNivEau = true;
        else
            alarmeNivEau = false;
    }


    public void Valider(View view)
    {
        /** cree des variables string locale pour envoyer des argument à l'activité principale*/
        String Temp= "Temp";
        String Ph = "Ph";
        String nivEau = "NivEau";
        /** recrée une instance pour retransferer des valeurs à l'activité principale*/
        Intent intent = new Intent();
        /** met une valeur qui sera envoyée à l'activité principale lors de la fin de l'activité en cours*/
        intent.putExtra(Temp, alarmeTemp);
        intent.putExtra(Ph, alarmePh);
        intent.putExtra(nivEau, alarmeNivEau);
        /**
         * Remplacement du tuples correspondant aux seuils dans la table seuils : voir main activity
         */
        // TODO
        /** définit le résultat pour le retour vers l'activité principale qui permettra la vérification et
         * le remplacement des valeurs de l'activité principales par celles de cette activité CF fonction onActivityResult
         * */
        setResult(RESULT_OK, intent);
        /** met fin a l'activité pour retourner à l'activité principale*/
        finish();
    }

    public void Retour(View view) {
        Intent intent = new Intent();
        intent.putExtra("active", false);
        setResult(RESULT_CANCELED, intent);
        // met fin à l'activité
        finish();
    }


    public void verifierEtat()
    {
        if (alarmePh == true)
            ((CheckBox)findViewById(R.id.checkBoxAlarmePh)).setChecked(true);
        else
            ((CheckBox)findViewById(R.id.checkBoxAlarmePh)).setChecked(false);

        if (alarmeNivEau == true)
            ((CheckBox)findViewById(R.id.checkBoxAlarmeNiveau)).setChecked(true);
        else
            ((CheckBox)findViewById(R.id.checkBoxAlarmeNiveau)).setChecked(false);

        if (alarmeTemp == true)
            ((CheckBox)findViewById(R.id.checkBoxAlarmeTemperature)).setChecked(true);
        else
            ((CheckBox)findViewById(R.id.checkBoxAlarmeTemperature)).setChecked(false);
    }
}
