package com.example.cloart.gesaqua;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;

/**
 * Created by bresset on 31/03/17.
 * crée une nouvelle instance pour les paramétres.
 */

public class Parametres extends AppCompatActivity
{
    double tempMax;
    double tempMin;
    double PhMax;
    double PhMin;
    double niveauMin;
    double niveauMax;
    private Seuils seuils = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parametres_main);
        /** crée une instance */
        Intent intent = getIntent();
        /** récupére les données mise dans l'intent par l'activité principale pour les attribuer aux variables locales
         * qui correspondent aux données anciennement saisies par l'utilisateur sinon valeurs par défaut
         * */
        tempMin = intent.getDoubleExtra("Tmin", 23);
        tempMax = intent.getDoubleExtra("Tmax", 27);
        PhMin = intent.getDoubleExtra("Phmin", 6);
        PhMax = intent.getDoubleExtra("Phmax", 8);
        niveauMin = intent.getDoubleExtra("Niveaumin", 25);
        niveauMax = intent.getDoubleExtra("Niveaumax", 30);
        /** récupére les 4 EditText pour les modifier et édite les EditText avec les données récupérées*/
        ((EditText) findViewById(R.id.editTextMinTemp)).setText(""+ tempMin);
        ((EditText) findViewById(R.id.editTextMaxTemp)).setText(""+ tempMax);
        ((EditText) findViewById(R.id.editTextMinPh)).setText(""+ PhMin);
        ((EditText) findViewById(R.id.editTextMaxPh)).setText(""+ PhMax);
        ((EditText) findViewById(R.id.editTextNiveauEauMin)).setText(""+ niveauMin);
        ((EditText) findViewById(R.id.editTextNiveauEauMax)).setText(""+ niveauMax);
    }

    public void Retour(View view)
    {
        /** met fin a l'activité pour retourner à l'activité principale*/
        finish();
    }
    public void Valider(View view)
    {

        /** récupére les valeurs saisies par l'utilisateur dans les edittext et les met dans les variables locales tempMin, tempMax, PhMax et PhMin*/
        tempMin = Double.valueOf(((EditText) findViewById(R.id.editTextMinTemp)).getText().toString());
        tempMax = Double.valueOf(((EditText) findViewById(R.id.editTextMaxTemp)).getText().toString());
        PhMin = Double.valueOf(((EditText) findViewById(R.id.editTextMinPh)).getText().toString());
        PhMax = Double.valueOf(((EditText) findViewById(R.id.editTextMaxPh)).getText().toString());
        niveauMin = Double.valueOf(((EditText) findViewById(R.id.editTextNiveauEauMin)).getText().toString());
        niveauMax = Double.valueOf(((EditText) findViewById(R.id.editTextNiveauEauMax)).getText().toString());
        /** cree des variables string locale pour envoyer des argument à l'activité principale*/
        String Tmin= "Tmin";
        String Tmax = "Tmax";
        String Phmin = "PhMin";
        String Phmax = "PhMax";
        String niveauEaumin = "niveauEaumin";
        String niveauEaumax = "niveauEaumax";
        /** recrée une instance pour retransferer des valeurs à l'activité principale*/
        Intent intent = new Intent();
        /** met une valeur qui sera envoyée à l'activité principale lors de la fin de l'activité en cours*/
        intent.putExtra(Tmin, tempMin);
        intent.putExtra(Tmax, tempMax);
        intent.putExtra(Phmin, PhMin);
        intent.putExtra(Phmax, PhMax);
        intent.putExtra(niveauEaumax, niveauMax);
        intent.putExtra(niveauEaumin, niveauMin);
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
}
