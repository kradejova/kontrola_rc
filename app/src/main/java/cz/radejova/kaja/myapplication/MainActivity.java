package cz.radejova.kaja.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.time.temporal.ValueRange;

public class MainActivity extends AppCompatActivity {

    private EditText main_rodne_editText;
    private Button main_check_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_rodne_editText = findViewById(R.id.main_rodne_editText);
        main_check_button = findViewById(R.id.main_check_button);

    }

    //osetreni mesice pro zeny pro pouziti metody jeMesicValidni
    private Integer osetriMesic(Integer mesic) {
        if (mesic >= 51 && mesic <= 62) {
            return mesic - 50;
        }
        return mesic;
    }


    private boolean jeDenValidni(Integer rok, Integer mesic, Integer den) {

        switch (mesic) {
//mesice, ktere maji 31 dni
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
//mesice, ktere maji 30 dni
            case 4:
            case 6:
            case 9:
            case 11:
                if (den < 31) {
                    return true;
                }
                return false;
            //  break;
// unor
            case 2:
                if ((rok % 4 == 0 && rok % 100 != 0 || rok % 400 == 0) && den < 30) { //prestupny rok
                    return true;
                } else if (den < 29) { //neprestupny rok
                    return true;
                }
                return false; //pokud je uvedeno vic nez 28 nebo 29 dni v unoru
            //break;
            default:
                return false;
            //break;
        }
    }

    private boolean jeMesicValidni(Integer mesic) {

        if ((mesic >= 1 && mesic <= 12) || (mesic >= 51 && mesic <= 62)) {
            return true;
        } else return false;

    }

    private boolean jeRodneCisloSpravneDlouhe(String rodneCislo) {
        return rodneCislo.length() == 9 || rodneCislo.length() == 10;

    }

    ////    TODO nutné ošetřit pro devíticiferné rodné číslo !!!!!!!!!!
    private boolean jeRodneCisloDelitelne(String rodneCisloText) {

        if (rodneCisloText.length() == 9) {
            return true;
        } else {

            Integer devetCiferRodneCislo = Integer.parseInt(rodneCisloText.substring(0, 9));
            Integer desataCifraRodneCislo = Integer.parseInt(rodneCisloText.substring(9));
            Long rodneCislo = Long.parseLong(rodneCisloText);

            if (devetCiferRodneCislo % 11 == desataCifraRodneCislo && rodneCislo % 11 == 0) {
                return true;
            } else if (devetCiferRodneCislo % 11 == 10 && desataCifraRodneCislo == 0) {
                return true;
            } else return false;
        }

    }


    public void checkRodne(View view) {

        String rodneCislo = main_rodne_editText.getText().toString();

        if (!jeRodneCisloSpravneDlouhe(rodneCislo)) {
            Toast.makeText(this, "Zadejte 9 nebo 10 číslic", Toast.LENGTH_LONG).show();
            Log.d("delkaRodneCislo", "delka rodneho cisla");
        } else {
            //prevedeni substringu na integery
            int mesic = Integer.parseInt(rodneCislo.substring(2, 4));
            int rok = Integer.parseInt(rodneCislo.substring(0, 2));
            int den = Integer.parseInt(rodneCislo.substring(4, 6));
            if (!jeMesicValidni(mesic)) {
                Toast.makeText(this, "Zkontrolujte rodné číslo, chybný měsíc - třetí a čtvrtá číslice.", Toast.LENGTH_LONG).show();
                Log.d("chybnyMesic", "chybny mesic");
            } else if (!jeDenValidni(rok, osetriMesic(mesic), den)) {
                Toast.makeText(this, "Zkontrolujte rodné číslo, chybný den - pátá a šestá číslice.", Toast.LENGTH_LONG).show();
                Log.d("validniDen", "validni den");
            } else if (!jeRodneCisloDelitelne(rodneCislo)) {
                Toast.makeText(this, "Toto není validní rodné číslo! Zkontrolujte všechny číslice.", Toast.LENGTH_LONG).show();
                Log.d("delitelnostRodneCislo", "delitelnost rodneho cisla");
            } else {
                Intent intent = new Intent(this, ResultActivity.class);
                intent.putExtra("rodneCislo", rodneCislo);
                intent.putExtra("den", den);
                intent.putExtra("mesic", mesic);
                intent.putExtra("rok",rok);
                startActivity(intent);
                main_rodne_editText.setText("");

            }


        }
        
    }



}
