package cz.radejova.kaja.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    private TextView result_gender_input_textView;
    private TextView result_age_input_textView;
    private TextView result_birthday_input_textView;
    private String rodneCislo;
    private int den;
    private int mesic;
    private int rok;
    private int age;
    private String ageText;

    //osetreni mesice pro zeny pro pouziti metody jeMesicValidni
    private Integer osetriMesic(Integer mesic) {
        if (mesic >= 51 && mesic <= 62) {
            return mesic - 50;
        }
        return mesic;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

//ziskani dat z "obalky" do druhe aktivity
        rodneCislo = getIntent().getStringExtra("rodneCislo");
        den = getIntent().getIntExtra("den", den);
        mesic = getIntent().getIntExtra("mesic", mesic);
        rok = getIntent().getIntExtra("rok", rok);

//gender
        result_gender_input_textView = findViewById(R.id.result_gender_input_textView);
        if (mesic >= 51 && mesic <= 62) {
            result_gender_input_textView.setText("Žena");
        } else {
            result_gender_input_textView.setText("Muž");
        }

//age
        result_age_input_textView = findViewById(R.id.result_age_input_textView);

        if (rodneCislo.length() == 9) {
            age = 2018 - (rok + 1900);
        } else if (rodneCislo.length() == 10 && rok < 19) {
            age = 2018 - (rok + 2000);
        } else if (rodneCislo.length() == 10 && rok >= 19) {
            age = 2018 - (rok + 1900);
        }
        ageText = String.valueOf(age);
        result_age_input_textView.setText(ageText + " " + "let");


//birthday
        result_birthday_input_textView = findViewById(R.id.result_birthday_input_textView);

        String denText = String.valueOf(den);
        String mesicText = String.valueOf(osetriMesic(mesic));
        String rokText = String.valueOf(rok);

        if (rodneCislo.length() == 9) {
            if (rok >= 0 && rok <= 9) {
                rokText = 190 + rokText;
            } else {
                rokText = 19 + rokText;
            }
        } else if (rodneCislo.length() == 10 && rok < 19) {
            rokText = 20 + rokText;
        } else if (rodneCislo.length() == 10 && rok >= 19) {
            rokText = 19 + rokText;
        }
        result_birthday_input_textView.setText(denText + "." + " " + mesicText + "." + " " + rokText);

    }
}
